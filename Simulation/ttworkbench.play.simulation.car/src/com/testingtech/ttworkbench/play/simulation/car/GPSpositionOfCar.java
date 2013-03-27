package com.testingtech.ttworkbench.play.simulation.car;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Queue;
/**
 * 
 * @author Björn, Kurosch
 *
 */

public class GPSpositionOfCar {
	private GPSposition nextGPSDestination;
	private GPSposition currentGPSPosition;
	private List<WarningType> warnings = new ArrayList<WarningType>();
	private Queue<GPSposition> positions = null;
	private long lastTime;
	private CarInterface icar;


	/**
	 * Class compute positions, warnings and attributes (f.e. fuel,) of cars,
	 * @param positions queue of positions for one track
	 * @param icar is an Interface to get access to attributes of Car.java
	 */
	public GPSpositionOfCar(Queue<GPSposition> positions, CarInterface icar) {
		this.icar = icar;
		this.currentGPSPosition = positions.poll();
		this.nextGPSDestination = positions.peek();
		this.positions = positions;
	}

	/**
	 * 
	 * @param tankFillLevel
	 * @param petrolUsage
	 * @param length
	 * @param dtime
	 * @return new fuel level with given consumption or time
	 */
	private Double tankFillUpdate(double tankFillLevel, double petrolUsage,
			double length, long dtime) {
		
		double actualUsage = 0;
		// calculate actual usage
		if(length == 0){
			//1.5l per hour, if engine is started
			actualUsage = 1.5/3600000.0 * dtime;
		}else{
			actualUsage = petrolUsage * length / 100;
		}
		// update tankfill
		tankFillLevel -= actualUsage;
		return tankFillLevel;
	}


	/**
	 * sets the new position to car, update the fuel level
	 * @param actualTime gets the actual system time
	 */
	public void updateEverything(long actualTime) {
		
		//destination reached, or no track loaded
		if(positions.isEmpty()){
			icar.setSpeed(0);
			return;
		}
		
		//at the beginning, nothing happens
		if(lastTime == 0){
			lastTime = actualTime;
			return;
		}
		
		//time between last update and this one in ms
		long dtime = actualTime - lastTime;
		
		double speed = icar.getSpeed();
		
		//compute the distance what are driven since the last update
		double driveDistance = speed * dtime / 3600000.0;
		
		//set new fuel status in relationship to the driven distance
		icar.setFuelLevel(tankFillUpdate(icar.getFuelLevel(), icar.getConsumption(), driveDistance, dtime));
		
		//calculate the distance between current position and next position
		double distanceBetweenCurrentandNext = calculateDistance(currentGPSPosition, nextGPSDestination);
		
		/*if driven distance greater then distance between two position, decrease driven distance with distance of
		 * current and next position. Set new current and next ones and repeat this until decreased driven distance is
		 * lesser.
		*/
		while(driveDistance > distanceBetweenCurrentandNext){
			driveDistance -= distanceBetweenCurrentandNext;
			currentGPSPosition = positions.poll();
			//track finished, no next position in queue
			if(positions.isEmpty()){
				return;
			}
			nextGPSDestination = positions.peek();
			
			distanceBetweenCurrentandNext = calculateDistance(currentGPSPosition, nextGPSDestination);
		}
		
		currentGPSPosition = calculateGPSPosition(driveDistance, currentGPSPosition, nextGPSDestination);
		
		lastTime = actualTime;


	}

	/**
	 * 
	 * @return current position of car
	 */
	public GPSposition getCurrentPosition() {
		return currentGPSPosition;
	}

	/**
	 * adds a new warning to list
	 * @param wt 
	 */
	public void addWarning(WarningType wt) {
		double distance = calculateDistance(wt.getGpsPosition(), currentGPSPosition);
		wt.setDistance(distance);
		warnings.add(wt);
		refreshWarningList();
		
	}
	
	/**
	 * sets more important warnings on top of list
	 * sorts for distance than priority
	 */
	public void refreshWarningList(){
		//sort List
				Collections.sort(warnings, new Comparator<WarningType>() {

					@Override
					public int compare(WarningType o1, WarningType o2) {
						
							double o1Distance = calculateDistance(o1.getGpsPosition(), currentGPSPosition);
							double o2Distance = calculateDistance(o2.getGpsPosition(), currentGPSPosition);
							if(Math.abs(o2Distance - o1Distance) < 1E-5) {
								return (int) (o2.getPriority() - o1.getPriority());
							}else{
								return (int) Math.signum(o2Distance - o1Distance);
							}
						}
				});
	}
	
