package com.testingtech.ttworkbench.play.simulation.car;

import java.util.ArrayList;

public class Car implements CarInterface {
	static int carID;
	final int customID;

	double speed, maxSpeed, petrolUsage;
	WarningType currentComingWarning;
	
	Sensors sensors;
	boolean engine;

	GPSpositionOfCar position;
	GPSposition currentPosition;
	// if this boolean is set the car will be removed from the simulation
	private boolean carDisposed = false;
	private String trackName;
	private double oldSpeed = Double.NaN;

	
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
	/*
	 * What is needed to be returned: tankFillLevel currentGPSposition
	 */
	public void update() {
		if (position == null) {
			System.out.println("No route set");
			return;
		}

		// check warnings[], Sensors, damage,
		Tupel<GPSposition, Double> gpsPositionOfCarUpdate;
		

		// ---------- Update Process starts here-------------//
		// update with speed and everything only if the engine is turned on and
		// there is still petrol in the tank
		if (engine && sensors.tankFillLevel > 0) {

			// get the new tankfill level
			gpsPositionOfCarUpdate = position.updateEverything(
					sensors.tankFillLevel, petrolUsage, speed);

			// update the current tankFill level
			sensors.tankFillLevel = gpsPositionOfCarUpdate.second;

			// update the current gpsPosition
			currentPosition = gpsPositionOfCarUpdate.first;
			// get next world positions warning
			// this functionality only
			currentComingWarning = position.getNextWarning();
		} else {
			// get the new tankfill level, if engine off then the car cannot
			// drive
			gpsPositionOfCarUpdate = position.updateEverything(
					sensors.tankFillLevel, petrolUsage, 0);

			// update the current tankFill level
			sensors.tankFillLevel = gpsPositionOfCarUpdate.second;

			// update the current gpsPosition
			currentPosition = gpsPositionOfCarUpdate.first;
			currentComingWarning = position.getNextWarning();
		}

		if (currentComingWarning != null
				&& currentPosition.latitude == currentComingWarning
						.getGpsPosition().latitude
				&& currentPosition.longitude == currentComingWarning
						.getGpsPosition().longitude) {
			// check warning and enable counter meassures
			if (currentComingWarning.equals(Warnings.ACCIDENT)
					|| currentComingWarning.equals(Warnings.DEER)) {
				doBreak();
			} else if (currentComingWarning.equals(Warnings.FOG)) {
				turnFogLampOn();
			} else if (currentComingWarning.equals(Warnings.ICE)) {
				// only reduce speed if oldSpeed is not set
				if (Double.isNaN(oldSpeed)) {
					doSlowDown(80);
				}
			} else if (currentComingWarning.equals(Warnings.RAIN)) {
				if (Double.isNaN(oldSpeed)) {
					doSlowDown(10);
				}

			} else if (currentComingWarning.equals(Warnings.SNOW)) {
				if (Double.isNaN(oldSpeed)) {
					doSlowDown(20);
				}
			}
		} else {
			if (oldSpeed >= 0) {
				speed = oldSpeed;
				oldSpeed = Double.NaN;
			}
			sensors.fogLight = false;
		}
	}

	private void doSlowDown(int i) {
		this.oldSpeed = speed;
		this.speed *= (i / 100);
	}

	private void turnFogLampOn() {
		this.sensors.fogLight = true;
	}

	private void doBreak() {
		this.oldSpeed = speed;
		this.speed = 0;
	}

	public void addWarning(WarningType wt) {
		this.position.addWarning(wt);
	}

	public boolean isCarDisposed() {
		return carDisposed;
	}

	public void disposeCar() {
		this.carDisposed = true;
	}

	public void setTrack(String trackName) {
		this.trackName = trackName;
	}

	public String getTrackName() {
		return trackName;
	}
}
