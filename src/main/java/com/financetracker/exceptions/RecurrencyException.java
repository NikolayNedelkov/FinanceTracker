package com.financetracker.exceptions;

public class RecurrencyException extends Exception {

	private static final long serialVersionUID = 4202637523001234690L;

	public RecurrencyException() {
		super();
	}

	public RecurrencyException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public RecurrencyException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public RecurrencyException(String arg0) {
		super(arg0);
	}

	public RecurrencyException(Throwable arg0) {
		super(arg0);
	}

}
