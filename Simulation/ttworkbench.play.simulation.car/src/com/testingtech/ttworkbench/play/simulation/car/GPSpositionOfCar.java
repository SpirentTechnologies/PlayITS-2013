package com.testingtech.ttworkbench.play.simulation.car;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Queue;


public class GPSpositionOfCar {
	private GPSposition nextGPSDestination;
	private GPSposition currentPosition;
	private double angle;
	private List<WarningType> warnings = new ArrayList<WarningType>();
	private Queue<GPSposition> positions = null;
	private long lastTime;
	private CarInterface icar;
	private double actualDistance; //distance between two gps coordinates


	public GPSpositionOfCar(Queue<GPSposition> positions, CarInterface icar) {
		this.icar = icar;
		this.currentPosition = positions.poll();
		this.nextGPSDestination = positions.peek();
		actualDistance = calculateDistance(currentPosition, nextGPSDestination);
		this.positions = positions;
	}


	// update the tank fill level of the car
	private Double tankFillUpdate(double tankFillLevel, double petrolUsage,
			double length) {
		// calculate actual distance
		double actualUsage = petrolUsage * length / 100;
		// update tankfill
		tankFillLevel -= actualUsage;
		return tankFillLevel;
	}


	// update the petrol usage state and the new gps position
	public void updateEverything(long actualTime) {
		// already just add function call
		if(positions.isEmpty()){
			icar.setSpeed(0);
		}
		
		//at the beginning, nothing happens
		if(lastTime == 0){
			lastTime = actualTime;
			return;
		}
		
		long dtime = actualTime - lastTime;
		
		// km
		double driveDistance = icar.getSpeed() * dtime / 3600000.0;
		
		//calculate new Position
		// TODO fix this with a real computation: latitude and longitude are degrees
		double latitude = currentPosition.latitude + driveDistance*(nextGPSDestination.latitude - currentPosition.latitude);
		double longitude = currentPosition.longitude + driveDistance*(nextGPSDestination.longitude - currentPosition.longitude);
		currentPosition = new GPSposition(longitude, latitude);
		
		lastTime = actualTime;
		
		icar.setFuelLevel(tankFillUpdate(icar.getFuelLevel(), icar.getConsumption(), driveDistance));
		
		if(actualDistance <= driveDistance){
			
			currentPosition = positions.poll();
			//track done
			if(positions.isEmpty())return;
			
			nextGPSDestination = positions.peek();
			double rest = driveDistance - actualDistance;
			latitude = currentPosition.latitude + rest*(nextGPSDestination.latitude - currentPosition.latitude);
			longitude = currentPosition.longitude + rest*(nextGPSDestination.longitude - currentPosition.longitude);
			currentPosition = new GPSposition(longitude, latitude);
			
		}
		actualDistance = calculateDistance(currentPosition, nextGPSDestination);
		
	}

	public double getAngle() {
		return angle;
	}

	public GPSposition getCurrentPosition() {
		return currentPosition;
	}

	public void setCurrentPosition(GPSposition currentPosition) {
		this.currentPosition = currentPosition;
	}

	public GPSposition getNextGPSPosition() {
		return positions.poll();
	}

	public void addWarning(WarningType wt) {
//		for (int i = 0; i < positions.size()-1; i++) {
//			if (positions.get(i).getGpsPosition().latitude == wt.getGpsPosition().latitude && positions.get(i).getGpsPosition().longitude == wt.getGpsPosition().longitude){
//				positions.set(i, wt);
//			}
//		}
		warnings.add(wt);
		
		//sort List
		Collections.sort(warnings, new Comparator<WarningType>() {

			@Override
			public int compare(WarningType o1, WarningType o2) {
				
					double o1Distance = calculateDistance(o1.getGpsPosition(), currentPosition);
					double o2Distance = calculateDistance(o2.getGpsPosition(), currentPosition);
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
	 * @param currentPosition is the position is 
	 */
	public void removeWarningsWithCurrentPosition(GPSposition currentPosition) {
		// TODO Auto-generated method stub
		
	}
}
