package ttworkbench.play.widget.car.ui.model;

public class CarStatusModel {
	
	private boolean engineStarted;
	private boolean aBSenabled;
	private boolean eSPenabled;
	private boolean lightenabled;
	private int actualTemperature;
	private double actualSpeed;
	
	
	
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
		return actualSpeed;
	}

	/**
	 * @param actualSpeed the actualSpeed to set
	 */
	public void setActualSpeed(double actualSpeed) {
		this.actualSpeed = actualSpeed;
	}


	/**
	 * @return the lightenabled
	 */
	public boolean isLightenabled() {
		return lightenabled;
	}

	/**
	 * @param lightenabled the lightenabled to set
	 */
	public void setLightenabled(boolean lightenabled) {
		this.lightenabled = lightenabled;
	}

	/**
	 * @return the actualTemperature
	 */
	public int getActualTemperature() {
		return actualTemperature;
	}

	/**
	 * @param actualTemperature the actualTemperature to set
	 */
	public void setActualTemperature(int actualTemperature) {
		this.actualTemperature = actualTemperature;
	}


	
	
}
