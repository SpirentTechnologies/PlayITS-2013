package com.testingtech.ttworkbench.play.simulation.car;

import java.util.Hashtable;
import java.util.Map;

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
		car.engine = request.getEngineStatus();
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
		long id = request.getCarId();
		Car car = getCar(id);
		car.setTrack(request.getTrackName());
		
		return nil();
	}

	@Override
	public Void aPIWarningType(RpcController controller, warningType request)
			throws ServiceException {
		long id = request.getCarId();
		Car car = getCar(id);
		Warnings danger = Warnings.valueOf(request.getWarningName()
				.getEnumValue().toString());
		// add the warning to the next possible position
		/*
		 * FIXME add to WarningType a gpsPosition for the map at least or let it
		 * stay at this implementation
		 */
		Tupel<Warnings, GPSposition> warning = new Tupel<Warnings, GPSposition>(
				danger, new GPSposition(request.getGpsPos().getLongitude(), request.getGpsPos().getLatitude()));
		carModel.addWarning(warning);
		return nil();
	}

	@Override
	public Void aPICarInitType(RpcController controller, carInitType request)
			throws ServiceException {
		// updates the initial car setup with the wanted field values
		Car car = new Car(0, request.getMaxSpeed(), request.getFuelFilling(), request.getFuelConsumption(), request.getLightSensorExists(), true, request.hasFuelFilling(), true, request.getEspSensorExists(), request.getAbsSensorExists(), false, request.getFogLightSensorExists());
		long id = carModel.addCar(car);
		Socket socket = new Socket(car,(int) request.getTtcnEventsPort(),request.getTtcnEventsHostName());
		carSocket.put(car, socket);
		new Thread(socket).start();
		//TODO send id as message
		return nil();
	}

	@Override
	public Void aPIWidgetExit(RpcController controller, widgetExit request)
			throws ServiceException {
		// set the car to be destructed in the next update or immediately
		// (depending on simulation)
		long id = request.getCarId();
		Car car = getCar(id);
		car.setDestroyCar(true);
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
