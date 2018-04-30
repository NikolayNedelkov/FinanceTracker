package com.financetracker.model.budget;

public class Budget {
	private double income;
	private double expese;
	
	public Budget(double income, double expese) {
		this.income = income;
		this.expese = expese;
	}

	public double getIncome() {
		return income;
	}

	public void setIncome(double income) {
		this.income = income;
	}

	public double getExpese() {
		return expese;
	}

	public void setExpese(double expese) {
		this.expese = expese;
	}
	
	
	
}
