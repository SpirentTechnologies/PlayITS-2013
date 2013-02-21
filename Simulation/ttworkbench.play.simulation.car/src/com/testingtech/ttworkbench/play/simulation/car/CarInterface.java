package com.testingtech.ttworkbench.play.simulation.car;


public interface CarInterface {
	boolean toggleEngine(); //engine
	boolean toggleLight(); //light on off
	boolean toggleFogLight(); // foglight on off
	double setSpeed(double speed); //set speed of car
	double getSpeed(); // get speed of car
	boolean toggleESP(); // esp on off
	boolean toggleABS(); // abs on off
	boolean toggleAirbag(); //airbag on off
	boolean toggleWarningSender(); // send warnings on off -> warnings between cars
	boolean toggleWarningReceiver(); // receive warnings on off -> warnings between cars
	boolean toggleLightSensor(); //lightsensor on off
	boolean toggleRainSensor(); // rainsensor on off
	GPSposition getGPSPosition(); // get gpsposition
	GPSposition setGPSPosition(GPSposition position); // set gpsposition
	double getTankFill(); // tank fill sensor -> get its tank fill
	boolean triggerAirbag(); // airbag pressure trigger
	void breaking(); // breaking length stuff
	Damage getDamage(); // get car damage
	Damage setDamage(Damage d); // set car damage
	void update(); // update all resources 
	
}
