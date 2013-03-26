package ttworkbench.play.widget.car.ui.model;




/**
 * @author Björn, Andre
 *
 */

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
	private long id = -1;
	


	private String track;
		
	/**
	 * 
	 * @return max speed of car
	 */
	public float getMaxSpeed() {
		return maxSpeed;
	}

	/**
	 * 
	 * @param maxSpeed as float is set
	 */
	public void setMaxSpeed(float maxSpeed) {
		this.maxSpeed = maxSpeed;
	}
	
	/**
	 * @return true if engine is started
	 */
	public boolean isEngineStarted() {
		return engineStarted;
	}
	
	/**
	 * @param engine status is set
	 */
	public synchronized void setEngineStarted(boolean engineStarted) {
		this.engineStarted = engineStarted;
	}
	/**
	 * @return true if ABS is enabled
	 */
	public boolean isABSenabled() {
		return aBSenabled;
	}
	
	/**
	 * @param ABS status is set
	 */
	public synchronized void setABSenabled(boolean aBSenabled) {
		this.aBSenabled = aBSenabled;
	}
	
	/**
	 * @return true if ESP is enabled
	 */
	public boolean isESPenabled() {
		return eSPenabled;
	}
	
	/**
	 * @param ESP status is set
	 */
	public synchronized void setESPenabled(boolean eSPenabled) {
		this.eSPenabled = eSPenabled;
	}

	/**
	 * @return current speed of car
	 */
	public double getCurrentSpeed() {
		return speed;
	}

	/**
	 * @param set current speed of car
	 */
	public synchronized void setActualSpeed(float speed) {
		this.speed = speed;
	}


	/**
	 * @return gpsPosition its value
	 */
	public GPSposition getGpsPosition() {
		return gpsPosition;
	}

	/**
	 * @param gpsPosition value to be set
	 */
	public synchronized void setGpsPosition(GPSposition gpsPosition) {
		this.gpsPosition = gpsPosition;
	}

	/**
	 * @return fogLightEnabled its value
	 */
	public boolean isFogLightSensorEnabled() {
		return fogLightEnabled;
	}

	/**
	 * @param set foglight status on or off
	 */
	public synchronized void setFogLightEnabled(boolean fogLightEnabled) {
		this.fogLightEnabled = fogLightEnabled;
	}

	/**
	 * @return true if light is enabled
	 */
	public boolean isLightEnabled() {
		return lightEnabled;
	}

	/**
	 * @param set light status
	 */
	public synchronized void setLightEnabled(boolean lightSensorEnabled) {
		this.lightEnabled = lightSensorEnabled;
	}

	/**
	 * @return fuel value of the tank
 	 */
	public float getFuel() {
		return fuel;
	}

	/**
	 * @param set fuel status of tank
	 */
	public synchronized void setFuel(float fuel) {
		this.fuel = fuel;
	}

	/**
	 * @return set track uri
	 */
	public String getTrack() {
		return track;
	}

	/**
	 * @param get track uri
	 */
	public void setTrackFile(String track) {
		this.track = track;
	}

	/**
	 * @return consumption as l/100km
	 */
	public float getFuelConsumption() {
		return fuelConsumption;
	}

	/**
	 * @param set consumption of car l/100km
	 */
	public void setFuelConsumption(float fuelConsumption) {
		this.fuelConsumption = fuelConsumption;
	}

	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}


}

