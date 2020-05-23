package com.example.gsonobj;

public class MainObj {
	private Double temp;
	private int pressure;
	private int humidity;
	private Double feels_like;

	public MainObj(Double temp, int pressure, int humidity) {
		super();
		this.temp = temp;
		this.pressure = pressure;
		this.humidity = humidity;
	}

	public MainObj() {
		super();
	}

	public Double getTemp() {
		return temp;
	}

	public void setTemp(Double temp) {
		this.temp = temp;
	}

	public int getPressure() {
		return pressure;
	}

	public void setPressure(int pressure) {
		this.pressure = pressure;
	}

	public int getHumidity() {
		return humidity;
	}

	public void setHumidity(int humidity) {
		this.humidity = humidity;
	}

	public Double getFeels_like() {
		return feels_like;
	}

	public void setFeels_like(Double feels_like) {
		this.feels_like = feels_like;
	}

}
