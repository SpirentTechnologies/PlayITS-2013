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
	
	public void addWarning(WarningType warning, long id){
		//Add the warning to every car
		map.get((int) id).addWarning(warning);
	}
	//TODO if (engine && speed == 0) tell all the other cars that there is a standing car at the position 
	
}
