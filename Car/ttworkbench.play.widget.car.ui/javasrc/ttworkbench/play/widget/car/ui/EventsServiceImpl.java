
package ttworkbench.play.widget.car.ui;

import java.util.List;

import ttworkbench.play.widget.car.ui.model.CarStatusModel;
import ttworkbench.play.widget.car.ui.model.EnumWarning;
import ttworkbench.play.widget.car.ui.model.GPSposition;
import ttworkbench.play.widget.car.ui.model.NotifyAttributes;
import ttworkbench.play.widget.car.ui.model.WarningType;

import com.google.protobuf.RpcController;
import com.google.protobuf.ServiceException;
import com.testingtech.ttworkbench.play.generated.PROTO_API.EVENTS.BlockingInterface;
import com.testingtech.ttworkbench.play.generated.PROTO_API.Void;
import com.testingtech.ttworkbench.play.generated.PROTO_API.carStatusType;
import com.testingtech.ttworkbench.play.generated.PROTO_API.warning.EnumValue;
import com.testingtech.ttworkbench.play.generated.PROTO_API.warningType;


/**
 * 
 * @author Björn
 *
 */
public class EventsServiceImpl implements BlockingInterface {

  private final CarModel model;
  private boolean isFirstCall = true;
  private ICommunication comm;
  
  /**
   * Received messages with current status of the car from sut.
   * Sends notifies to view and change model, if necessary. 
   * @param CarModel model
   * @param ICommunication comm
   */
  public EventsServiceImpl(CarModel model, ICommunication comm) {
    this.model = model;
    this.comm = comm;
  }

/*
 * (non-Javadoc)
 * @see com.testingtech.ttworkbench.play.generated.PROTO_API.EVENTS.BlockingInterface#aPICarStatusType(com.google.protobuf.RpcController, com.testingtech.ttworkbench.play.generated.PROTO_API.carStatusType)
 *	
 *	
 */
@Override
public Void aPICarStatusType(RpcController controller, carStatusType request)
		throws ServiceException {
	
	
	if(request != null){
		CarStatusModel status = model.getStatus();
		//first message sets car id
		if(status.getId() == -1){
			status.setId(request.getCarId());
		}
		
		
		//change Model only if attributes are different from current car status
		if (request.getWarningCount() > 0){
			System.out.println("warnings in list");
			List<warningType> reqWarnings = request.getWarningList();
			model.clearWarnings();
			for (warningType reqWarning : reqWarnings) {
				WarningType warning = new WarningType();
				warning.setWarning(convertWarn(reqWarning.getWarningName().getEnumValue()));
				warning.setGpsPosition(new GPSposition(reqWarning.getGpsPos().getLatitude(),reqWarning.getGpsPos().getLongitude()));
				warning.setPriority(reqWarning.getPriority());
				model.addWarning(warning);
			}
			model.notifyListener(NotifyAttributes.WARNING);
		}
		

		if(request.hasAbsSensor()){
			if(request.getAbsSensor() != status.isABSenabled()){
				status.setABSenabled(request.getAbsSensor());
				model.notifyListener(NotifyAttributes.ABS);
				
			}
		}
		
		if(request.hasSpeed()){
			if(request.getSpeed() != status.getCurrentSpeed()){
				status.setActualSpeed(request.getSpeed());
				model.notifyListener(NotifyAttributes.SPEED);
			}
		}
		
		if(request.hasEngineStatus()){
			
			if(request.getEngineStatus() != status.isEngineStarted()){
				status.setEngineStarted(request.getEngineStatus());
				model.notifyListener(NotifyAttributes.ENGINE);
				
			}
		}
		
		if(request.hasEspSensor()){
			if(request.getEspSensor() != status.isESPenabled()){
				status.setESPenabled(request.getEspSensor());
				model.notifyListener(NotifyAttributes.ESP);
			}
				
		}
		
		if(request.hasLightSensor()){
			if(request.getLightSensor() != status.isLightEnabled()){
				status.setLightEnabled(request.getLightSensor());
				model.notifyListener(NotifyAttributes.LIGHT);
			}
		}
		
		if(request.hasFogLightSensor()){
			if(request.getFogLightSensor() != status.isFogLightSensorEnabled()){
				status.setFogLightEnabled(request.getFogLightSensor());
				model.notifyListener(NotifyAttributes.FOG);
			}
		}
		
		if(request.hasFuelFilling()){
			if(request.getFuelFilling() != status.getFuel()){
				status.setFuel(request.getFuelFilling());
				model.notifyListener(NotifyAttributes.FUEL);
			}
		}
		
		if(request.hasGpsPos()){
			if(!(new GPSposition(request.getGpsPos().getLatitude(), request.getGpsPos().getLongitude()).equals(status.getGpsPosition()))){
				model.setGPSPostion(request.getGpsPos().getLatitude(), request.getGpsPos().getLongitude());
				model.notifyListener(NotifyAttributes.GPS);
			}
		}
		

		
		//if first init of car is complete, enable actions in View
		if(isFirstCall){
			comm.enableActions();
			isFirstCall = false;
		}
		
	}
	return nil();
}

/**
 * Converts EnumValue to EnumWarning
 * @param enumValue
 * @return EnumWarning
 */
private static EnumWarning convertWarn(EnumValue enumValue) {
	EnumWarning res;
	switch (enumValue) {
	case accident:
		res = EnumWarning.ACCIDENT;
		break;
	case deer:
		res = EnumWarning.DEER;
		break;
	case fog:
		res = EnumWarning.FOG;
		break;
	case ice:
		res = EnumWarning.ICE;
		break;
	case rain:
		res = EnumWarning.RAIN;
		break;
	case snow:
		res = EnumWarning.SNOW;
		break;
	default:
		throw new IllegalArgumentException("Unknown warning type "+enumValue);
	}
	return res;
}

protected Void nil() {
    return Void.newBuilder().build();
  }

}