	/**
	 * remove warning from list
	 * @param wt
	 */
	public void removeWarning(WarningType wt){
		warnings.remove(wt);
	}
	
	/**
	 * 
	 * @return current warning list
	 */
	public List<WarningType> getAllWarnings(){
		return warnings;
	}

	
	/**
	 * Calculate the air distance of two GPS Positions 
	 * @param src
	 * @param dest
	 * @return distance in km
	 * @see http://www.movable-type.co.uk/scripts/latlong.html
	 */
	public static double calculateDistance(GPSposition src, GPSposition dest){
		double r = 6371; // Earthradius in km
		double dLat = Math.toRadians(dest.latitude-src.latitude);
		double dLon = Math.toRadians(dest.longitude-src.longitude);
		double srcLat = Math.toRadians(src.latitude);
		double destLat = Math.toRadians(dest.latitude);
		
		//calculates the distance
		double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
		        Math.sin(dLon/2) * Math.sin(dLon/2) * Math.cos(srcLat) * Math.cos(destLat); 
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)); 
		return r * c;
	}
	
	
	
	/**
	 * Computes the GPS position between two GPS positions
	 * @param distance from the source gps position in the direction of the destination gps position,
	 * the distance must be non-negative and smaller than the full distance between src and dest
	 * @param src the source gps position (starting point)
	 * @param dest the destination gps position (end point)
	 * @return GPS position with given distance from startpostion
	 * @see http://www.movable-type.co.uk/scripts/latlong.html
	 */
	public static GPSposition calculateGPSPosition(double distance, GPSposition src, GPSposition dest){
	
		if(distance == 0){
			return src;
		}
		
		// the full distance is the length of the arc from the src to the dest position
		double fullDistance = calculateDistance(src, dest);
		// the distancePercentage is the percentage of the distance of the full distance
		double percentage = distance/fullDistance;
		
		// add the percentage of the full difference of latitudes to the src latitude
		double latitude = src.latitude + percentage * (dest.latitude - src.latitude);
		// add the percentage of the full difference of longitudes to the src longitude
		double longitude = src.longitude + percentage * (dest.longitude - src.longitude);
		// Example: 
		// src.lat == 1, dest.lat == 2, distPerc = 0.5 => 1 + (2-1) * 0.5 = 1.5
		// src.long == 2, dest.long == 0, distPerc == 0.5 => 2 + (0 - 2) * 0.5 = 1
		
		return new GPSposition(longitude, latitude);
		
//		double r = 6371; // Earthradius in km
//		double dLon = Math.toRadians(src.longitude-dest.longitude);
//
//		double srcLat = Math.toRadians(src.latitude);
//		double destLat = Math.toRadians(dest.latitude);
//		double srclon = Math.toRadians(src.longitude);
//		
//		//calculates the bearing
//		double y = Math.sin(dLon) * Math.cos(srcLat);
//		double x = Math.cos(destLat)*Math.sin(srcLat) -
//		        Math.sin(destLat)*Math.cos(srcLat)*Math.cos(dLon);
//		 double brng = Math.atan2(y, x)+Math.PI;
//		 
//		 //calculates the new GPSposition
//		 double ergLat = Math.asin( Math.sin(srcLat)*Math.cos(distance/r) + 
//	              Math.cos(srcLat)*Math.sin(distance/r)*Math.cos(brng) );
//		 double ergLon = srclon + Math.atan2(Math.sin(brng)*Math.sin(distance/r)*Math.cos(srcLat), 
//	                     Math.cos(distance/r)-Math.sin(srcLat)*Math.sin(ergLat));
//		 
//		 return new GPSposition(Math.toDegrees(ergLon), Math.toDegrees(ergLat));
	}

	/**
	 * @param currentPosition is the position is 
	 */
	public void removeWarningsWithCurrentPosition(GPSposition currentPosition) {
		// TODO Auto-generated method stub
		
	}
}
