package com.epam.mk.module4.exception;

public class DraftNotFoundException extends Exception {
	private static final long serialVersionUID = 1L;

	public DraftNotFoundException(String message) {
		super(message);
	}
}
