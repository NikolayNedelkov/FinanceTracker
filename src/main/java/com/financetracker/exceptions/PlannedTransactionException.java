package com.financetracker.exceptions;

public class PlannedTransactionException extends Exception{

	private static final long serialVersionUID = -8197418287292567257L;
	
	public PlannedTransactionException() {
		super();
	}

	public PlannedTransactionException(String arg0, Throwable arg1, boolean arg2, boolean arg3) {
		super(arg0, arg1, arg2, arg3);
	}

	public PlannedTransactionException(String arg0, Throwable arg1) {
		super(arg0, arg1);
	}

	public PlannedTransactionException(String arg0) {
		super(arg0);
	}

	public PlannedTransactionException(Throwable arg0) {
		super(arg0);
	}

}
