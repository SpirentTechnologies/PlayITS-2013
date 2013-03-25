
package ttworkbench.play.widget.car.ui;

import java.util.List;

import ttworkbench.play.widget.car.ui.model.CarStatusModel;
import ttworkbench.play.widget.car.ui.model.GPSposition;
import ttworkbench.play.widget.car.ui.model.NotifyAttributes;
import ttworkbench.play.widget.car.ui.model.WarningType;
import ttworkbench.play.widget.car.ui.model.enumWarning;

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
  private boolean isFirstCall = true;
  
  public EventsServiceImpl(CarModel model) {
    this.model = model;
  }


@Override
public Void aPICarStatusType(RpcController controller, carStatusType request)
		throws ServiceException {
	
	//String debug = new String(); 
	
	if(request != null){
		CarStatusModel status = model.getStatus();
		//first message sets car id
		if(status.getId() == -1){
			status.setId(request.getCarId());
		}
		if(request.getWarningCount() > 0){
			List<warningType> reqWarnings = request.getWarningList();
			model.clearWarnings();
			for (warningType reqWarning : reqWarnings) {
				WarningType warning = new WarningType();
				warning.setWarning(enumWarning.getWarning((reqWarning.getWarningName().getEnumValue().getNumber())));
				warning.setGpsPosition(new GPSposition(reqWarning.getGpsPos().getLatitude(),reqWarning.getGpsPos().getLongitude()));
				warning.setPriority(reqWarning.getPriority());
				model.addWarning(warning);
//				debug = debug.concat(warning.toString() + " : ");
			}
			model.notifyListener(NotifyAttributes.WARNING);
		}

		
		//change Model only if attributes are different from event status
		if(request.hasAbsSensor()){
//			debug = debug.concat("ABS : " + status.isABSenabled() + "  :");
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
			//debug = debug.concat("Engine : " + status.isEngineStarted() + "  :");
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
//			debug = debug.concat("GPS : " + request.getGpsPos().toString() + "  :");
			if(!(new GPSposition(request.getGpsPos().getLatitude(), request.getGpsPos().getLongitude()).equals(status.getGpsPosition()))){
				model.setGPSPostion(request.getGpsPos().getLatitude(), request.getGpsPos().getLongitude());
				model.notifyListener(NotifyAttributes.GPS);
			}
		}
		
		//System.out.println(debug);
		
		if(isFirstCall){
			model.notifyListener(NotifyAttributes.FIRST);
			isFirstCall = false;
		}
		
	}
	return nil();
}

protected Void nil() {
    return Void.newBuilder().build();
  }

}
