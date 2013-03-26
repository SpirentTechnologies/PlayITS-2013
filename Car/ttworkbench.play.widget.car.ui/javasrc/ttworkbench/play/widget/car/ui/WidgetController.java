package ttworkbench.play.widget.car.ui;

import java.io.IOException;

import ttworkbench.play.widget.car.ui.model.CarStatusModel;
import ttworkbench.play.widget.car.ui.model.WarningType;
import ttworkbench.play.widget.car.ui.model.EnumWarning;

import com.testingtech.ttworkbench.play.generated.PROTO_API;
import com.testingtech.ttworkbench.play.generated.PROTO_API.carInitType;
import com.testingtech.ttworkbench.play.generated.PROTO_API.gpsPosition;
import com.testingtech.ttworkbench.play.generated.PROTO_API.onOffEngineType;
import com.testingtech.ttworkbench.play.generated.PROTO_API.speedType;
import com.testingtech.ttworkbench.play.generated.PROTO_API.warning.EnumValue;
import com.testingtech.ttworkbench.play.generated.PROTO_API.warningType;
import com.testingtech.ttworkbench.play.generated.PROTO_API.widgetExit;


/**
 * 
 * @author Björn
 *
 */
public class WidgetController {

	private final ICommunication comm;
	private boolean initialized;

	/**
	 * sends messages to TTCN 3 layer 
	 * @param ICommunication interface give access to some functions in Mainwidget
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

		} catch (Throwable e) {

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
		} catch (Throwable e) {

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

		} catch (Throwable e) {

			System.out.println("Can't change speed! " + e.getMessage());
		}
	}

	/**
	 * send a new Warning to SUT
	 * @param warning
	 */
	public void sendWarning(WarningType wt){
		try {
			warningType.Builder request = warningType.newBuilder();
			gpsPosition.Builder gpsBuilder = gpsPosition.newBuilder();
			request.setCarId(comm.getCarModel().getStatus().getId());
			gpsBuilder.setLatitude((float)wt.getGpsPosition().getLatitude());
			gpsBuilder.setLongitude((float)wt.getGpsPosition().getLongitude());
			request.setGpsPos(gpsBuilder);

			request.setWarningName(PROTO_API.warning.newBuilder().setEnumValue(convertWarn(wt.getWarning())));
			request.setPriority(-1);
			client().getActionsService().aPIWarningType(client().getController(), request.build());

		} catch (Throwable e) {

			System.out.println("Can't send Warning! " + e.getMessage());
		}
	}

	
	/**
	 * Converts EnumWarning to EnumValue 
	 * @param EnumWarning
	 * @return EnumValue
	 */
	private static EnumValue convertWarn(EnumWarning warning) {
		EnumValue enumValue;
		switch (warning) {
		case ACCIDENT:
			enumValue = EnumValue.accident;
			break;
		case DEER:
			enumValue = EnumValue.deer;
			break;
		case FOG:
			enumValue = EnumValue.fog;
			break;
		case ICE:
			enumValue = EnumValue.ice;
			break;
		case RAIN:
			enumValue = EnumValue.rain;
			break;
		case SNOW:
			enumValue = EnumValue.snow;
			break;
		default:
			throw new IllegalArgumentException("Unknown warning type "+warning);
		}
		return enumValue;
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

		//disable Actions until first message with car id arrive
		comm.disableActions();
		CarStatusModel status = new CarStatusModel();

		status.setABSenabled(abs);
		status.setESPenabled(esp);
		status.setLightEnabled(light);
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
			request.setLightSensorExists(status.isLightEnabled());
			request.setFogLightSensorExists(status.isFogLightSensorEnabled());
			request.setFuelFilling(status.getFuel());
			request.setFuelConsumption(status.getFuelConsumption());
			request.setMaxSpeed(status.getMaxSpeed());
			request.setTrackName(status.getTrack());

			client().getActionsService().aPICarInitType(client().getController(), request.build());

			initialized = true;

		} catch (Throwable e) {

			System.out.println("Initialize failed! " + e.getMessage());
		}
	}

	/**
	 * Send an exit message to SUT
	 */
	public void exitWidget(){

		try {
			widgetExit.Builder request = widgetExit.newBuilder();

			client().getActionsService().aPIWidgetExit(client().getController(), request.build());

		} catch (Throwable e) {

			e.printStackTrace();
		}
	}
}
