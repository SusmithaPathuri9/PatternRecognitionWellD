package com.patternRecognition.test.model;

import com.patternRecognition.exception.InvalidLineException;
import com.patternRecognition.model.Line;
import com.patternRecognition.model.Plane;
import com.patternRecognition.model.Point;

import org.junit.Test;

import static org.junit.Assert.*;

public class PlaneTest {

	@Test
    public void addPoint() {
        Plane plane = new Plane();

        Point p = Point.of(5.0d, 1.0d);
        plane.addPoint(p);
        assertTrue(plane.getAllLines().isEmpty());

        p = Point.of(7.0d, 12.1d);
        plane.addPoint(p);
        assertTrue(plane.getAllLines().size() == 1);

        p = Point.of(7.0d, 1.0d);
        plane.addPoint(p);
        assertTrue(plane.getAllLines().size() == 3);

        p = Point.of(17.0d, 1.0d);
        plane.addPoint(p);
        assertTrue(plane.getAllLines().size() == 4);

        p = Point.of(27.0d, 1.0d);
        plane.addPoint(p);
        assertTrue(plane.getAllLines().size() == 5);

        p = Point.of(-4.22d, 6.55d);
        plane.addPoint(p);
        assertTrue(plane.getAllLines().size() == 10);

        assertEquals(plane.getLinesWithCollinearPoints(3).size(), 1);
        assertEquals(plane.getLinesWithCollinearPoints(2).size(), 10);

        p = Point.of(-0.8d, -0.8d);
        plane.addPoint(p);

        p = Point.of(0, 0d);
        plane.addPoint(p);

        p = Point.of(0.8, 0.8d);
        plane.addPoint(p);

        assertEquals(plane.getLinesWithCollinearPoints(3).size(), 2);

        plane.clear();
        p = Point.of(5.0d, 1.0d);
        plane.addPoint(p);
        assertTrue(plane.getAllLines().isEmpty());
    }

    @Test
    public void isPointCollinearToLineTest() throws InvalidLineException {
        Plane plane = new Plane();

        Point p = Point.of(5.0d, 1.0d);

        Point p1 = Point.of(6.0d, 1.0d);
        Point p2 = Point.of(7.0d, 1.0d);
        Line l = Line.of(p1, p2);

        boolean collinear = plane.isPointCollinearToLine(p, l);
        assertTrue(collinear);

        p = Point.of(8.5d, 1.5d);
        collinear = plane.isPointCollinearToLine(p, l);
        assertFalse(collinear);
    }

}
