package ttworkbench.play.widget.car.ui.model;

public class CarStatusModel {
	
	private boolean engineStarted;
	private boolean aBSenabled;
	private boolean eSPenabled;
	private float speed;
	private GPSposition gpsPosition;
	private boolean lightEnabled;
	private boolean fogLightEnabled;
	private float fuel;
		
	/**
	 * @return the engineStarted
	 */
	public boolean isEngineStarted() {
		return engineStarted;
	}
	
	/**
	 * @param engineStarted the engineStarted to set
	 */
	public void setEngineStarted(boolean engineStarted) {
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
	public void setABSenabled(boolean aBSenabled) {
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
	public void setESPenabled(boolean eSPenabled) {
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
	public void setActualSpeed(float actualSpeed) {
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
	public void setGpsPosition(GPSposition gpsPosition) {
		this.gpsPosition = gpsPosition;
	}

	/**
	 * @return the fogLightEnabled
	 */
	public boolean isFogLightEnabled() {
		return fogLightEnabled;
	}

	/**
	 * @param fogLightEnabled the fogLightEnabled to set
	 */
	public void setFogLightEnabled(boolean fogLightEnabled) {
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
	public void setLightSensorEnabled(boolean lightSensorEnabled) {
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
	public void setFuel(float fuel) {
		this.fuel = fuel;
	}


	
	
}
