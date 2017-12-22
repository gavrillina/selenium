package com.epam.mk.module4.exception;

public class CannotLoginException extends Exception {
	private static final long serialVersionUID = 1L;

	public CannotLoginException(String s) {
		super(s);
	}
}
