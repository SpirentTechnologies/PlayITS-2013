package ttworkbench.play.widget.car.ui.model;

import java.io.File;

public class CarStatusModel {
	
	private boolean engineStarted;
	private boolean aBSenabled;
	private boolean eSPenabled;
	private float speed;
	private GPSposition gpsPosition;
	private boolean lightEnabled;
	private boolean fogLightEnabled;
	private float fuel;
	private float maxSpeed;
	private float fuelConsumption;
	


	private File trackFile;
		
	
	public float getMaxSpeed() {
		return maxSpeed;
	}

	public void setMaxSpeed(float maxSpeed) {
		this.maxSpeed = maxSpeed;
	}
	
	/**
	 * @return the engineStarted
	 */
	public boolean isEngineStarted() {
		return engineStarted;
	}
	
	/**
	 * @param engineStarted the engineStarted to set
	 */
	public synchronized void setEngineStarted(boolean engineStarted) {
		this.engineStarted = engineStarted;
	}
	/**
	 * @return the aBSenabled
	 */
	public boolean isABSenabled() {
		return aBSenabled;
	}
	
	/**
	 * @param aBSenabled the aBSenabled to set
	 */
	public synchronized void setABSenabled(boolean aBSenabled) {
		this.aBSenabled = aBSenabled;
	}
	
	/**
	 * @return the eSPenabled
	 */
	public boolean isESPenabled() {
		return eSPenabled;
	}
	
	/**
	 * @param eSPenabled the eSPenabled to set
	 */
	public synchronized void setESPenabled(boolean eSPenabled) {
		this.eSPenabled = eSPenabled;
	}

	/**
	 * @return the actualSpeed
	 */
	public double getActualSpeed() {
		return speed;
	}

	/**
	 * @param actualSpeed the actualSpeed to set
	 */
	public synchronized void setActualSpeed(float actualSpeed) {
		this.speed = actualSpeed;
	}


	/**
	 * @return the gpsPosition
	 */
	public GPSposition getGpsPosition() {
		return gpsPosition;
	}

	/**
	 * @param gpsPosition the gpsPosition to set
	 */
	public synchronized void setGpsPosition(GPSposition gpsPosition) {
		this.gpsPosition = gpsPosition;
	}

	/**
	 * @return the fogLightEnabled
	 */
	public boolean isFogLightSensorEnabled() {
		return fogLightEnabled;
	}

	/**
	 * @param fogLightEnabled the fogLightEnabled to set
	 */
	public synchronized void setFogLightEnabled(boolean fogLightEnabled) {
		this.fogLightEnabled = fogLightEnabled;
	}

	/**
	 * @return the lightSensorEnabled
	 */
	public boolean isLightSensorEnabled() {
		return lightEnabled;
	}

	/**
	 * @param lightSensorEnabled the lightSensorEnabled to set
	 */
	public synchronized void setLightSensorEnabled(boolean lightSensorEnabled) {
		this.lightEnabled = lightSensorEnabled;
	}

	/**
	 * @return the fuel
	 */
	public float getFuel() {
		return fuel;
	}

	/**
	 * @param fuel the fuel to set
	 */
	public synchronized void setFuel(float fuel) {
		this.fuel = fuel;
	}

	/**
	 * @return the trackFile
	 */
	public File getTrackFile() {
		return trackFile;
	}

	/**
	 * @param trackFile the trackFile to set
	 */
	public void setTrackFile(File trackFile) {
		this.trackFile = trackFile;
	}

	/**
	 * @return the fuelConsumption
	 */
	public float getFuelConsumption() {
		return fuelConsumption;
	}

	/**
	 * @param fuelConsumption the fuelConsumption to set
	 */
	public void setFuelConsumption(float fuelConsumption) {
		this.fuelConsumption = fuelConsumption;
	}


	
	
}
