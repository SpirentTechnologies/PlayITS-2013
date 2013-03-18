package ttworkbench.play.widget.car.ui;

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

	public WidgetController(ICommunication comm) {
		this.comm = comm;
	}

	/**
	 * 
	 * @param client from com.testingtech.ttworkbench.play.dashboard.widget.AbstractDashboardWidget.getActionsClient()
	 * @throws IOException
	 */
	public void startEngine(){	

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
	public void stopEngine() throws IOException{	

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
	 */
	public void enableLightSensor(){
		try {
			carInitType.Builder request = carInitType.newBuilder();

			client().getActionsService().aPICarInitType(client().getController(), request.build());
			request.setLightSensorExists(true);

		} catch (ServiceException e) {

			System.err.println("Unable to enable light sensor! \n" + e.getMessage());
		}
	}

	/**
	 * 
	 * @param client
	 */
	public void disableLightSensor(){
		try {
			carInitType.Builder request = carInitType.newBuilder();

			client().getActionsService().aPICarInitType(client().getController(), request.build());
			request.setLightSensorExists(false);

		} catch (ServiceException e) {

			System.err.println("Unable to disable light sensor! \n" + e.getMessage());
		}
	}

	/**
	 * 
	 * @param client
	 */
	public void enableFogLight(){
		try {
			carInitType.Builder request = carInitType.newBuilder();

			client().getActionsService().aPICarInitType(client().getController(), request.build());
			request.setFogLightSensorExists(true);

		} catch (ServiceException e) {

			System.err.println("Unable to enable fog light! \n" + e.getMessage());
		}
	}

	/**
	 * 
	 * @param client
	 */

	public void disableFogLight(){
		try {
			carInitType.Builder request = carInitType.newBuilder();

			client().getActionsService().aPICarInitType(client().getController(), request.build());
			request.setFogLightSensorExists(false);

		} catch (ServiceException e) {

			System.err.println("Unable to disable fog light! \n" + e.getMessage());
		}
	}

	/**
	 * 
	 * @param client
	 * @param speed
	 */
	public void setMaxSpeed(float speed){
		try {
			carInitType.Builder request = carInitType.newBuilder();

			client().getActionsService().aPICarInitType(client().getController(), request.build());
			request.setMaxSpeed(speed);

		} catch (ServiceException e) {

			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param client
	 * @param speed
	 */
	public void changeSpeed(float speed){
		try {
			speedType.Builder request = speedType.newBuilder();

			client().getActionsService().aPISpeedType(client().getController(), request.build());
			request.setSpeed(speed);

		} catch (ServiceException e) {

			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param client
	 * @param value
	 */
	public void setTrack(String value){
		try {
			trackType.Builder request = trackType.newBuilder();

			client().getActionsService().aPITrackType(client().getController(), request.build());
			request.setTrackName(value);

		} catch (ServiceException e) {

			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param client
	 */
	public void enableABS(){
		try {
			carInitType.Builder request = carInitType.newBuilder();

			client().getActionsService().aPICarInitType(client().getController(), request.build());
			request.setAbsSensorExists(true);

		} catch (ServiceException e) {

			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param client
	 */
	public void disableABS(){
		try {
			carInitType.Builder request = carInitType.newBuilder();

			client().getActionsService().aPICarInitType(client().getController(), request.build());
			request.setAbsSensorExists(false);

		} catch (ServiceException e) {

			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param client
	 */
	public void enableESP(){
		try {
			carInitType.Builder request = carInitType.newBuilder();

			client().getActionsService().aPICarInitType(client().getController(), request.build());
			request.setEspSensorExists(true);

		} catch (ServiceException e) {

			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param client
	 */
	public void disableESP(){
		try {
			carInitType.Builder request = carInitType.newBuilder();

			client().getActionsService().aPICarInitType(client().getController(), request.build());
			request.setEspSensorExists(false);

		} catch (ServiceException e) {

			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param client
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
