package com.testingtech.ttworkbench.play.simulation.car;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URL;
import java.util.Queue;

import com.testingtech.util.FileUtil;

public class Car implements CarInterface {
	static int carID;
	final int customID;


	double speed = 0;
	double maxSpeed, petrolUsage;
	
	Sensors sensors;
	boolean engine;

	/**
	 * @return the engine
	 */
	public boolean isEngine() {
		return engine;
	}
	
	public boolean isABS(){
		return sensors.abs;
	}
	
	public boolean isESP(){
		return sensors.esp;
	}

	public boolean isFogLight(){
		return sensors.fogLight;
	}
	
	public boolean isLight(){
		return sensors.light;
	}
	
	
	/**
	 * @param engine the engine to set
	 */
	public synchronized void setEngine(boolean engine) {
		if(this.engine != engine){
			toggleEngine();
		}
	}

	GPSpositionOfCar position;
	GPSposition currentPosition;
	// if this boolean is set the car will be removed from the simulation
	private boolean carDisposed = false;
	private String trackName;
	private double oldSpeed = -1;
	private boolean slowedDown;


	
	public Car(double speed, double maxSpeed, double tirePressure,
			double tankFill, double petrolUsage, boolean lightExists,
			boolean rainExists, boolean tankFillExists,
			boolean tirePressureExists, boolean espExists, boolean absExists,
			boolean airbagExists, boolean fogLightExists,
			String trackName) {

		this.speed = speed;
		this.maxSpeed = maxSpeed;
		this.petrolUsage = petrolUsage;
		this.sensors = new Sensors(lightExists, rainExists, tankFillExists,
				airbagExists, espExists, absExists, fogLightExists,
				tirePressure, tankFill);
		carID++;
		customID = carID;
		this.trackName = trackName;
	}

	private void initTrack() {
		if (position == null) {
			Queue<GPSposition> positions = parseTrack(getTrackName());
			currentPosition = positions.peek();
			position = new GPSpositionOfCar(positions,this);
		}
	}

	private Queue<GPSposition> parseTrack(String trackName) {
		Queue<GPSposition> positions;
		File tmpFile = null;
		try {
			URL url = new URL(trackName);
			InputStream urlStream = url.openStream();
			tmpFile = File.createTempFile("http", ".kml");
			OutputStream out = new FileOutputStream(tmpFile);
			FileUtil.copy(urlStream, out);
			out.flush();

			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		positions =	new KMLtoGPSQueue(tmpFile).getPositions();
		return positions;
	}

	@Override
	public boolean toggleEngine() {
		if (!engine) {
			engine = true;
			sensors.toggleOn();
			return true;
		} else {
			engine = false;
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
	public void setSpeed(double speed) {
		this.speed = speed;
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
		initTrack();
		if (position == null) {
			System.out.println("No route set");
			return;
		}


		// get time for distance check
		long time = System.currentTimeMillis();


		// ---------- Update Process starts here-------------//
		// update with speed and everything only if the engine is turned on and
		// there is still petrol in the tank
		if (isEngine() && (getFuelLevel() > 0)) {

			position.updateEverything(time);

			position.refreshWarningList();

			
			//get init back
			turnFogLampOff();
			turnLightOff();
			if(slowedDown) doSpeedUP();
			
			//in 100m around a warning, the car change its behaviour temporarily
			for(WarningType wt : position.getAllWarnings()){
				if(wt.getDistance() <= 0.1){
					switch(wt.getWarning()){
					case ACCIDENT: 
					case DEER: 
					case ICE: doSlowDown(50); break;
					case FOG: {
						doSlowDown(50);
						turnFogLampOn(); 
						break;
					}
					case RAIN: turnLightOn(); break;
					case SNOW: {
						turnLightOn();
						doSlowDown(50);
						break;
					}
					}
				}
			}


		} else {

			if (engine)
				toggleEngine();
			setSpeed(0);	
		}
	}

	private void turnLightOn() {
		this.sensors.light = true;
		
	}
	
	/**
	 * return to standard configuration
	 */
	private void turnLightOff() {
		this.sensors.light = this.sensors.lightExists;
		
	}

	private void doSlowDown(int i) {
		if(!slowedDown){
			oldSpeed = speed;
			speed = speed * i/100;
			slowedDown = true;
		}
	}
	
	private void doSpeedUP(){
		this.speed = this.oldSpeed;
		this.oldSpeed = speed;
		slowedDown = false;
	}

	private void turnFogLampOn() {
		this.sensors.fogLight = true;
	}
	
	private void turnFogLampOff() {
		this.sensors.fogLight = this.sensors.fogLightExists;
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
		this.position = null;
	}

	public String getTrackName() {
		return trackName;
	}
	

	@Override
	public void setFuelLevel(double fuel) {
		sensors.tankFillLevel = fuel;
		
	}

	@Override
	public double getConsumption() {
		return petrolUsage;
	}

	@Override
	public double getFuelLevel() {
		return sensors.tankFillLevel;
	}

}
