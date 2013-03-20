package ttworkbench.play.widget.car.ui.model;

/**
 * 
 * @author Björn, Andre
 *
 */
public class GPSposition {
	double latitude, longitude;

	/**
	 * The constructor
	 * @param latitude
	 * @param longitude
	 */
	public GPSposition(double latitude, double longitude) {
		this.latitude = latitude;
		this.longitude = longitude;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof GPSposition){
			
			if(this.latitude == ((GPSposition)obj).latitude && 
					this.longitude == ((GPSposition)obj).longitude){
				return true;
			}else{
				return false;
			}
		}
		return super.equals(obj);
	}

	/**
	 * @return latitude its value
	 */
	public double getLatitude() {
		return this.latitude;
	}
	
	/**
	 * @return longitude its value
	 */
	public double getLongitude() {
		return this.longitude;
	}	
}