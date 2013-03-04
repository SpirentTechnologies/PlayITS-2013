package ttworkbench.play.widget.car.ui.model;

import ttworkbench.play.widget.car.ui.model.Warnings;

/**
 * 
 * @author kensan
 *
 */

public class WarningType {
	
	private float distance;
	private Warnings warning;
	private long priority;
	private GPSposition gpsPosition;
	/**
	 * @return the distance
	 */
	public float getDistance() {
		return distance;
	}
	/**
	 * @param distance the distance to set
	 */
	public void setDistance(float distance) {
		this.distance = distance;
	}
	/**
	 * @return the warning
	 */
	public Warnings getWarning() {
		return warning;
	}
	/**
	 * @param warning the warning to set
	 */
	public void setWarning(Warnings warning) {
		this.warning = warning;
	}
	/**
	 * @return the priority
	 */
	public long getPriority() {
		return priority;
	}
	/**
	 * @param l the priority to set
	 */
	public void setPriority(long l) {
		this.priority = l;
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
	
	
	
}
