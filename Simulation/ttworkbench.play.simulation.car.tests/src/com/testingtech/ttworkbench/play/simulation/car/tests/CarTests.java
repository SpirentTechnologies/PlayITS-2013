package com.testingtech.ttworkbench.play.simulation.car.tests;

import java.io.File;
import java.net.MalformedURLException;

import junit.framework.Assert;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.testingtech.ttworkbench.play.generated.PROTO_API.carStatusType;
import com.testingtech.ttworkbench.play.simulation.car.Car;
import com.testingtech.ttworkbench.play.simulation.car.CarStatusTypeParser;

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


	@Test
	public void testCarUpdate() throws MalformedURLException {
		car.setEngine(true);
		car.setSpeed(50);

		int countUpdates = 200;
		for (int i = 0; i < countUpdates; i++) {
			car.update();
			
			// parse the car into a carStatusType message and make the rpc call
			carStatusType request = CarStatusTypeParser.parseToStatusType(car);
			Assert.assertFalse("Fail latitude is NaN", Float.isNaN(request.getGpsPos().getLatitude()));
		}
	}

	@SuppressWarnings("deprecation")
	protected String trackName() throws MalformedURLException {
		String trackName = new File("tracks", TRACK).toURL().toString();
		return trackName;
	}

}
