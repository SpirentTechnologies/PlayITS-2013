package com.testingtech.ttworkbench.play.simulation.car;

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
	Car car;

	public ActionsServiceImpl(Car car) {
		this.car = car;
	}

	@Override
	public Void aPIOnOffEngineType(RpcController controller,
			onOffEngineType request) throws ServiceException {
		car.engine = request.getEngineStatus();
		return nil();
	}

	@Override
	public Void aPISpeedType(RpcController controller, speedType request)
			throws ServiceException {
		car.speed = request.getSpeed();
		return nil();
	}

	@Override
	public Void aPITrackType(RpcController controller, trackType request)
			throws ServiceException {
		// TODO set car track depending on name of the track/map
		return nil();
	}

	@Override
	public Void aPIWarningType(RpcController controller, warningType request)
			throws ServiceException {
		Warnings danger = Warnings.valueOf(request.getWarningName().getEnumValue().toString());
		//add the warning to the next possible position, TODO add to WarningType a gpsPosition for the map at least
		Tupel<Warnings, GPSposition> warning = new Tupel<Warnings, GPSposition>(danger, car.position.getNextWorldPosition());
		car.addWarning(warning);
		return nil();
	}

	@Override
	public Void aPICarInitType(RpcController controller, carInitType request)
			throws ServiceException {
		// TODO generate the car or update all the fields see into it how to do that
		return nil();
	}

	@Override
	public Void aPIWidgetExit(RpcController controller, widgetExit request)
			throws ServiceException {
		// TODO kill the car simulation
		return nil();
	}

	protected Void nil() {
		return Void.newBuilder().build();
	}
}
