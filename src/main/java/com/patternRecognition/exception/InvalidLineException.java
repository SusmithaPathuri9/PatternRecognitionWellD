package com.patternRecognition.exception;

public class InvalidLineException  extends RuntimeException {

	
	private static final long serialVersionUID = 1924771844476633195L;
	

	public InvalidLineException(final String message) {
        super(message);
    }
}
