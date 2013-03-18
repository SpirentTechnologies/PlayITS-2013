package com.testingtech.ttworkbench.play.simulation.car;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentLinkedQueue;

public class Car implements CarInterface {
	static int carID;
	final int customID;
	
	
	double speed, maxSpeed, petrolUsage;
	private ConcurrentLinkedQueue<Tupel<Warnings, GPSposition>> warning;
	Sensors sensors;
	boolean engine;

	GPSpositionOfCar position;
	GPSposition currentPosition;
	// if this boolean is set the car will be removed from the simulation
	private boolean destroyCar = false;

		
	public Car(double speed, double maxSpeed, double tirePressure,
			double tankFill, double petrolUsage, boolean lightExists,
			boolean rainExists, boolean tankFillExists,
			boolean tirePressureExists, boolean espExists, boolean absExists,
			boolean airbagExists, boolean fogLightExists,
			ArrayList<GPSposition> positions) {

		this.speed = speed;
		this.maxSpeed = maxSpeed;
		this.petrolUsage = petrolUsage;
		this.sensors = new Sensors(lightExists, rainExists, tankFillExists,
				airbagExists, espExists, absExists, fogLightExists,
				tirePressure, tankFill);
		carID++;
		customID = carID;
		currentPosition = positions.get(0);
		position = new GPSpositionOfCar(positions);
	}

	public void setCar(double speed, double maxSpeed,
			double tankFill, double petrolUsage, boolean lightExists,
			boolean rainExists, boolean tankFillExists,
			boolean tirePressureExists, boolean espExists, boolean absExists,
			boolean airbagExists, boolean fogLightExists) {

		this.speed = speed;
		this.maxSpeed = maxSpeed;
		this.petrolUsage = petrolUsage;
		this.sensors = new Sensors(lightExists, rainExists, tankFillExists,
				airbagExists, espExists, absExists, fogLightExists,
				new Tires(), tankFill);
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
		return position.getOldPosition();
	}

	@Override
	public GPSposition setGPSPosition(GPSposition position) {
		this.position.setOldPosition(position);
		return this.position.getOldPosition();
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
	/*
	 * What is needed to be returned: tankFillLevel currentGPSposition
	 */
	public void update() {
		// check warnings[], Sensors, damage,
		Tupel<GPSposition, Double> gpsPositionOfCarUpdate;
		Tupel<Warnings, GPSposition> nextWarning;
		
		//---------- Update Process starts here-------------//
		// get the new tankfill level
		gpsPositionOfCarUpdate = position.updateEverything(
				sensors.tankFillLevel, petrolUsage, speed);
		
		// update the current tankFill level
		sensors.tankFillLevel = gpsPositionOfCarUpdate.second;
		
		// update the current gpsPosition
		currentPosition = gpsPositionOfCarUpdate.first;
		nextWarning = warning.peek();
		
		if (currentPosition.latitude == nextWarning.second.latitude
				&& currentPosition.longitude == nextWarning.second.longitude) {
			// remove warning because it will be handled now
			warning.poll();
			
			// TODO check warning and enable counter meassures
		}
	}

	public Warnings getWarning() {
		return warning.peek().first;
	}

	public void setWarning(
			ConcurrentLinkedQueue<Tupel<Warnings, GPSposition>> warning) {
		this.warning = warning;
	}

	public void addWarning(Tupel<Warnings, GPSposition> t) {
		this.warning.add(t);
	}

	public boolean doDestroyCar() {
		return destroyCar;
	}

	public void setDestroyCar(boolean destroyCar) {
		this.destroyCar = destroyCar;
	}
	
	
}
