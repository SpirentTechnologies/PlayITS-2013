
package ttworkbench.play.widget.car.ui;

import ttworkbench.play.widget.car.ui.model.CarStatusModel;
import ttworkbench.play.widget.car.ui.model.GPSposition;
import ttworkbench.play.widget.car.ui.model.NotifyAttributes;
import ttworkbench.play.widget.car.ui.model.Warnings;
import ttworkbench.play.widget.car.ui.model.WarningType;

import com.google.protobuf.RpcController;
import com.google.protobuf.ServiceException;
import com.testingtech.ttworkbench.play.generated.PROTO_API.EVENTS.BlockingInterface;
import com.testingtech.ttworkbench.play.generated.PROTO_API.Void;
import com.testingtech.ttworkbench.play.generated.PROTO_API.carStatusType;
import com.testingtech.ttworkbench.play.generated.PROTO_API.warningType;


/**
 * 
 * @author kensan
 *
 */
public class EventsServiceImpl implements BlockingInterface {

  private final CarModel model;

  public EventsServiceImpl(CarModel model) {
    this.model = model;
  }

@Override
public Void aPICarStatusType(RpcController controller, carStatusType request)
		throws ServiceException {
	
	if(request != null){
		
		if(request.hasWarning()){
			warningType reqWarning = request.getWarning();
			if(reqWarning != null){
				WarningType warning = new WarningType();
				warning.setWarning(Warnings.getWarning((reqWarning.getWarningName().getEnumValue().getNumber())));
				warning.setGpsPosition(new GPSposition(reqWarning.getGpsPos().getLatitude(),reqWarning.getGpsPos().getLongitude()));
				warning.setPriority(reqWarning.getPriority());
				model.addWarning(warning);
				model.notifyListener(NotifyAttributes.WARNING);
			}
		}

		//get actual Status from Model
		CarStatusModel status = model.getStatus();
		
		//change Model only if attributes are different from event status
		if(request.hasAbsSensor()){
			if(request.getAbsSensor() != status.isABSenabled()){
				status.setABSenabled(request.getAbsSensor());
				model.notifyListener(NotifyAttributes.ABS);
			}
		}
		
		if(request.hasSpeed()){
			if(request.getSpeed() != status.getActualSpeed()){
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
		
		if(request.getEspSensor()){
			if(request.getEspSensor() != status.isESPenabled()){
				status.setESPenabled(request.getEspSensor());
				model.notifyListener(NotifyAttributes.ESP);
			}
				
		}
		
		if(request.getLightSensor()){
			if(request.getLightSensor() != status.isLightSensorEnabled()){
				status.setLightSensorEnabled(request.getLightSensor());
				model.notifyListener(NotifyAttributes.LIGHT);
			}
		}
		
		if(request.getFogLightSensor()){
			if(request.getFogLightSensor() != status.isFogLightEnabled()){
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
		
		
	}
	return nil();
}

protected Void nil() {
    return Void.newBuilder().build();
  }

}
