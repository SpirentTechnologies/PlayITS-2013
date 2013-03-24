package com.testingtech.ttworkbench.play.simulation.car;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;


public class GPSpositionOfCar {
	private double directionX, directionY;
	private GPSposition nextGPSDestination;
	private GPSposition currentPosition;
	private double angle;
	private List<WarningType> warnings = new ArrayList<WarningType>();
	//private List<GPSposition> positions = null;
	private int positionsCounter = 2;

	public int warningsSize(){
		return warnings.size();
	}
	

	public GPSpositionOfCar(ArrayList<GPSposition> positions) {
		this.nextGPSDestination = positions.get(1);
		this.currentPosition = positions.get(0);
//		this.positions = positions;
		for (GPSposition g : positions) {
			WarningType wt = new WarningType();
			wt.setGpsPosition(g);
			this.warnings.add(wt);
		}
		updateAngleAndDirections(nextGPSDestination, currentPosition);
	}

	public GPSpositionOfCar(GPSposition destinationPositions,
			GPSposition startPosition, ArrayList<GPSposition> positions) {
		this.nextGPSDestination = destinationPositions;
		this.currentPosition = startPosition;
//		this.positions = positions;
		for (GPSposition g : positions) {
			WarningType wt = new WarningType();
			wt.setGpsPosition(g);
			this.warnings.add(wt);
		}
		// calculate the angle needed for calculating the distance
		double lon1 = startPosition.longitude;
		double lon2 = destinationPositions.longitude;
		double lat1 = startPosition.latitude;
		double lat2 = destinationPositions.latitude;

		lat1 = Math.toRadians(lat1);
		lat2 = Math.toRadians(lat2);
		double longDiff = Math.toRadians(lon2 - lon1);
		double y = Math.sin(longDiff) * Math.cos(lat2);
		double x = Math.cos(lat1) * Math.sin(lat2) - Math.sin(lat1)
				* Math.cos(lat2) * Math.cos(longDiff);

		angle = (Math.toDegrees(Math.atan2(y, x)) + 360) % 360;

		// update the directions
		directionX = Math.abs(lon1) - Math.abs(lon2);
		directionY = Math.abs(lat1) - Math.abs(lat2);
	}

