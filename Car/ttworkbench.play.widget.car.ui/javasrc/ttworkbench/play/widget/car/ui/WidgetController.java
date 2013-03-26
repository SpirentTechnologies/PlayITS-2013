package ttworkbench.play.widget.car.ui;

import java.io.IOException;

import ttworkbench.play.widget.car.ui.model.CarStatusModel;
import ttworkbench.play.widget.car.ui.model.WarningType;

import com.google.protobuf.ServiceException;
import com.testingtech.ttworkbench.play.generated.PROTO_API.carInitType;
import com.testingtech.ttworkbench.play.generated.PROTO_API.gpsPosition;
import com.testingtech.ttworkbench.play.generated.PROTO_API.onOffEngineType;
import com.testingtech.ttworkbench.play.generated.PROTO_API.speedType;
import com.testingtech.ttworkbench.play.generated.PROTO_API.warningType;
import com.testingtech.ttworkbench.play.generated.PROTO_API.widgetExit;


/**
 * 
 * @author kensan
 *
 */
public class WidgetController {

	private final ICommunication comm;
	private boolean initialized;

	/**
	 * The constructor
	 * @param comm Communication to be built up
	 */
	public WidgetController(ICommunication comm) {
		this.comm = comm;
	}

	/**
	 * Sets engine status to started
	 * @throws IOException
	 */
	public void startEngine(){	

		try {
			onOffEngineType.Builder request = onOffEngineType.newBuilder();
			request.setEngineStatus(true);
			request.setCarId(comm.getCarModel().getStatus().getId());
			client().getActionsService().aPIOnOffEngineType(client().getController(), request.build());

		} catch (ServiceException e) {

			System.err.println("Unable to start engine! \n" + e.getMessage());
		}
	}

	private ActionsClient client() {
		return comm.getActionsClient();
	}

	/**
	 * Sets engine status to stopped
	 * @throws IOException
	 */
	public void stopEngine(){	

		try {
			onOffEngineType.Builder request = onOffEngineType.newBuilder();
			request.setEngineStatus(false);
			request.setCarId(comm.getCarModel().getStatus().getId());
			client().getActionsService().aPIOnOffEngineType(client().getController(), request.build());
			// TODO response check
		} catch (ServiceException e) {

			System.err.println("Unable to stop Engine! \n" + e.getMessage());
		}

	}


	/**
	 * Changes speed status
	 * @param speed
	 */
	public void changeSpeed(float speed){
		try {
			speedType.Builder request = speedType.newBuilder();
			request.setSpeed(speed);
			CarStatusModel status = comm.getCarModel().getStatus();
			request.setCarId(status.getId());
		
			client().getActionsService().aPISpeedType(client().getController(), request.build());

		} catch (ServiceException e) {

			System.out.println("Can't change speed! " + e.getMessage());
		}
	}

	public void sendWarning(WarningType warning){
		try {
			warningType.Builder request = warningType.newBuilder();
			gpsPosition.Builder gpsBuilder = gpsPosition.newBuilder();
			request.setCarId(comm.getCarModel().getStatus().getId());
			gpsBuilder.setLatitude((float)warning.getGpsPosition().getLatitude());
			gpsBuilder.setLongitude((float)warning.getGpsPosition().getLongitude());
			request.setGpsPos(gpsBuilder);

			client().getActionsService().aPIWarningType(client().getController(), request.build());

		} catch (ServiceException e) {

			System.out.println("Can't change speed! " + e.getMessage());
		}
	}

	/**
	 * Sets the track file
	 * @param trackFile
	 */
	//	public void setTrack(File trackFile){
	//		CarStatusModel status = comm.getCarModel().getStatus();
	//		status.setTrackFile(trackFile);
	//
	//		boolean sendTrack = !initialized;
	//			
	//		
	//		if (!sendTrack) {
	//			return;
	//		}
	//
	//		try {
	//			trackType.Builder request = trackType.newBuilder();
	//			request.setTrackName(status.getTrackFile().getAbsolutePath());
	//			request.setCarId(status.getId());
	//
	//			client().getActionsService().aPITrackType(client().getController(), request.build());
	//
	//		} catch (ServiceException e) {
	//
	//			System.out.println("Can't set track! " + e.getMessage());
	//		}
	//	}

	/**
	 * is called from view, to initialize a new car, sets the car status attributes
	 * @param abs
	 * @param esp
	 * @param light
	 * @param fogLight
	 * @param maxSpeed
	 * @param fuel
	 * @param fuelConsumption
	 * @param trackFile
	 */
	public void initializeCar(boolean abs,boolean esp,boolean light,
			boolean fogLight,float maxSpeed,
			float fuel,float fuelConsumption,String track){

		//disable Actions until first message with car id arrive
		comm.disableActions();
		CarStatusModel status = new CarStatusModel();

		status.setABSenabled(abs);
		status.setESPenabled(esp);
		status.setLightSensorEnabled(light);
		status.setFogLightEnabled(fogLight);
		status.setTrackFile(track);
		status.setMaxSpeed(maxSpeed);
		status.setFuel(fuel);
		status.setFuelConsumption(fuelConsumption);

		initCar(status);

	}


	/**
	 * Sends carInitType to SUT
	 * 
	 */
	private void initCar(CarStatusModel status) {
		if (initialized) {
			return;
		}
		try {
			carInitType.Builder request = carInitType.newBuilder();
			request.setEspSensorExists(status.isESPenabled());
			request.setAbsSensorExists(status.isABSenabled());
			request.setLightSensorExists(status.isLightSensorEnabled());
			request.setFogLightSensorExists(status.isFogLightSensorEnabled());
			request.setFuelFilling(status.getFuel());
			request.setFuelConsumption(status.getFuelConsumption());
			request.setMaxSpeed(status.getMaxSpeed());
			request.setTrackName(status.getTrack());

			client().getActionsService().aPICarInitType(client().getController(), request.build());

			initialized = true;

		} catch (ServiceException e) {

			System.out.println("Initialize failed! " + e.getMessage());
		}
	}

	/**
	 * Exits the widget
	 */
	public void exitWidget(){

		try {
			widgetExit.Builder request = widgetExit.newBuilder();

			client().getActionsService().aPIWidgetExit(client().getController(), request.build());

		} catch (ServiceException e) {

			e.printStackTrace();
		}
	}
}
