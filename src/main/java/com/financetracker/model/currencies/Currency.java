package com.financetracker.model.currencies;

public class Currency {
	private int id;
	private String currencyType;
	private float coeffitient;
	public Currency(int id, String currencyType, float coeffitient) {
		super();
		this.id = id;
		this.currencyType = currencyType;
		this.coeffitient = coeffitient;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getCurrencyType() {
		return currencyType;
	}
	public void setCurrencyType(String currencyType) {
		this.currencyType = currencyType;
	}
	public float getCoeffitient() {
		return coeffitient;
	}
	public void setCoeffitient(float coeffitient) {
		this.coeffitient = coeffitient;
	}
	
}
