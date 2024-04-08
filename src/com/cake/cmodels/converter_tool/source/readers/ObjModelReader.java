package com.cake.cmodels.converter_tool.source.readers;

import com.cake.cmodels.converter_tool.ConversionLog;
import com.cake.cmodels.converter_tool.source.Submodel;
import com.cake.cmodels.converter_tool.source.SubmodelReader;
import com.cake.cmodels.converter_tool.source.exception.SourceCompileError;
import com.cake.cmodels.core.Geometry;
import com.cake.cmodels.core.model.*;
import com.cake.cmodels.core.types.ResourceLocationLike;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class ObjModelReader implements SubmodelReader {
    
    @Override
    public Submodel readSource(JSONObject source, File file) throws SourceCompileError {
        
        HashMap<String, ResourceLocationLike> materialMap = new HashMap<>();
        if (source.has("material_map")) {
            ConversionLog.log("Using defined material map");
            for (String material : source.getJSONObject("material_map").keySet()) {
                materialMap.put(material, ResourceLocationLike.fromString(material));
            }
        } else {
            ConversionLog.log("Material map undefined, is this intentional?");
        }
    
        ConversionLog.startTimer("obj_read", "Reading OBJ source data");
        try (
            BufferedReader fileInputStream = new BufferedReader(new FileReader(file.getAbsoluteFile()));
        ) {
            ConversionLog.endTimer("obj_read", "Read OBJ source data");
            
            //Mutable properties to read into
            List<Vertex> vertices = new ArrayList<>();
            List<VertexNormal> vertexNormals = new ArrayList<>();
            List<VertexUV> vertexUVs = new ArrayList<>();
            HashMap<String, List<FaceReference>> groupedFaceComponents = new HashMap<>();
            AtomicReference<String> currentObject = new AtomicReference<>();
            AtomicReference<String> currentFaceMaterial = new AtomicReference<>();
    
            ConversionLog.startTimer("obj_parse", "Parsing OBJ source data");
            for (String line : fileInputStream.lines().toList())
                readSourceLine(line, vertices, vertexNormals, vertexUVs, groupedFaceComponents, currentObject, currentFaceMaterial);
            ConversionLog.endTimer("obj_parse", "Parsed OBJ source data");
    
            //Convert the face reference to an actual face
            HashMap<String, List<Face>> readGeometry = new HashMap<>();
            for (String group : groupedFaceComponents.keySet()) for (FaceReference reference : groupedFaceComponents.get(group)) {
                
                Integer currentFaceNormalId = null; //Since there is only flat surfaces, we expect the current face normal to be constant, anything else is treason
                
                List<FaceVertex> faceVertices = new ArrayList<>();
                
                //Check that like, there is actual verticies
                if (reference.vertices.size() == 0)
                    throw new SourceCompileError(//TODO: what fuch
                        "forgor vertecies 💀",
                        "DEATH UPON THOU, END OF ALL AGES IS UPON US"
                    );
                
                for (FaceVertexReference faceVertexReference : reference.vertices) {
                    if (currentFaceNormalId == null)
                        currentFaceNormalId = faceVertexReference.vertexNormal;
                    else if (currentFaceNormalId != faceVertexReference.vertexNormal)
                        throw new SourceCompileError(//TODO: what fuch
                            "NON COMFORMITY IN THE FACE VER TEX NORMALLL!! TREASON BE IT !!",
                            "DEATH UPON THOU, END OF ALL AGES IS UPON US"
                        );
                    
                    faceVertices.add(new FaceVertex(
                        tryGetFaceReferenceProperty(vertices, faceVertexReference.vertex, "vertex"),
                        tryGetFaceReferenceProperty(vertexUVs, faceVertexReference.vertexTexture, "vertex texture")
                    ));
                }
                
                //Get the face normal that was corroborated
                VertexNormal faceNormal = tryGetFaceReferenceProperty(vertexNormals, currentFaceNormalId, "vertex/face normal");
    
                //Put read face in to list
                if (!readGeometry.containsKey(group))
                    readGeometry.put(group, new ArrayList<>());
                
                readGeometry.get(group).add(new Face(faceVertices, faceNormal, reference.material));
            }
            
            return new Geometry(readGeometry, materialMap);
        } catch (IOException e) {
            throw new SourceCompileError("Unable to read file", e.getMessage());
        }
    }
    
    /**Throws a relevant source compile error if the array does not contain the index*/
    private <T> T tryGetFaceReferenceProperty(List<T> propertySource, int index, String propertyName) throws SourceCompileError {
        if (index >= propertySource.size())
            throw new SourceCompileError(
                "Could not find matching property at the index " + index + " of type " + propertyName,
                "Check obj model formatting or report to developer"
            );
        else
            return propertySource.get(index);
    }
    
    /**Tokens that are just skipped, such as smoothing since models aren't able to account for it*/
    List<String> silencedTokens = List.of(
        "mtllib" //Materials are externally defined so they are ignored
    );
    
    /**Take in the line from an OBJ file and place the respective value in the arrays*/
    private void readSourceLine(
        String line,
        List<Vertex> readVertexes, List<VertexNormal> vertexNormals, List<VertexUV> vertexUVs,
        HashMap<String, List<FaceReference>> groupedFaceComponents,
        AtomicReference<String> currentObjectName, AtomicReference<String> currentFaceMaterial
    ) throws SourceCompileError {
        
        if (line.strip().equals("") || line.startsWith("#")) return;
        
        if (!line.contains(" ")) throw new SourceCompileError(
            "Couldn't extract tokens from line " + line,
            "Check obj model formatting or report to developer"
        );
        
        SafeFieldAccessor fields = new SafeFieldAccessor(line);
    
        switch (fields.tryGet(0)) {
            //Misc object definitions
            case "o" -> currentObjectName.set(fields.tryGet(1));
            case "s" -> {
                if (!fields.tryGet(1).equals("0"))
                    ConversionLog.warn("Faces were configured for a curved surface, but these are not supported, change your model?");
            }
            case "usemtl" -> currentFaceMaterial.set(fields.tryGet(1));
            //Vertex definitions
            case "v" -> readVertexes.add(new Vertex(fields.tryGetFloat(1), fields.tryGetFloat(2), fields.tryGetFloat(3)));
            case "vn" -> vertexNormals.add(new VertexNormal(fields.tryGetFloat(1), fields.tryGetFloat(2), fields.tryGetFloat(3)));
            case "vt" -> vertexUVs.add(new VertexUV(fields.tryGetFloat(1), fields.tryGetFloat(2)));
            //Face def
            case "f" -> {
                
                int faceVertexCount = fields.getFieldCount() -1;
                
                if (faceVertexCount <= 2)
                    throw new SourceCompileError(
                        "Face made up of too few verticies! in " + line,
                        "Check .obj file formatting or report to developer"
                    );
                List<FaceVertexReference> faceVertexReferences = new ArrayList<>();
                for (int i = 0; i < faceVertexCount; i++) {
                    String vertexData = fields.tryGet(0);
                    String[] vertexFields = vertexData.split("/");
                    
                    if (vertexFields.length != 3)
                        throw new SourceCompileError(
                            "Expected 3 face vertex components, but found " + vertexFields.length + "! in " + line,
                            "Check .obj file formatting or report to developer"
                        );
    
                    List<Integer> vertexReferenceIds = new ArrayList<>();
                    for (String field : vertexFields) {
                        try {
                            vertexReferenceIds.add(Integer.valueOf(field));
                        } catch (NumberFormatException e) {
                            throw new SourceCompileError(
                                "Misformatted integer value " + field + " in .obj from line for a reference, in" + line,
                                "Check .obj file formatting or report to developer if this should be a valid value"
                            );
                        }
                    }
    
                    faceVertexReferences.add(new FaceVertexReference(vertexReferenceIds.get(0), vertexReferenceIds.get(1), vertexReferenceIds.get(2)));
                }
                
                if (!groupedFaceComponents.containsKey(currentObjectName.get()))
                    groupedFaceComponents.put(currentObjectName.get(), new ArrayList<>());
                groupedFaceComponents.get(currentObjectName.get()).add(new FaceReference(faceVertexReferences, currentFaceMaterial.get()));
            }
        }
        
    }
    
    @Override
    public String getDefaultExtension() {
        return "obj";
    }
    
    //Types to make the data more comprehensible to future me
    private record FaceReference(List<FaceVertexReference> vertices, String material) { }
    private record FaceVertexReference(int vertex, int vertexTexture, int vertexNormal) { }
    
}
