package com.testingtech.ttworkbench.play.simulation.car;

import com.testingtech.ttworkbench.play.simulation.car.Warnings;;

/**
 * 
 * @author kensan,andre
 *
 */

public class WarningType {
	
	private Warnings warning;
	private long priority;
	private GPSposition gpsPosition;

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
	
	public int compareTo(WarningType wt1){
		final int BEFORE = -1;
		final int EQUAL = 0;
		final int AFTER = 1;
		
		if(this.getGpsPosition().latitude == wt1.getGpsPosition().latitude && this.getGpsPosition().longitude == wt1.getGpsPosition().longitude){
			return EQUAL;
		}
		if(this.getGpsPosition().latitude <= wt1.getGpsPosition().latitude && this.getGpsPosition().longitude <= wt1.getGpsPosition().longitude){
			return BEFORE;
		}
		if(this.getGpsPosition().latitude >= wt1.getGpsPosition().latitude && this.getGpsPosition().longitude >= wt1.getGpsPosition().longitude){
			return AFTER;
		}
		return EQUAL;
	}
}
