package ttworkbench.play.widget.car.ui.model;

import ttworkbench.play.widget.car.ui.model.EnumWarning;

/**
 * 
 * @author Björn, Andre
 *
 */

public class WarningType {
	
	private EnumWarning warning;
	private long priority;
	private GPSposition gpsPosition;

	/**
	 * @return warning its value
	 */
	public EnumWarning getWarning() {
		return warning;
	}
	/**
	 * @param warning value to be set
	 */
	public void setWarning(EnumWarning warning) {
		this.warning = warning;
	}
	/**
	 * @return priority its value
	 */
	public long getPriority() {
		return priority;
	}
	/**
	 * @param sets priority
	 */
	public void setPriority(long priority) {
		this.priority = priority;
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
	public void setGpsPosition(GPSposition gpsPosition) {
		this.gpsPosition = gpsPosition;
	}	
}