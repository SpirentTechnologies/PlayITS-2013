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
		
	
	public float getMaxSpeed() {
		return maxSpeed;
	}

	public void setMaxSpeed(float maxSpeed) {
		this.maxSpeed = maxSpeed;
	}
	
	/**
	 * @return engineStarted its value
	 */
	public boolean isEngineStarted() {
		return engineStarted;
	}
	
	/**
	 * @param engineStarted value to be set
	 */
	public synchronized void setEngineStarted(boolean engineStarted) {
		this.engineStarted = engineStarted;
	}
	/**
	 * @return aBSenabled its value
	 */
	public boolean isABSenabled() {
		return aBSenabled;
	}
	
	/**
	 * @param aBSenabled value to be set
	 */
	public synchronized void setABSenabled(boolean aBSenabled) {
		this.aBSenabled = aBSenabled;
	}
	
	/**
	 * @return eSPenabled its value
	 */
	public boolean isESPenabled() {
		return eSPenabled;
	}
	
	/**
	 * @param eSPenabled value to be set
	 */
	public synchronized void setESPenabled(boolean eSPenabled) {
		this.eSPenabled = eSPenabled;
	}

	/**
	 * @return actualSpeed its value
	 */
	public double getActualSpeed() {
		return speed;
	}

	/**
	 * @param actualSpeed value to be set
	 */
	public synchronized void setActualSpeed(float actualSpeed) {
		this.speed = actualSpeed;
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
	 * @param fogLightEnabled value to be set
	 */
	public synchronized void setFogLightEnabled(boolean fogLightEnabled) {
		this.fogLightEnabled = fogLightEnabled;
	}

	/**
	 * @return lightSensorEnabled its value
	 */
	public boolean isLightSensorEnabled() {
		return lightEnabled;
	}

	/**
	 * @param lightSensorEnabled value to be set
	 */
	public synchronized void setLightSensorEnabled(boolean lightSensorEnabled) {
		this.lightEnabled = lightSensorEnabled;
	}

	/**
	 * @return fuel its value
	 */
	public float getFuel() {
		return fuel;
	}

	/**
	 * @param fuel value to be set
	 */
	public synchronized void setFuel(float fuel) {
		this.fuel = fuel;
	}

	/**
	 * @return the trackFile
	 */
	public String getTrack() {
		return track;
	}

	/**
	 * @param trackFile the trackFile to set
	 */
	public void setTrackFile(String track) {
		this.track = track;
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

