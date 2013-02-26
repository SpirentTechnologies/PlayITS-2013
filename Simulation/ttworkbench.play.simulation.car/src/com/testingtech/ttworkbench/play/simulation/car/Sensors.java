package com.testingtech.ttworkbench.play.simulation.car;

public class Sensors {
	// active?
	boolean light, rain, tankFill, airbag, esp, abs, breaks, fogLight;

	// sensor values
	double tankFillLevel;
	Tires tire;

	// sensor exists
	boolean lightExists, rainExists, tankFillExists, airbagExists, espExists,
			absExists, fogLightExists;

	public Sensors(boolean lightExists, boolean rainExists,
			boolean tankFillExists, boolean airbagExists, boolean espExists,
			boolean absExists, boolean fogLightExists, Tires tire,
			double tankFillLevel) {
		super();
		this.lightExists = lightExists;
		this.rainExists = rainExists;
		this.tankFillExists = tankFillExists;
		this.airbagExists = airbagExists;
		this.tire = tire;
		this.espExists = espExists;
		this.absExists = absExists;
		this.tankFillLevel = tankFillLevel;
		this.fogLightExists = fogLightExists;
		light = rain = tankFill = airbag = breaks = fogLight = esp = abs = false;
	}

	public Sensors(boolean lightExists, boolean rainExists,
			boolean tankFillExists, boolean airbagExists, boolean espExists,
			boolean absExists, boolean fogLightExists, double tire,
			double tankFillLevel) {
		super();
		this.espExists = espExists;
		this.absExists = absExists;
		this.lightExists = lightExists;
		this.rainExists = rainExists;
		this.tankFillExists = tankFillExists;
		this.airbagExists = airbagExists;
		this.tire = new Tires(tire, tire, tire, tire);
		this.tankFillLevel = tankFillLevel;
		this.fogLightExists = fogLightExists;
		light = rain = tankFill = airbag = esp = fogLight = abs = breaks = false;
	}

	// toggle all available Sensors on
	public void toggleOn() {
		light = lightExists;
		rain = rainExists;
		tankFill = tankFillExists;
		airbag = airbagExists;
		abs = absExists;
		esp = espExists;
		fogLight = fogLightExists;
	}

	// toggle all Sensors off
	public void toggleOff() {
		light = rain = tankFill = airbag = esp = fogLight = abs = breaks = false;
	}
}