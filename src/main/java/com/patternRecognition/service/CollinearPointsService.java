package com.patternRecognition.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.patternRecognition.dto.PointDto;
import com.patternRecognition.model.Plane;
import com.patternRecognition.model.Point;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class CollinearPointsService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

    private Plane plane = new Plane();

    private Function<Point, PointDto> pointDtoMapper = p -> new PointDto(p.getX(), p.getY());

    public void addPointToSpace(final PointDto dto) {
        logger.info("addPointToSpace {}" , dto);

        Assert.notNull(dto, "point cannot be null");

        plane.addPoint(Point.of(dto.getX(), dto.getY()));
    }

    public Collection<PointDto> getAllPoints() {
        logger.info("getAllPoints");

        return this.plane.getPoints()
                .stream()
                .map(pointDtoMapper)
                .collect(Collectors.toSet());
    }

    public List<List<PointDto>> getLinesWithCollinearPoints(int n) {
        logger.info("getLinesWithCollinearPoints {}", n);

        return this.plane.getLinesWithCollinearPoints(n)
                .stream()
                .map(l -> l.getAllPoints()
                        .stream()
                        .map(pointDtoMapper)
                        .collect(Collectors.toList()))
                .collect(Collectors.toList());
    }

    public void resetSpace() {
        logger.info("resettingSpace {}");

        plane.clear();
    }
}
