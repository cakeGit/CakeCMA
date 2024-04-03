package com.cake.cmodels.converter_tool.user_interface.layout;


import java.awt.Component;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.LayoutManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Supplier;

public class ResponsiveGridLayout implements LayoutManager {
    
    List<List<CellResponsiveStrategy>> gridResponsiveStrategies = new ArrayList<>();
    Dimension size;
    
    public ResponsiveGridLayout(Dimension size) {
        this.size = size;
    }
    
    public void addRowStrategy(List<CellResponsiveStrategy> rowStrategy) {
        gridResponsiveStrategies.add(rowStrategy);
    }
    
    @Override
    public void layoutContainer(Container parent) {
        int columnCount = gridResponsiveStrategies.get(0).size();
        
        Dimension size = parent.getSize();
        
        //>SCALING
        
        //How much space is left in cells for weighted scales
        List<Integer> remainingHeights = arrayOfLengthAndContents(columnCount, () -> size.height);
        Integer remainingWidth = parent.getWidth();
        
        List<Integer> heightWeightSum = arrayOfLengthAndContents(columnCount, () -> 0);
        Integer widthWeightSum = 0;
        
        //Apply any fixed widths
        AtomicInteger columnIndex = new AtomicInteger();
        AtomicInteger rowIndex = new AtomicInteger();
        
        //Also put together a row - column list for later
        List<List<Component>> gridComponents = new ArrayList<>();
        
        for (Component component : parent.getComponents()) {
            
            CellResponsiveStrategy responsiveStrategy = getCellResponsiveStrategy(rowIndex, columnIndex);
            if (gridComponents.size() <= columnIndex.get())
                gridComponents.add(new ArrayList<>());
            gridComponents.get(columnIndex.get()).add(component);
            //Skip to next cell if this is merged, since merged cells don't have a corresponding component
            if (responsiveStrategy instanceof CellResponsiveStrategy.MergeAbove) {
                nextCell(columnIndex, rowIndex, columnCount);
                
                responsiveStrategy = getCellResponsiveStrategy(rowIndex, columnIndex);
    
                if (gridComponents.size() <= columnIndex.get())
                    gridComponents.add(new ArrayList<>());
                gridComponents.get(columnIndex.get()).add(null);
            }
            
            //Subtract widths and heights from remaining
            if (responsiveStrategy instanceof CellResponsiveStrategy.Scaled scaled) {
                if (scaled.getXStrategy() == CellResponsiveStrategy.ScalingStrategy.SET_SIZE) {
                    remainingWidth -= scaled.getX();
                } else if (scaled.getXStrategy() == CellResponsiveStrategy.ScalingStrategy.WEIGHT) {
                    //Width scales are relative to 1st only, merging stuff
                    if (rowIndex.get() == 0)
                        widthWeightSum += scaled.getX();
                }
                
                if (scaled.getYStrategy() == CellResponsiveStrategy.ScalingStrategy.SET_SIZE) {
                    remainingHeights.set(columnIndex.get(), remainingHeights.get(columnIndex.get()) - scaled.getY());
                } else if (scaled.getYStrategy() == CellResponsiveStrategy.ScalingStrategy.WEIGHT) {
                    heightWeightSum.set(columnIndex.get(), heightWeightSum.get(columnIndex.get()) + scaled.getY());
                }
            }
            
            nextCell(columnIndex, rowIndex, columnCount);
        }
    
        //>APPLY SCALES
        
        List<Double> heightWeight = new ArrayList<>();
        columnIndex.set(0);
        for (int weightSum : heightWeightSum) {
            heightWeight.add(remainingHeights.get(columnIndex.get()) / ((double) weightSum));
            columnIndex.incrementAndGet();
        }
        Double widthWeight = remainingWidth / ((double) widthWeightSum);
        
        rowIndex.set(0);
        columnIndex.set(0);
        
        for (Component component : parent.getComponents()) {
            CellResponsiveStrategy responsiveStrategy = getCellResponsiveStrategy(rowIndex, columnIndex);
            
            //Skip to next cell if this is merged, since merged cells don't have a corresponding component
            if (responsiveStrategy instanceof CellResponsiveStrategy.MergeAbove) {
                nextCell(columnIndex, rowIndex, columnCount);
                responsiveStrategy = getCellResponsiveStrategy(rowIndex, columnIndex);
            }
    
            //Apply widths and heights
            if (responsiveStrategy instanceof CellResponsiveStrategy.Scaled scaled) {
                if (scaled.getXStrategy() == CellResponsiveStrategy.ScalingStrategy.SET_SIZE) {
                    component.setSize(scaled.getX(), component.getHeight());
                } else if (scaled.getXStrategy() == CellResponsiveStrategy.ScalingStrategy.WEIGHT) {
                    component.setSize((int) (scaled.getX() * widthWeight), component.getHeight());
                }
    
                if (scaled.getYStrategy() == CellResponsiveStrategy.ScalingStrategy.SET_SIZE) {
                    component.setSize(component.getWidth(), scaled.getY());
                } else if (scaled.getYStrategy() == CellResponsiveStrategy.ScalingStrategy.WEIGHT) {
                    component.setSize(component.getWidth(), (int) (scaled.getY() * heightWeight.get(columnIndex.get())));
                }
            }
            
            nextCell(columnIndex, rowIndex, columnCount);
        }
        
        //> POSITIONING
        List<Double> heightPositions = arrayOfLengthAndContents(columnCount, () -> 0.0);
        //rowIndex.get() at this point is the last row index
        Double currentWidthPosition = 0.0;
    
        rowIndex.set(0);
        columnIndex.set(0);
        
        for (Component component : parent.getComponents()) {
            Double currentHeightPosition = heightPositions.get(columnIndex.get());
            
            CellResponsiveStrategy responsiveStrategy = getCellResponsiveStrategy(rowIndex, columnIndex);
    
            //Skip to next cell if this is merged, since merged cells don't have a corresponding component
            if (responsiveStrategy instanceof CellResponsiveStrategy.MergeAbove) {
                currentWidthPosition += gridComponents.get(rowIndex.get() -1).get(columnIndex.get()).getWidth();
                nextCell(columnIndex, rowIndex, columnCount);
                if (columnIndex.get() == 0) currentWidthPosition = 0.0;
            }
            
            component.setLocation((int) (double) currentWidthPosition, (int) (double) heightPositions.get(columnIndex.get()));
            
            heightPositions.set(columnIndex.get(), currentHeightPosition + component.getHeight());
            currentWidthPosition += component.getWidth();
    
            nextCell(columnIndex, rowIndex, columnCount);
            if (columnIndex.get() == 0) currentWidthPosition = 0.0;
        }
        
    }
    
    private <T> ArrayList<T> arrayOfLengthAndContents(int n, Supplier<T> supplier) {
        ArrayList<T> arrayList = new ArrayList<>();
        for (int i = 0; i < n; i++)
            arrayList.add(supplier.get());
        return arrayList;
    }
    
    private CellResponsiveStrategy getCellResponsiveStrategy(AtomicInteger rowIndex, AtomicInteger columnIndex) {
        return gridResponsiveStrategies.get(rowIndex.get()).get(columnIndex.get());
    }
    
    private void nextCell(AtomicInteger columnIndex, AtomicInteger rowIndex, int columnCount) {
        columnIndex.incrementAndGet();
        if (columnIndex.get() == columnCount) {
            columnIndex.set(0);
            rowIndex.incrementAndGet();
        }
    
    }
    
    @Override
    public Dimension preferredLayoutSize(Container parent) {
        return size;
    }
    @Override
    public Dimension minimumLayoutSize(Container parent) {
        return size;
    }
    
    @Override
    public void addLayoutComponent(String name, Component comp) {}
    @Override
    public void removeLayoutComponent(Component comp) {}
}
