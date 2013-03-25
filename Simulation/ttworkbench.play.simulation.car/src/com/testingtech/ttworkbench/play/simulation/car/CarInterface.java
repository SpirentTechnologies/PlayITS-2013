package com.testingtech.ttworkbench.play.simulation.car;

public interface CarInterface {
	boolean toggleEngine(); //engine
	boolean toggleLight(); //light on off
	boolean toggleFogLight(); // foglight on off
	double getSpeed(); // get speed of car
	boolean toggleESP(); // esp on off
	boolean toggleLightSensor(); //lightsensor on off
	boolean toggleRainSensor(); // rainsensor on off
	GPSposition getGPSPosition(); // get gpsposition
	double getTankFill(); // tank fill sensor -> get its tank fill
	double breaking(); // breaking length stuff
	void update(); // update all resources 
	void setSpeed(double speed); 
	void setFuelLevel(double fuel);
	double getConsumption();
	double getFuelLevel();
	
}
