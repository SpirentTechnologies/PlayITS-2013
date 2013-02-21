package com.testingtech.ttworkbench.play.simulation.car;


public class Sensors {
	//active?
	boolean light, rain, tirePressure, tankFill, airbag;
	
	//sensor values
	double tankFillLevel;
	Tires tire;
	
	public Sensors(boolean light, boolean rain, boolean tirePressure,
			boolean tankFill, boolean airbag, double tankFillLevel, Tires tire) {
		this.light = light;
		this.rain = rain;
		this.tirePressure = tirePressure;
		this.tankFill = tankFill;
		this.airbag = airbag;
		this.tankFillLevel = tankFillLevel;
		this.tire = tire;
	}
	
}