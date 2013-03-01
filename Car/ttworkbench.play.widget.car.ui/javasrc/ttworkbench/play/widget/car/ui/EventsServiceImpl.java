
package ttworkbench.play.widget.car.ui;

import ttworkbench.play.widget.car.ui.model.CarStatusModel;
import ttworkbench.play.widget.car.ui.model.GPSposition;
import ttworkbench.play.widget.car.ui.model.Warnings;
import ttworkbench.play.widget.car.ui.model.WarningType;

import com.google.protobuf.RpcController;
import com.google.protobuf.ServiceException;
import com.testingtech.ttworkbench.play.generated.PROTO_API.EVENTS.BlockingInterface;
import com.testingtech.ttworkbench.play.generated.PROTO_API.Void;
import com.testingtech.ttworkbench.play.generated.PROTO_API.carStatusType;
import com.testingtech.ttworkbench.play.generated.PROTO_API.warningType;



public class EventsServiceImpl implements BlockingInterface {

  private final CarModel model;

  public EventsServiceImpl(CarModel widget) {
    this.model = widget;
  }

@Override
public Void aPICarStatusType(RpcController controller, carStatusType request)
		throws ServiceException {
	if(request != null){
		warningType reqWarning = request.getWarning();
		if(reqWarning != null){
			WarningType warning = new WarningType();
			warning.setWarning(Warnings.getWarning((reqWarning.getWarningName().getEnumValue().getNumber())));
			warning.setGpsPosition(new GPSposition(reqWarning.getGpsPos().getLatitude(),reqWarning.getGpsPos().getLongitude()));
			warning.setPriority(reqWarning.getPriority());
			model.addWarning(warning);
		}
		
		CarStatusModel status = model.getStatus();
		status.setABSenabled(request.getAbsSensor());
		status.setActualSpeed(request.getSpeed());
		status.setEngineStarted(request.getEngineStatus());
		status.setESPenabled(request.getEspSensor());
		status.setLightenabled(request.getLightSensor());
		
		model.notifyListener();
	}
	return nil();
}

protected Void nil() {
    return Void.newBuilder().build();
  }

}
