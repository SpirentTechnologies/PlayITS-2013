package com.testingtech.ttworkbench.play.simulation.car;

public class GPSpositionOfCar {

	GPSposition worldPositions[];
	int currentWorldPosition = 0;
	GPSposition currentPosition;
	private GPSposition newPosition;
	private GPSposition oldPosition;

	public GPSpositionOfCar(GPSposition[] worldPositions,
			GPSposition currentPosition) {
		super();
		this.worldPositions = worldPositions;
		this.currentPosition = currentPosition;
	}

	private double toRadiants(double a) {
		return a * Math.PI / 180;
	}

	// get distance in km
	private double distance(GPSposition newPosition, GPSposition oldPosition) {
		double R = 6371.0;
		double dLat = toRadiants(newPosition.latitude - oldPosition.latitude);
		double dLon = toRadiants(newPosition.longitude - oldPosition.longitude);
		double lat1 = toRadiants(oldPosition.latitude);
		double lat2 = toRadiants(newPosition.latitude);
		double tmp = Math.sin(dLat / 2) * Math.sin(dLat / 2)
				+ Math.sin(dLon / 2) * Math.sin(dLon / 2) * Math.cos(lat1)
				* Math.cos(lat2);

		double tmp2 = 2 * Math.atan2(Math.sqrt(tmp), Math.sqrt(1 - tmp));
		return tmp2 * R;
	}

	// update the tank fill level of the car
	private Double tankFillUpdate(double tankFillLevel, double petrolUsage) {
		// calculate the distance driven since last update
		double distance = distance(newPosition, oldPosition);
		// calculate actual distance
		double actualUsage = petrolUsage * distance / 100;
		// update tankfill
		tankFillLevel -= actualUsage;
		return tankFillLevel;
	}

	
	//calculate the new position of the car
	private GPSposition calculatePosition(double length, GPSposition currentPositon) {
		double latitude = length;
		double longitude = length;
		double cosine = Math.cos(currentPositon.latitude);
		GPSposition pos = new GPSposition(0, 0);

		// calculate latitude km
		// 111km
		while (latitude - 111 > 0) {
			latitude -= 111;
			pos.latitude += 1;
		}
		// 11.1km
		while (latitude - 11.1 > 0) {
			latitude -= 11.1;
			pos.latitude += 0.1;
		}
		// 1.11km
		while (latitude - 1.11 > 0) {
			latitude -= 1.11;
			pos.latitude += 0.01;
		}
		// 0.111km <=> 111m
		while (latitude - 0.111 > 0) {
			latitude -= 0.111;
			pos.latitude += 0.001;
		}
		// 0.0111km <=> 11.1m
		while (latitude - 0.0111 > 0) {
			latitude -= 0.0111;
			pos.latitude += 0.0001;
		}
		// 0.00111km <=> 1.11m
		while (latitude - 0.00111 > 0) {
			latitude -= 0.00111;
			pos.latitude += 0.00001;
		}

		// calculate latitude km
		// 111km
		while (longitude- 111 > 0) {
			longitude-= 111*cosine;
			pos.longitude+= 1;
		}
		// 11.1km
		while (longitude- 11.1 > 0) {
			longitude-= 11.1*cosine ;
			pos.longitude+= 0.1;
		}
		// 1.11km
		while (longitude- 1.11 > 0) {
			longitude-= 1.11*cosine ;
			pos.longitude+= 0.01;
		}
		// 0.111km <=> 111m
		while (longitude- 0.111 > 0) {
			longitude-= 0.111*cosine ;
			pos.longitude+= 0.001;
		}
		// 0.0111km <=> 11.1m
		while (longitude- 0.0111 > 0) {
			longitude-= 0.0111*cosine ;
			pos.longitude+= 0.0001;
		}
		// 0.00111km <=> 1.11m
		while (longitude- 0.00111 > 0) {
			longitude-= 0.00111*cosine ;
			pos.longitude+= 0.00001;
		}

		return pos;
	}
	//update the petrol usage state and the new gps position 
	public Tupel<GPSposition, Double> updateEverything(double tankFillLevel,
			double petrolUsage) {
		//TODO calculate the length driven, should be somewhere in the code already just add function call
		Double second = tankFillUpdate(tankFillLevel, petrolUsage);
		GPSposition first = updateGPSposition(length,currentPosition);
		return new Tupel<GPSposition, Double>(first, second);
	}
}
