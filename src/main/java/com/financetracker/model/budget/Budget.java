package com.financetracker.model.budget;

public class Budget {
	private double totalIncome;
	private double totalExpense;
	
	public Budget() {
		
	}
	
	public Budget(double income, double expese) {
		this.totalIncome = income;
		this.totalExpense = expese;
	}

	public double getTotalIncome() {
		return totalIncome;
	}

	public void setTotalIncome(double income) {
		this.totalIncome = income;
	}

	public double gettotalExpense() {
		return totalExpense;
	}

	public void settotalExpense(double expese) {
		this.totalExpense = expese;
	}
	
	
	
}
