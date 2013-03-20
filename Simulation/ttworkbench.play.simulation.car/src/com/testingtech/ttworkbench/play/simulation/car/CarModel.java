package com.testingtech.ttworkbench.play.simulation.car;

import java.util.Hashtable;
import java.util.Map;

public class CarModel {
	private Map<Integer, Car> map = new Hashtable<Integer, Car>();

	public int addCar(Car car) {
		map.put(car.customID, car);
		return car.customID;
	}

	public Car get(long id) {
		return map.get((int) id);
	}

	public void addWarning(WarningType warning, long id) {
		// Add the warning to every car
		for (Car c : map.values()) {
			c.addWar3ning(warning);
		}
	}
}
