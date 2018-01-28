package com.github.diogopeixoto.exception;

public class EntityNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 212165143917577092L;

	public EntityNotFoundException(String message) {
		super(message);
	}
}
