package com.testingtech.ttworkbench.play.simulation.car;

import java.util.ArrayList;

public class GPSpositionOfCar {
	private double directionX, directionY;
	private GPSposition worldDestination;
	private GPSposition oldPosition;
	private double angle;
	//TODO make it possible to have multiple warnings per position
	private ArrayList<WarningType> positions = new ArrayList<WarningType>();
	private int positionsCounter = 2;
	private WarningType nextWarning;

	public GPSpositionOfCar(ArrayList<GPSposition> positions) {
		this.worldDestination = positions.get(1);
		this.oldPosition = positions.get(0);
		for (GPSposition g : positions) {
			WarningType wt = new WarningType();
			wt.setGpsPosition(g);
			this.positions.add(wt);
		}
		updateAngleAndDirections(worldDestination, oldPosition);
	}

	public GPSpositionOfCar(GPSposition destinationPositions,
			GPSposition startPosition, ArrayList<GPSposition> positions) {
		this.worldDestination = destinationPositions;
		this.oldPosition = startPosition;
		for (GPSposition g : positions) {
			WarningType wt = new WarningType();
			wt.setGpsPosition(g);
			this.positions.add(wt);
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
		this.worldDestination = destinationPositions;
		this.oldPosition = startPosition;

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

	// calculate the new position of the car
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
				- Math.abs(worldDestination.longitude);
		double directionYlocal = Math.abs(newPos.latitude)
				- Math.abs(worldDestination.latitude);

		if (directionX < 0 && directionXlocal >= 0) {
			if (directionY < 0 && directionYlocal >= 0) {
				newPos = worldDestination;
			}
			if (directionY > 0 && directionYlocal <= 0) {
				newPos = worldDestination;
			}
			if (directionY == 0 && directionYlocal <= 0) {
				newPos = worldDestination;
			}
			if (directionY == 0 && directionYlocal >= 0) {
				newPos = worldDestination;
			}
			if (directionY == 0 && directionYlocal == 0) {
				newPos = worldDestination;
			}
		}
		if (directionX > 0 && directionXlocal <= 0) {
			if (directionY < 0 && directionYlocal >= 0) {
				newPos = worldDestination;
			}
			if (directionY > 0 && directionYlocal <= 0) {
				newPos = worldDestination;
			}
			if (directionY == 0 && directionYlocal <= 0) {
				newPos = worldDestination;
			}
			if (directionY == 0 && directionYlocal >= 0) {
				newPos = worldDestination;
			}
			if (directionY == 0 && directionYlocal == 0) {
				newPos = worldDestination;
			}
		}

		if (directionX == 0 && directionXlocal == 0) {
			if (directionY < 0 && directionYlocal >= 0) {
				newPos = worldDestination;
			}
			if (directionY > 0 && directionYlocal <= 0) {
				newPos = worldDestination;
			}
			if (directionY == 0 && directionYlocal <= 0) {
				newPos = worldDestination;
			}
			if (directionY == 0 && directionYlocal >= 0) {
				newPos = worldDestination;
			}
			if (directionY == 0 && directionYlocal == 0) {
				newPos = worldDestination;
			}
		}

		return newPos;
	}

	// update the petrol usage state and the new gps position
	public Tupel<GPSposition, Double> updateEverything(double tankFillLevel,
			double petrolUsage, double speed) {
		// already just add function call
		double length = speed * 0.000277777778; // km/s

		Double second = tankFillUpdate(tankFillLevel, petrolUsage, length);
		GPSposition first = calculatePosition(length, oldPosition);
		// set old position on current position for next update
		oldPosition.longitude = first.longitude;
		oldPosition.latitude = first.latitude;

		// validation of position
		if (oldPosition.latitude == worldDestination.latitude
				&& oldPosition.longitude == worldDestination.longitude) {
			
			worldDestination.latitude = positions.get(positionsCounter)
					.getGpsPosition().latitude;
			
			worldDestination.longitude = positions.get(positionsCounter)
					.getGpsPosition().longitude;
			
			updateAngleAndDirections(oldPosition, worldDestination);
			positionsCounter++;
			for(WarningType gps : positions){
				if(gps.getGpsPosition().equals(worldDestination)){
					nextWarning = gps;
				}
			}
		}
		return new Tupel<GPSposition, Double>(first, second);
	}

	public double getAngle() {
		return angle;
	}

	public GPSposition getOldPosition() {
		return oldPosition;
	}

	public void setOldPosition(GPSposition oldPosition) {
		this.oldPosition = oldPosition;
	}

	public GPSposition getNextWorldPosition() {
		return positions.get(positionsCounter + 1).getGpsPosition();
	}

	public void setWarning(WarningType wt) {
		for (int i = 0; i < positions.size()-1; i++) {
			if (positions.get(i).getGpsPosition().latitude == wt.getGpsPosition().latitude && positions.get(i).getGpsPosition().longitude == wt.getGpsPosition().longitude){
				positions.set(i, wt);
			}
		}
	}

	public WarningType getNextWarning() {
		return nextWarning;
	}
	//public void addWarning()
}
