package com.patternRecognition.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.patternRecognition.dto.PointDto;
import com.patternRecognition.service.CollinearPointsService;

import java.net.URI;
import java.util.Collection;

@RestController
public class CollinearPointsController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

    private final CollinearPointsService service;

    public CollinearPointsController(CollinearPointsService service) {
        this.service = service;
    }

    @PostMapping("/point")
    public ResponseEntity addPoint(@RequestBody PointDto dto) {
        logger.info("addPoint {}", dto);

        Assert.notNull(dto, "Point is mandatory");

        service.addPointToSpace(dto);

        final URI uri = URI.create("NoEndpointAvailable");
        return ResponseEntity.created(uri).build();
    }

    @GetMapping("/space")
    public ResponseEntity getAllPoints() {
        logger.info("getAllPoints");

        return retrieveResponseByCollection(service.getAllPoints());
    }

    @GetMapping("/lines/{n}")
    public ResponseEntity getLinesWithCollinearPoints(@PathVariable int n) {
        logger.info("getLinesWithCollinearPoints {}" , n);

        if(n < 2) {
            return ResponseEntity.badRequest().body("N should be greater than 1");
        }

        return retrieveResponseByCollection(service.getLinesWithCollinearPoints(n));
    }

    @DeleteMapping("/space")
    public ResponseEntity reset() {
        logger.info("reset space");

        service.resetSpace();
        return ResponseEntity.ok().build();
    }

    private ResponseEntity retrieveResponseByCollection(Collection c) {
        if(c.isEmpty()) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok(c);
    }
}
