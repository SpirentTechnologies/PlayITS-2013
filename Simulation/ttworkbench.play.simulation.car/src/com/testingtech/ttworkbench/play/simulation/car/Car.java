package com.testingtech.ttworkbench.play.simulation.car;

import java.util.ArrayList;

public class Car implements CarInterface {

	static int carID;

	double speed, maxSpeed, petrolUsage;
	private Warnings warning[];
	Sensors sensors;
	boolean engine;

	GPSpositionOfCar position = new GPSpositionOfCar(new GPSposition(0, 0), new GPSposition(0, 0), new ArrayList<GPSposition>());
	
	public Car(double speed, double maxSpeed, double tirePressure,
			double tankFill, double petrolUsage, boolean lightExists,
			boolean rainExists, boolean tankFillExists,
			boolean tirePressureExists, boolean espExists, boolean absExists,
			boolean airbagExists, boolean fogLightExists) {
		super();
		this.speed = speed;
		this.maxSpeed = maxSpeed;
		this.petrolUsage = petrolUsage;
		this.sensors = new Sensors(lightExists, rainExists, tankFillExists,
				airbagExists, espExists, absExists, fogLightExists, tirePressure, tankFill);
		carID++;
	}

	
	@Override
	public boolean toggleEngine() {
		if (!engine) {
			engine = true;
			sensors.toggleOn();
			return true;
		} else {
			engine = sensors.abs = sensors.esp = sensors.light = sensors.fogLight = sensors.breaks = false;
			sensors.toggleOff();
			return false;
		}
	}

	@Override
	public boolean toggleFogLight() {
		sensors.fogLight = !sensors.fogLight;
		return sensors.fogLight;
	}

	@Override
	public boolean toggleLight() {
		if (sensors.light) {
			sensors.fogLight = false;
		}
		sensors.light = !sensors.light;
		return sensors.light;
	}

	@Override
	public double setSpeed(double speed) {
		this.speed = speed;
		return this.speed;
	}

	@Override
	public double getSpeed() {
		return this.speed;
	}

	@Override
	public boolean toggleESP() {
		sensors.esp = !sensors.esp;
		return sensors.esp;
	}

	@Override
	public boolean toggleWarningSender() {
		// TODO toggleWaningSender
		return false;
	}

	@Override
	public boolean toggleWarningReceiver() {
		// TODO toggleWarningReceiver
		return false;
	}

	@Override
	public boolean toggleLightSensor() {
		sensors.light = !sensors.light;
		return sensors.light;
	}

	@Override
	public boolean toggleRainSensor() {
		sensors.rain = !sensors.rain;
		return sensors.rain;
	}

	@Override
	public GPSposition getGPSPosition() {
		return position.getCurrentPosition();
	}

	@Override
	public GPSposition setGPSPosition(GPSposition position) {
		this.position.setCurrentPosition(position);
		return this.position.getCurrentPosition();
	}

	@Override
	public double getTankFill() {
		return sensors.tankFillLevel;
	}

	@Override
	public double breaking() {
		double result = speed * speed / 200;
		return result;
	}

	@Override
	public void update() {
		// TODO update
		// check warnings[], Sensors, damage,

		// TODO change true to a variable so it can be closed reasonable
		while (true) {
			//get the new tankfill level
			//position;
			
			/*
			 * TODO calculate distance between the two KML gps coordinates let
			 * car drive that distanace
			 */
			
			//TODO get distance with sine and cosine do this in the GPSpositionOfCar class
		}
	}
}
