<<<<<<< HEAD
package com.testingtech.ttworkbench.play.simulation.car;

import java.util.ArrayList;

public class GPSpositionOfCar {
	private double directionX, directionY;
	private GPSposition worldDestination;
	private GPSposition oldPosition;
	private double angle;
	private ArrayList<GPSposition> positions;
	private int positionsCounter = 2;

	public GPSpositionOfCar(GPSposition destinationPositions,
			GPSposition startPosition, ArrayList<GPSposition> positions) {
		this.worldDestination = destinationPositions;
		this.oldPosition = startPosition;
		this.positions = positions;

		// calculate the angle needed for calculating the new position 
		// of the car
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

		// update the directions the car is driving
		directionX = Math.abs(lon1) - Math.abs(lon2);
		directionY = Math.abs(lat1) - Math.abs(lat2);
	}

	// basically the constructors functionality repeated for new start and 
	// destination points
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

	// calculate the new position of the car using the length driven
	// and the old position of the car
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
		//validation of the driving funcitonality, so the car stops,
		//by reaching its destination
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
		// calculate the length driven, should be somewhere in the code
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
			worldDestination.latitude = positions.get(positionsCounter).latitude;
			worldDestination.longitude = positions.get(positionsCounter).longitude;
			updateAngleAndDirections(oldPosition, worldDestination);
			positionsCounter++;

		}
		//return the new destination of the car and the tankfill status
		return new Tupel<GPSposition, Double>(first, second);
	}

	public double getAngle() {
		return angle;
	}
	public GPSposition getCurrentPosition(){
		return oldPosition;
	}
	public GPSposition setCurrentPosition(GPSposition position){
		oldPosition = position;
		return oldPosition;
	}
}
=======
package com.testingtech.ttworkbench.play.simulation.car;

import java.util.ArrayList;

public class GPSpositionOfCar {
	private double directionX, directionY;
	private GPSposition worldDestination;
	private GPSposition oldPosition;
	private double angle;
	private ArrayList<GPSposition> positions;
	private int positionsCounter = 2;

	public GPSpositionOfCar(GPSposition destinationPositions,
			GPSposition startPosition, ArrayList<GPSposition> positions) {
		this.worldDestination = destinationPositions;
		this.oldPosition = startPosition;
		this.positions = positions;

		// calculate the angle needed for calculating the new position 
		// of the car
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

		// update the directions the car is driving
		directionX = Math.abs(lon1) - Math.abs(lon2);
		directionY = Math.abs(lat1) - Math.abs(lat2);
	}

	// basically the constructors functionality repeated for new start and 
	// destination points
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

	// calculate the new position of the car using the length driven
	// and the old position of the car
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
		//validation of the driving funcitonality, so the car stops,
		//by reaching its destination
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
		// calculate the length driven, should be somewhere in the code
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
			worldDestination.latitude = positions.get(positionsCounter).latitude;
			worldDestination.longitude = positions.get(positionsCounter).longitude;
			updateAngleAndDirections(oldPosition, worldDestination);
			positionsCounter++;

		}
		//return the new destination of the car and the tankfill status
		return new Tupel<GPSposition, Double>(first, second);
	}

	public double getAngle() {
		return angle;
	}
	public GPSposition getCurrentPosition(){
		return oldPosition;
	}
	public GPSposition setCurrentPosition(GPSposition position){
		oldPosition = position;
		return oldPosition;
	}
}
>>>>>>> f30563df9578bb45d9d1c7cd385e227a04f3fab6
