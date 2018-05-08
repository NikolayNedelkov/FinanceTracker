package com.financetracker.exceptions;

public class CategoryException extends Exception {

	private static final long serialVersionUID = 2456422781738188272L;

	public CategoryException() {
		super();
	}

	public CategoryException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
		super(message, cause, enableSuppression, writableStackTrace);
	}

	public CategoryException(String message, Throwable cause) {
		super(message, cause);
	}

	public CategoryException(String message) {
		super(message);
	}

	public CategoryException(Throwable cause) {
		super(cause);
	}

}
