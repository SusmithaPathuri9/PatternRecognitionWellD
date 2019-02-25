package com.patternRecognition.model;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class Plane {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

    private Set<Point> points;
    private Map<Integer, List<Line>> lines;

    public Plane() {
        init();
    }

    /**
     * When adding a new point, the data structure recomputes all possible lines.
     *
     */
    public void addPoint(final Point point) {
        Assert.notNull(point, "Point cannot be null");

        logger.info("Adding point {} to plane", point);

        if(!points.contains(point)) {
            recomputeLines(point);
            this.points.add(point);
        }
    }

    public Set<Point> getPoints() {
        return points;
    }

    public void clear() {
        init();
    }

    public List<Line> getLinesWithCollinearPoints(int n) {
        return this.lines.entrySet()
                .stream()
                .filter(v -> v.getKey() >= n)
                .map(Map.Entry::getValue)
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    public boolean isPointCollinearToLine(final Point p, final Line line) {
        final List<Point> linePoints = line.getAllPoints();
        final Point p1 = linePoints.get(0);
        final Point p2 = linePoints.get(1);

        double result = (p2.getY() - p1.getY()) * p.getX();
        result += (p1.getX() - p2.getX()) * p.getY();
        result += (p2.getX() * p1.getY() - p1.getX() * p2.getY());

        return result == 0d;
    }

    //Unrequired method (used in Test) useful to check all available lines
    public List<Line> getAllLines() {
        return this.lines.values()
                .stream()
                .flatMap(Collection::stream)
                .collect(Collectors.toList());
    }

    private void init() {
        this.points = new HashSet<>();
        this.lines = new HashMap<>();
    }

    /**
     * When this method gets called, it checks if any existing Line can be extended,
     * after that it draws any new line obtained with the new point.
     *
     */
    private void recomputeLines(final Point point) {
        Map<Integer, List<Line>> lines = new HashMap<>();

        this.lines.values()
                .stream()
                .flatMap(Collection::stream)
                .forEach(line -> {
                    if(isPointCollinearToLine(point, line)) {
                        line.addPoint(point);
                    }
                    List<Line> lineList = lines.get(line.getAllPoints().size());
                    if(lineList == null) {
                        lineList = new ArrayList<>();
                    }
                    lineList.add(line);

                    logger.info("Recomputing line {}", line);
                    lines.put(line.getAllPoints().size(), lineList);
                });

        this.lines = lines;
        drawNewLines(point);
    }

    //Drawing all lines composed by two points only
    private void drawNewLines(final Point point) {
        final List<Line> twoPointsLine;
        if(this.lines.get(2) != null) {
            twoPointsLine = this.lines.get(2);
        } else {
            twoPointsLine = new ArrayList<>();
        }

        this.points
                .forEach(p -> {
                    if(! alreadyInLine(point, p)) {
                        logger.info("Drawing new line between point {} and {}", point, p);
                        twoPointsLine.add(Line.of(point, p));
                    }
                });

        if(!twoPointsLine.isEmpty()) {
            this.lines.put(2, twoPointsLine);
        }
    }

    private boolean alreadyInLine(Point p1, Point p2) {
        List<Point> points = new ArrayList<>();
        points.add(p1);
        points.add(p2);

        return this.lines.values()
                .stream()
                .flatMap(Collection::stream)
                .anyMatch(line -> line.getAllPoints().containsAll(points));
    }

}
