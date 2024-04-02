package com.cake.cmodels.core.model;

public class GenericVec3 {
    float x, y, z;
    public GenericVec3(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    //Copy method since this is
    public GenericVec3 copy() {
        return new GenericVec3(x, y, z);
    }

    //Vector functions
    /**Slower than {@link GenericVec3#getMagnitudeSquared()}*/
    public float getMagnitude() {
        return (float) Math.sqrt(this.getMagnitudeSquared());
    }

    /**Faster than {@link GenericVec3#getMagnitude()}*/
    public float getMagnitudeSquared() {
        return (float) (Math.pow(this.x, 2) + Math.pow(this.y, 2) + Math.pow(this.z, 2));
    }

    public float dot(GenericVec3 other) {
        return this.multiply(other).getSum();
    }

    public float getSum() {
        return this.x + this.y + this.z;
    }

    public GenericVec3 rotateX(float angle) {
        GenericVec2 rotated = new GenericVec2(y, z).rotate(angle);
        this.y = rotated.x;
        this.z = rotated.y;
        return this;
    }

    public GenericVec3 rotateY(float angle) {
        GenericVec2 rotated = new GenericVec2(x, z).rotate(angle);
        this.x = rotated.x;
        this.y = rotated.y;
        return this;
    }

    public GenericVec3 rotateZ(float angle) {
        GenericVec2 rotated = new GenericVec2(x, y).rotate(angle);
        this.x = rotated.x;
        this.y = rotated.y;
        return this;
    }

    //Component manipulation functions
    public GenericVec3 add(GenericVec3 other) {
        this.x += other.x;
        this.y += other.y;
        this.z += other.z;
        return this;
    }

    public GenericVec3 addAll(float l) {
        this.x += l;
        this.y += l;
        this.z += l;
        return this;
    }

    public GenericVec3 subtract(GenericVec3 other) {
        this.x -= other.x;
        this.y -= other.y;
        this.z -= other.z;
        return this;
    }

    public GenericVec3 subtractAll(float l) {
        this.x -= l;
        this.y -= l;
        this.z -= l;
        return this;
    }

    public GenericVec3 div(GenericVec3 other) {
        this.x /= other.x;
        this.y /= other.y;
        this.z /= other.z;
        return this;
    }

    public GenericVec3 divAll(float l) {
        this.x /= l;
        this.y /= l;
        this.z /= l;
        return this;
    }

    public GenericVec3 multiply(GenericVec3 other) {
        this.x *= other.x;
        this.y *= other.y;
        this.z *= other.z;
        return this;
    }

    public GenericVec3 multiplyAll(float l) {
        this.x *= l;
        this.y *= l;
        this.z *= l;
        return this;
    }

    //Direct setters
    public GenericVec3 setX(float x) {
        this.x = x;
        return this;
    }
    public GenericVec3 setY(float y) {
        this.y = y;
        return this;
    }
    public GenericVec3 setZ(float z) {
        this.z = z;
        return this;
    }

    //Getters
    public float getX() {
        return x;
    }
    public float getY() {
        return y;
    }
    public float getZ() {
        return z;
    }

}