	private void updateAngleAndDirections(GPSposition destinationPositions,
			GPSposition startPosition) {
		this.nextGPSDestination = destinationPositions;
		this.currentPosition = startPosition;

		// calculate the angle needed for calculating the distance
		double lon1 = startPosition.longitude;
		double lon2 = destinationPositions.longitude;
		double lat1 = startPosition.latitude;
		double lat2 = destinationPositions.latitude;

		lat1 = Math.toRadians(lat1);
		lat2 = Math.toRadians(lat2);
		double longDiff = Math.toRadians(lon2 - lon1);
		double y = Math.sin(longDiff) * Math.cos(lat2);
		double x = Math.cos(lat1) * Math.sin(lat2) - Math.sin(lat1)
				* Math.cos(lat2) * Math.cos(longDiff);

		angle = (Math.toDegrees(Math.atan2(y, x)) + 360) % 360;

		// update the directions
		directionX = Math.abs(lon1) - Math.abs(lon2);
		directionY = Math.abs(lat1) - Math.abs(lat2);
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

	// calculate the next position of the car
	private GPSposition calculatePosition(double length,
			GPSposition currentPositon) {

		GPSposition newPos = new GPSposition(0, 0);
		double dist = length / 6371.0;
		double brng = Math.toRadians(angle);
		double lat1 = Math.toRadians(currentPositon.latitude);
		double lon1 = Math.toRadians(currentPositon.longitude);

		double lat2 = Math.asin(Math.sin(lat1) * Math.cos(dist)
				+ Math.cos(lat1) * Math.sin(dist) * Math.cos(brng));
		double a = Math.atan2(Math.sin(brng) * Math.sin(dist) * Math.cos(lat1),
				Math.cos(dist) - Math.sin(lat1) * Math.sin(lat2));
		// System.out.println("a = " + a);
		double lon2 = lon1 + a;

		lon2 = (lon2 + 3 * Math.PI) % (2 * Math.PI) - Math.PI;

		newPos.latitude = Math.toDegrees(lat2);
		newPos.longitude = Math.toDegrees(lon2);

		// check if destination reached

		double directionXlocal = Math.abs(newPos.longitude)
				- Math.abs(nextGPSDestination.longitude);
		double directionYlocal = Math.abs(newPos.latitude)
				- Math.abs(nextGPSDestination.latitude);

		if (directionX < 0 && directionXlocal >= 0) {
			if (directionY < 0 && directionYlocal >= 0) {
				newPos = nextGPSDestination;
			}
			if (directionY > 0 && directionYlocal <= 0) {
				newPos = nextGPSDestination;
			}
			if (directionY == 0 && directionYlocal <= 0) {
				newPos = nextGPSDestination;
			}
			if (directionY == 0 && directionYlocal >= 0) {
				newPos = nextGPSDestination;
			}
			if (directionY == 0 && directionYlocal == 0) {
				newPos = nextGPSDestination;
			}
		}
		if (directionX > 0 && directionXlocal <= 0) {
			if (directionY < 0 && directionYlocal >= 0) {
				newPos = nextGPSDestination;
			}
			if (directionY > 0 && directionYlocal <= 0) {
				newPos = nextGPSDestination;
			}
			if (directionY == 0 && directionYlocal <= 0) {
				newPos = nextGPSDestination;
			}
			if (directionY == 0 && directionYlocal >= 0) {
				newPos = nextGPSDestination;
			}
			if (directionY == 0 && directionYlocal == 0) {
				newPos = nextGPSDestination;
			}
		}

		if (directionX == 0 && directionXlocal == 0) {
			if (directionY < 0 && directionYlocal >= 0) {
				newPos = nextGPSDestination;
			}
			if (directionY > 0 && directionYlocal <= 0) {
				newPos = nextGPSDestination;
			}
			if (directionY == 0 && directionYlocal <= 0) {
				newPos = nextGPSDestination;
			}
			if (directionY == 0 && directionYlocal >= 0) {
				newPos = nextGPSDestination;
			}
			if (directionY == 0 && directionYlocal == 0) {
				newPos = nextGPSDestination;
			}
		}

		return newPos;
	}

	// update the petrol usage state and the new gps position
	public Tupel<GPSposition, Double> updateEverything(double tankFillLevel,
			double petrolUsage, double speed) {
		// already just add function call
		double length = speed * 0.000277777778; // km/s

		Double fillLevel = tankFillUpdate(tankFillLevel, petrolUsage, length);
		
		// set new current position for next update
		currentPosition = calculatePosition(length, currentPosition);

		// if nextGPSDestination is reached or passed, set new nextGPSDestination
		//is reached, when distance for the next Position is larger then the current one
		if (calculateDistance(currentPosition, nextGPSDestination) < 
				calculateDistance(calculatePosition(length, currentPosition), nextGPSDestination)) {
			
			nextGPSDestination.latitude = warnings.get(positionsCounter).getGpsPosition()
					.latitude;
			
			nextGPSDestination.longitude = warnings.get(positionsCounter).getGpsPosition()
					.longitude;
			
			updateAngleAndDirections(currentPosition, nextGPSDestination);
			positionsCounter++;
		}
		
		// if warning is reached or passed, remove
		Iterator<WarningType> iterator = warnings.iterator();
		
		WarningType warning;
		while(iterator.hasNext()){
			warning = iterator.next();
			GPSposition warningPos = warning.getGpsPosition();
			
			if(calculateDistance(currentPosition, warningPos) < 
				calculateDistance(calculatePosition(length, currentPosition), warningPos)){
				warnings.remove(warning);
	
			}
		}
		
		return new Tupel<GPSposition, Double>(currentPosition, fillLevel);
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
		return warnings.get(positionsCounter + 1).getGpsPosition();
	}

	public void addWarning(WarningType wt) {
		for (int i = 0; i < warnings.size()-1; i++) {
			if (warnings.get(i).getGpsPosition().latitude == wt.getGpsPosition().latitude && warnings.get(i).getGpsPosition().longitude == wt.getGpsPosition().longitude){
				warnings.set(i, wt);
			}
		}
		wt.setPriority(Warnings.getPriority(wt.getWarning()));
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
	
	/**
	 * @return the nearest warning
	 */
	public WarningType getNextWarning() {
		if(warnings.isEmpty()) return null;
		return warnings.get(0);
	}

	
	/**
	 * Calculate the air distance of two GPS Positions 
	 * @param src
	 * @param dest
	 * @return distance
	 */
	public double calculateDistance(GPSposition src, GPSposition dest){
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
}
