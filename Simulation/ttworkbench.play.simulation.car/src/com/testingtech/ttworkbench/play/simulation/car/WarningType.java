package com.testingtech.ttworkbench.play.simulation.car;


/**
 * 
 * @author kensan,andre
 * 
 */

public class WarningType {

	private Warnings warning;
	private GPSposition gpsPosition;
	private double distanceToCar;

	public WarningType(Warnings w, GPSposition gps) {
		warning = w;
		gpsPosition = gps;
	}

	/**
	 * @return the warning
	 */
	public Warnings getWarning() {
		return warning;
	}

	/**
	 * @param warning
	 *            the warning to set
	 */
	public void setWarning(Warnings warning) {
		this.warning = warning;
	}

	/**
	 * @return the priority
	 */
	public long getPriority() {
		return Warnings.getPriority(warning);
	}

	/**
	 * @return the gpsPosition
	 */
	public GPSposition getGpsPosition() {
		return gpsPosition;
	}

	/**
	 * @param gpsPosition
	 *            the gpsPosition to set
	 */
	public void setGpsPosition(GPSposition gpsPosition) {
		this.gpsPosition = gpsPosition;
	}

	public boolean equals(Object o) {
		if (o instanceof WarningType) {
			WarningType wt = (WarningType) o;
			if (wt.warning == this.warning) {
				if (this.getGpsPosition().compareTo(wt.getGpsPosition()) == 0) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * @return the distance
	 */
	public double getDistance() {
		return distanceToCar;
	}

	/**
	 * @param distance the distance to set
	 */
	public void setDistance(double distance) {
		this.distanceToCar = distance;
	}
}
