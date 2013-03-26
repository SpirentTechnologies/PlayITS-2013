package com.testingtech.ttworkbench.play.simulation.car.tests;

import java.io.File;
import java.net.MalformedURLException;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.testingtech.ttworkbench.play.simulation.car.Car;

public class CarTests {

	private static final String TRACK = "track1.kml";
	private Car car;


	@Before
	public void setUp() throws Exception {
		car = new Car(0, 200, 2, 50, 2, true, true, true, true, true, true, true, true, trackName());
	}

	@After
	public void tearDown() throws Exception {
		car.disposeCar();
		car = null;
	}
	
	@Test
	public void testCarParseTrack() throws MalformedURLException {
		car.update();
	}

	@Test
	public void testCarDrive() throws MalformedURLException {
		car.setEngine(true);
		car.setSpeed(50);
		car.update();
	}

	@SuppressWarnings("deprecation")
	protected String trackName() throws MalformedURLException {
		String trackName = new File("tracks", TRACK).toURL().toString();
		return trackName;
	}

}
