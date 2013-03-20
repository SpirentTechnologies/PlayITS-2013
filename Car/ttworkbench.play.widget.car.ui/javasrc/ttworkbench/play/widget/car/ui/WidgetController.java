package ttworkbench.play.widget.car.ui;

import java.io.File;
import java.io.IOException;

import com.google.protobuf.ServiceException;
import com.testingtech.ttworkbench.play.generated.PROTO_API.carInitType;
import com.testingtech.ttworkbench.play.generated.PROTO_API.onOffEngineType;
import com.testingtech.ttworkbench.play.generated.PROTO_API.speedType;
import com.testingtech.ttworkbench.play.generated.PROTO_API.trackType;
import com.testingtech.ttworkbench.play.generated.PROTO_API.widgetExit;


/**
 * 
 * @author kensan
 *
 */
public class WidgetController {

	private final ICommunication comm;
	private boolean initialized;

	public WidgetController(ICommunication comm) {
		this.comm = comm;
	}

	/**
	 * 
	 * @param client from com.testingtech.ttworkbench.play.dashboard.widget.AbstractDashboardWidget.getActionsClient()
	 * @throws IOException
	 */
	public void startEngine(){	
		
		initCar();

		try {
			onOffEngineType.Builder request = onOffEngineType.newBuilder();
			request.setEngineStatus(true);

			client().getActionsService().aPIOnOffEngineType(client().getController(), request.build());
			// TODO response check

		} catch (ServiceException e) {

			System.err.println("Unable to start engine! \n" + e.getMessage());
		}
	}

	private ActionsClient client() {
		return comm.getActionsClient();
	}

	/**
	 * 
	 * @param client from com.testingtech.ttworkbench.play.dashboard.widget.AbstractDashboardWidget.getActionsClient()
	 * @throws IOException
	 */
	public void stopEngine(){	
		initCar();

		try {
			onOffEngineType.Builder request = onOffEngineType.newBuilder();
			request.setEngineStatus(false);
			client().getActionsService().aPIOnOffEngineType(client().getController(), request.build());
			// TODO response check
		} catch (ServiceException e) {

			System.err.println("Unable to stop Engine! \n" + e.getMessage());
		}

	}


	/**
	 * 
	 * @param client
	 * @param speed
	 */
	public void changeSpeed(float speed){
		initCar();
		try {
			speedType.Builder request = speedType.newBuilder();
			request.setSpeed(speed);

			client().getActionsService().aPISpeedType(client().getController(), request.build());

		} catch (ServiceException e) {

			e.printStackTrace();
		}
	}

	public void setTrack(File trackFile){
		comm.getCarModel().getStatus().setTrackFile(trackFile);

		boolean sendTrack = !initialized;
			
		initCar();
		
		if (!sendTrack) {
			return;
		}

		try {
			trackType.Builder request = trackType.newBuilder();
			request.setTrackName(comm.getCarModel().getStatus().getTrackFile().getAbsolutePath());

			client().getActionsService().aPITrackType(client().getController(), request.build());

		} catch (ServiceException e) {

			e.printStackTrace();
		}
	}
	
	public void initializeCar(boolean abs,boolean esp,boolean light,
			boolean fogLight,float maxSpeed,
			float fuel,float fuelConsumption,File trackFile){
		comm.getCarModel().getStatus().setABSenabled(abs);
		comm.getCarModel().getStatus().setESPenabled(esp);
		comm.getCarModel().getStatus().setLightSensorEnabled(light);
		comm.getCarModel().getStatus().setFogLightEnabled(fogLight);
		comm.getCarModel().getStatus().setTrackFile(trackFile);
		comm.getCarModel().getStatus().setMaxSpeed(maxSpeed);
		comm.getCarModel().getStatus().setFuelConsumption(fuelConsumption);		
	}
	
	private void initCar() {
		if (initialized) {
			return;
		}
		try {
			carInitType.Builder request = carInitType.newBuilder();
			request.setEspSensorExists(comm.getCarModel().getStatus().isESPenabled());
			request.setAbsSensorExists(comm.getCarModel().getStatus().isABSenabled());
			request.setLightSensorExists(comm.getCarModel().getStatus().isLightSensorEnabled());
			request.setFogLightSensorExists(comm.getCarModel().getStatus().isFogLightSensorEnabled());
			request.setFuelFilling(comm.getCarModel().getStatus().getFuel());
			request.setFuelConsumption(comm.getCarModel().getStatus().getFuelConsumption());
			request.setMaxSpeed(comm.getCarModel().getStatus().getMaxSpeed());
			request.setTrackName(comm.getCarModel().getStatus().getTrackFile().getAbsolutePath());

			client().getActionsService().aPICarInitType(client().getController(), request.build());
			
			initialized = true;

		} catch (ServiceException e) {

			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param client
	 */
	public void exitWidget(){
		initCar();
		try {
			widgetExit.Builder request = widgetExit.newBuilder();

			client().getActionsService().aPIWidgetExit(client().getController(), request.build());
			
		} catch (ServiceException e) {

			e.printStackTrace();
		}
	}

}
