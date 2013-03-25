package ttworkbench.play.widget.car.ui;

import java.io.File;
import java.io.IOException;

import ttworkbench.play.widget.car.ui.model.CarStatusModel;

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
		
		initCar();

		try {
			onOffEngineType.Builder request = onOffEngineType.newBuilder();
			request.setEngineStatus(true);
			//request.setCarId(comm.getCarModel().getStatus().getId());
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
	 * Sets engine status to stopped
	 * @throws IOException
	 */
	public void stopEngine(){	
		initCar();

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
		initCar();
		try {
			speedType.Builder request = speedType.newBuilder();
			request.setSpeed(speed);
			request.setCarId(comm.getCarModel().getStatus().getId());

			client().getActionsService().aPISpeedType(client().getController(), request.build());

		} catch (ServiceException e) {

			System.out.println("Can't change speed! " + e.getMessage());
		}
	}

	/**
	 * Sets the track file
	 * @param trackFile
	 */
	public void setTrack(File trackFile){
		CarStatusModel status = comm.getCarModel().getStatus();
		status.setTrackFile(trackFile);

		boolean sendTrack = !initialized;
			
		initCar();
		
		if (!sendTrack) {
			return;
		}

		try {
			trackType.Builder request = trackType.newBuilder();
			request.setTrackName(status.getTrackFile().getAbsolutePath());
			request.setCarId(status.getId());

			client().getActionsService().aPITrackType(client().getController(), request.build());

		} catch (ServiceException e) {

			System.out.println("Can't set track! " + e.getMessage());
		}
	}
	
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
		File trackFile = new File(track);
		CarStatusModel status = comm.getCarModel().getStatus();
		status.setABSenabled(abs);
		status.setESPenabled(esp);
		status.setLightSensorEnabled(light);
		status.setFogLightEnabled(fogLight);
		status.setTrackFile(trackFile);
		status.setMaxSpeed(maxSpeed);
		status.setFuelConsumption(fuelConsumption);
		initCar();

	}
	
	
	/**
	 * Sends carInitType to SUT
	 * 
	 */
	private void initCar() {
		if (initialized) {
			return;
		}
		try {
			carInitType.Builder request = carInitType.newBuilder();
			CarStatusModel status = comm.getCarModel().getStatus();
			request.setEspSensorExists(status.isESPenabled());
			request.setAbsSensorExists(status.isABSenabled());
			request.setLightSensorExists(status.isLightSensorEnabled());
			request.setFogLightSensorExists(status.isFogLightSensorEnabled());
			request.setFuelFilling(status.getFuel());
			request.setFuelConsumption(status.getFuelConsumption());
			request.setMaxSpeed(status.getMaxSpeed());
			request.setTrackName(status.getTrackFile().getAbsolutePath());

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
		initCar();
		try {
			widgetExit.Builder request = widgetExit.newBuilder();

			client().getActionsService().aPIWidgetExit(client().getController(), request.build());
			
		} catch (ServiceException e) {

			e.printStackTrace();
		}
	}

}
