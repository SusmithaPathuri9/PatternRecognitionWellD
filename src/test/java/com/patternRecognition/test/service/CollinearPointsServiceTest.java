package com.patternRecognition.test.service;

import org.junit.Before;
import org.junit.Test;

import com.patternRecognition.service.CollinearPointsService;

public class CollinearPointsServiceTest {

	private CollinearPointsService service;

    @Before
    public void setup() {
        service = new CollinearPointsService();
    }

    @Test(expected = IllegalArgumentException.class)
    public void addPointToSpaceNullTest() {
        service.addPointToSpace(null);
    }
}
