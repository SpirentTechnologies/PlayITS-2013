package ttworkbench.play.widget.car.ui.model;

import ttworkbench.play.widget.car.ui.model.Warnings;

/**
 * 
 * @author Björn, Andre
 *
 */

public class WarningType {
	
	private Warnings warning;
	private long priority;
	private GPSposition gpsPosition;

	/**
	 * @return warning its value
	 */
	public Warnings getWarning() {
		return warning;
	}
	/**
	 * @param warning value to be set
	 */
	public void setWarning(Warnings warning) {
		this.warning = warning;
	}
	/**
	 * @return priority its value
	 */
	public long getPriority() {
		return priority;
	}
	/**
	 * @param l the priority to be set
	 */
	public void setPriority(long l) {
		this.priority = l;
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