package com.testingtech.ttworkbench.play.simulation.car.tests;

import static org.junit.Assert.fail;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.testingtech.ttworkbench.play.simulation.car.CarModel;

public class CarModelTests {

	private CarModel model;

	@Before
	public void setUp() throws Exception {
		model = new CarModel();
	}

	@After
	public void tearDown() throws Exception {
		model = null;
	}

//	@Test
//	public void test() {
//		fail("Not yet implemented");
//	}

}
