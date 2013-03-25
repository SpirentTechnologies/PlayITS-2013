package com.testingtech.ttworkbench.play.simulation.car;

import java.io.File;
import java.util.Hashtable;
import java.util.Map;
import java.util.Queue;

import com.google.protobuf.RpcController;
import com.google.protobuf.ServiceException;
import com.testingtech.ttworkbench.play.generated.PROTO_API.ACTIONS.BlockingInterface;
import com.testingtech.ttworkbench.play.generated.PROTO_API.Void;
import com.testingtech.ttworkbench.play.generated.PROTO_API.carInitType;
import com.testingtech.ttworkbench.play.generated.PROTO_API.onOffEngineType;
import com.testingtech.ttworkbench.play.generated.PROTO_API.speedType;
import com.testingtech.ttworkbench.play.generated.PROTO_API.trackType;
import com.testingtech.ttworkbench.play.generated.PROTO_API.warningType;
import com.testingtech.ttworkbench.play.generated.PROTO_API.widgetExit;

public class ActionsServiceImpl implements BlockingInterface {
	CarModel carModel;
	Map <Car, Socket> carSocket = new Hashtable<Car, Socket>();

	public ActionsServiceImpl(CarModel car) {
		this.carModel = car;
	}

	@Override
	public Void aPIOnOffEngineType(RpcController controller,
			onOffEngineType request) throws ServiceException {
		long id = request.getCarId();
		Car car = getCar(id);
		car.setEngine(request.getEngineStatus());
		return nil();
	}

	@Override
	public Void aPISpeedType(RpcController controller, speedType request)
			throws ServiceException {
		long id = request.getCarId();
		Car car = getCar(id);
		car.speed = request.getSpeed();
		return nil();
	}

	@Override
	public Void aPITrackType(RpcController controller, trackType request)
			throws ServiceException {
		// TODO set car track depending on name of the track/map
		// 		-> just init the car again
		long id = request.getCarId();
		Car car = getCar(id);
		car.setTrack(request.getTrackName());
		
		return nil();
	}

	@Override
	public Void aPIWarningType(RpcController controller, warningType request)
			throws ServiceException {
		
		GPSposition position = new GPSposition(request.getGpsPos().getLongitude(), request.getGpsPos().getLatitude());
		Warnings warning  = Warnings.valueOf( request.getWarningName().getEnumValue().toString());
		WarningType wt = new WarningType(warning,position);
		
		carModel.addWarning(wt,request.getCarId());
		return nil();
	}

	@Override
	public Void aPICarInitType(RpcController controller, carInitType request)
			throws ServiceException {
		Queue<GPSposition> positions;
		positions =	new KMLtoGPSQueue(new File(request.getTrackName())).getPositions();

		// updates the initial car setup with the wanted field values
		final Car car = new Car(0, request.getMaxSpeed(), 2.0, request.getFuelFilling(), request.getFuelConsumption(), request.getLightSensorExists(), true, request.hasFuelFilling(), true, request.getEspSensorExists(), request.getAbsSensorExists(), false, request.getFogLightSensorExists(), positions);
		carModel.addCar(car);
		Socket socket = new Socket(car,(int) request.getTtcnEventsPort(),request.getTtcnEventsHostName());
		carSocket.put(car, socket);
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				new CarWindow().open(car);	
			}
		}).start();

		
		Thread thread = new Thread(socket);
		thread.start();
		//CarWindow for Testing Simulation
		
		return nil();
	}

	@Override
	public Void aPIWidgetExit(RpcController controller, widgetExit request)
			throws ServiceException {
		// set the car to be destructed in the next update or immediately
		// (depending on simulation)
		long id = request.getCarId();
		Car car = getCar(id);
		car.disposeCar();
		return nil();
	}

	protected Void nil() {
		return Void.newBuilder().build();
	}

	private Car getCar(long id) throws ServiceException {
		Car car = carModel.get(id);
		if (car == null){
			throw new ServiceException("No car with this ID"+ id);
		}
		return car;
	}

}
