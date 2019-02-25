package com.patternRecognition.dto;

public class PointDto {
	private double x;
    private double y;

    public PointDto() {
        super();
    }

    public PointDto(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

}
