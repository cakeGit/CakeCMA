package com.cake.ccma.converter_tool.user_interface.layout;

public class CellResponsiveStrategy {
    
    
    /**Use to mark a cell that is overwritten by another one, space is assigned to other components*/
    public static class Scaled extends CellResponsiveStrategy {
        
        ScalingStrategy xStrategy;
        ScalingStrategy yStrategy;
        
        int x;
        int y;
        
        public Scaled(ScalingStrategy xStrategy, ScalingStrategy yStrategy, int x, int y) {
            this.xStrategy = xStrategy;
            this.yStrategy = yStrategy;
            this.x = x;
            this.y = y;
        }
    
        public ScalingStrategy getXStrategy() {
            return xStrategy;
        }
        public ScalingStrategy getYStrategy() {
            return yStrategy;
        }
        
        public int getX() {
            return x;
        }
        public int getY() {
            return y;
        }
    
    }
    
    public static class MergeAbove extends CellResponsiveStrategy { }
    
    public enum ScalingStrategy {
        SET_SIZE, WEIGHT
    }
    
}
