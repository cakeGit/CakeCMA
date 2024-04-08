package com.cake.cmodels.core.types;

/**
 * Generic Vec2 type (independent of mc) <br/>
 * Note that it is <strong>mutable</strong>, and returns are only for chaining, so that it may be modified by transformers, <br/>
 * Also note that a {@link GenericVec2#copy()} method is available <br/>
 * Subtypes of both Vec2 and Vec3 are used to avoid type misuse and for clarity in types<br>
 * but are safe to cast to super then down again,<br/>
 * */
public class GenericVec2 {
    float x, y;
    public GenericVec2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    //Copy method since this is
    public GenericVec2 copy() {
        return new GenericVec2(x, y);
    }

    //Vector functions
    /**Slower than {@link GenericVec2#getMagnitudeSquared()}*/
    public float getMagnitude() {
        return (float) Math.sqrt(this.getMagnitudeSquared());
    }

    /**Faster than {@link GenericVec2#getMagnitude()}*/
    public float getMagnitudeSquared() {
        return (float) (Math.pow(this.x, 2) + Math.pow(this.y, 2));
    }

    public float dot(GenericVec2 other) {
        return this.multiply(other).getSum();
    }

    public float getSum() {
        return this.x + this.y;
    }

    public GenericVec2 rotate(float angle) {
        this.x = (float) (Math.cos(angle) * this.x - Math.sin(angle) * this.y);
        this.y = (float) (Math.sin(angle) * this.x + Math.cos(angle) * this.y);
        return this;
    }

    //Component manipulation functions
    public GenericVec2 add(GenericVec2 other) {
        this.x += other.x;
        this.y += other.y;
        return this;
    }

    public GenericVec2 addAll(float l) {
        this.x += l;
        this.y += l;
        return this;
    }

    public GenericVec2 subtract(GenericVec2 other) {
        this.x -= other.x;
        this.y -= other.y;
        return this;
    }

    public GenericVec2 subtractAll(float l) {
        this.x -= l;
        this.y -= l;
        return this;
    }

    public GenericVec2 div(GenericVec2 other) {
        this.x /= other.x;
        this.y /= other.y;
        return this;
    }

    public GenericVec2 divAll(float l) {
        this.x /= l;
        this.y /= l;
        return this;
    }

    public GenericVec2 multiply(GenericVec2 other) {
        this.x *= other.x;
        this.y *= other.y;
        return this;
    }

    public GenericVec2 multiplyAll(float l) {
        this.x *= l;
        this.y *= l;
        return this;
    }

    //Direct setters
    public GenericVec2 setX(float x) {
        this.x = x;
        return this;
    }
    public GenericVec2 setY(float y) {
        this.y = y;
        return this;
    }

    //Getters
    public float getX() {
        return x;
    }
    public float getY() {
        return y;
    }

}
