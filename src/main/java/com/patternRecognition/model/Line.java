package com.patternRecognition.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import com.patternRecognition.exception.InvalidLineException;

public class Line {

	private Set<Point> points;

    private Line(Set<Point> points) {
        this.points = points;
    }

    public static Line of(Point... points) throws InvalidLineException {

        final Set<Point> pointSet = Stream.of(points)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet());

        if(pointSet.size() < 2) {
            throw new InvalidLineException("At least two points are required to draw a line");
        }

        return new Line(pointSet);
    }

    public List<Point> getAllPoints() {
        return new ArrayList<Point>(points);
    }

    public void addPoint(Point p) {
        points.add(p);
    }

    @Override
    public String toString() {
        return "Line{" +
                "points=" + points +
                '}';
    }
}
