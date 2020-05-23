package com.example.gsonobj;

public class ResponseObjCurrent {
	private MainObj main;
	private int visibility;
	private Wind wind;
	public ResponseObjCurrent() {
		super();
	}
	public MainObj getMain() {
		return main;
	}
	public void setMain(MainObj main) {
		this.main = main;
	}
	public int getVisibility() {
		return visibility;
	}
	public void setVisibility(int visibility) {
		this.visibility = visibility;
	}
	public Wind getWind() {
		return wind;
	}
	public void setWind(Wind wind) {
		this.wind = wind;
	}
}
