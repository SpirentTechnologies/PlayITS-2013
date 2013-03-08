package com.testingtech.ttworkbench.play.simulation.car;

import com.google.protobuf.RpcController;
import com.google.protobuf.ServiceException;
import com.testingtech.ttworkbench.play.generated.PROTO_API.carStatusType;
import com.testingtech.ttworkbench.play.generated.PROTO_API.Void;
import com.testingtech.ttworkbench.play.generated.PROTO_API.ACTIONS.BlockingInterface;
import com.testingtech.ttworkbench.play.generated.PROTO_API.onOffEngineType;
import com.testingtech.ttworkbench.play.generated.PROTO_API.speedType;
import com.testingtech.ttworkbench.play.generated.PROTO_API.trackType;
import com.testingtech.ttworkbench.play.generated.PROTO_API.warningType;


public class ActionsServiceImpl implements BlockingInterface {
	Car car;
	
	public ActionsServiceImpl(Car car) {
		// TODO Auto-generated constructor stub
		this.car = car;
	}
	public Void aPICarStatusType(RpcController controller, carStatusType request) throws ServiceException {
		//TODO 
		// TODO decide what to do and update car status
		//should run in the car instance same as the server itself
		//car should be running in the Simulation
		if (request.hasEngineStatus()){
			car.engine = request.getEngineStatus();
		}else if (request.hasAbsSensor()){
			car.sensors.abs = request.getAbsSensor();
		}else if (request.hasEspSensor()){
			car.sensors.esp = request.getEspSensor();
		}else if (request.hasFogLightSensor()){
			car.sensors.fogLight = request.getFogLightSensor();
		}else if (request.hasFuelFilling()){
			car.sensors.tankFillLevel = request.getFuelFilling();
		}else if (request.hasGpsPos()){
			car.currentPosition = new GPSposition(request.getGpsPos().getLongitude(), request.getGpsPos().getLatitude());
		}else if (request.hasLightSensor()){
			car.sensors.light = request.getLightSensor();
		}else if (request.hasSpeed()){
			car.speed = request.getSpeed();
		}else if (request.hasWarning()){
//			car.warning.addAll(request.getWarning());
		}
		return null;
	}
	@Override
	public Void aPIOnOffEngineType(RpcController controller,
			onOffEngineType request) throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Void aPISpeedType(RpcController controller, speedType request)
			throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Void aPITrackType(RpcController controller, trackType request)
			throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public Void aPIWarningType(RpcController controller, warningType request)
			throws ServiceException {
		// TODO Auto-generated method stub
		return null;
	}
}
