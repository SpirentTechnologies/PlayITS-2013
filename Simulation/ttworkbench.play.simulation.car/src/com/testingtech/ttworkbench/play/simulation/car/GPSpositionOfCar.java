package com.testingtech.ttworkbench.play.simulation.car;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Queue;


public class GPSpositionOfCar {
	private GPSposition nextGPSDestination;
	private GPSposition currentGPSPosition;
	private double angle;
	private List<WarningType> warnings = new ArrayList<WarningType>();
	private Queue<GPSposition> positions = null;
	private long lastTime;
	private CarInterface icar;


	/**
	 * Class compute positions, warnings and attributes (f.e. fuel,) of cars,
	 * @param positions
	 * @param icar
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


	// update the petrol usage state and the new gps position
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
		
		long dtime = actualTime - lastTime;
		
		// km
		double speed = icar.getSpeed();
		
		double driveDistance = speed * dtime / 3600000.0;
		
		icar.setFuelLevel(tankFillUpdate(icar.getFuelLevel(), icar.getConsumption(), driveDistance, dtime));
		
		//calculate new Position
		// TODO fix this with a real computation: latitude and longitude are degrees
		double distanceBetweenCurrentandNext = calculateDistance(currentGPSPosition, nextGPSDestination);
		
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

	public double getAngle() {
		return angle;
	}

	public GPSposition getCurrentPosition() {
		return currentGPSPosition;
	}

	public void setCurrentPosition(GPSposition currentPosition) {
		this.currentGPSPosition = currentPosition;
	}

	public GPSposition getNextGPSPosition() {
		return positions.poll();
	}

	public void addWarning(WarningType wt) {
		warnings.add(wt);
		refreshWarningList();
		
	}
	
	/**
	 * sets more important und short distance Warnings on top of list
	 */
	public void refreshWarningList(){
		//sort List
				Collections.sort(warnings, new Comparator<WarningType>() {

					@Override
					public int compare(WarningType o1, WarningType o2) {
						
							double o1Distance = calculateDistance(o1.getGpsPosition(), currentGPSPosition);
							double o2Distance = calculateDistance(o2.getGpsPosition(), currentGPSPosition);
							if(o1Distance == o2Distance) {
								if(o1.getPriority() == o2.getPriority()){
									return 0;
								}else{
									return (int) (o1.getPriority() - o2.getPriority());
								}
							}else{
								return (int) (o2Distance - o1Distance);
							}
						}
				});
	}
	
	public void removeWarning(WarningType wt){
		warnings.remove(wt);
	}
	
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
	// TODO document with the formula and a graphics
	public static double calculateDistance(GPSposition src, GPSposition dest){
		double r = 6371; // Earthradius in km
		double dLat = Math.toRadians(dest.latitude-src.latitude);
		double dLon = Math.toRadians(dest.longitude-src.longitude);
		double srcLat = Math.toRadians(src.latitude);
		double destLat = Math.toRadians(dest.latitude);

		double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
		        Math.sin(dLon/2) * Math.sin(dLon/2) * Math.cos(srcLat) * Math.cos(destLat); 
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)); 
		return r * c;
	}
	
	
	
	/**
	 * 
	 * @param distance
	 * @param src
	 * @param dest
	 * @return GPS position with given distance from startpostion
	 * @see http://www.movable-type.co.uk/scripts/latlong.html
	 */
	public static GPSposition calculateGPSPosition(double distance, GPSposition src, GPSposition dest){
	
		if(distance == 0){
			return src;
		}
		double r = 6371; // Earthradius in km
		double dLon = Math.toRadians(src.longitude-dest.longitude);

		double srcLat = Math.toRadians(src.latitude);
		double destLat = Math.toRadians(dest.latitude);
		double srclon = Math.toRadians(src.longitude);
		
		//calculates the bearing
		double y = Math.sin(dLon) * Math.cos(srcLat);
		double x = Math.cos(destLat)*Math.sin(srcLat) -
		        Math.sin(destLat)*Math.cos(srcLat)*Math.cos(dLon);
		 double brng = Math.atan2(y, x)+Math.PI;
		 
		 //calculates the new GPSposition
		 double ergLat = Math.asin( Math.sin(srcLat)*Math.cos(distance/r) + 
	              Math.cos(srcLat)*Math.sin(distance/r)*Math.cos(brng) );
		 double ergLon = srclon + Math.atan2(Math.sin(brng)*Math.sin(distance/r)*Math.cos(srcLat), 
	                     Math.cos(distance/r)-Math.sin(srcLat)*Math.sin(ergLat));
		 
		 return new GPSposition(Math.toDegrees(ergLon), Math.toDegrees(ergLat));
	}

	/**
	 * 
	 * @param currentPosition is the position is 
	 */
	public void removeWarningsWithCurrentPosition(GPSposition currentPosition) {
		// TODO Auto-generated method stub
		
	}
}
