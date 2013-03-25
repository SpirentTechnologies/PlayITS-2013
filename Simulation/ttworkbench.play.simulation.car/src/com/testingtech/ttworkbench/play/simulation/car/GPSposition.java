package com.testingtech.ttworkbench.play.simulation.car;
public class GPSposition {
	double latitude, longitude;

	public GPSposition(double longitude, double latitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}

	/**
	 * This method returns the difference between the latitudes or longitudes if
	 * they are not equal. Otherwise it returns 0
	 * 
	 * @param wt1
	 * @return
	 */
	public int compareTo(GPSposition gps) {
		final int EQUAL = 0;
		if (this.latitude != gps.latitude) {
			return (int) Math.signum(this.latitude
					- gps.latitude);
		}

		if (this.longitude != gps.longitude) {
			return (int) Math.signum(this.longitude
					- gps.longitude);
		}
		// lat1 == lat2 && long1 == long2
		return EQUAL;
	}

}
