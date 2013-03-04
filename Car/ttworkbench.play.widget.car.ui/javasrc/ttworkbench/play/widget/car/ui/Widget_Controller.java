package ttworkbench.play.widget.car.ui;

import java.io.IOException;

import com.google.protobuf.ServiceException;
import com.testingtech.ttworkbench.play.generated.PROTO_API.onOffEngineType;
import com.testingtech.ttworkbench.play.generated.PROTO_API.carInitType;
import com.testingtech.ttworkbench.play.generated.PROTO_API.speedType;
import com.testingtech.ttworkbench.play.generated.PROTO_API.trackType;
import com.testingtech.ttworkbench.play.generated.PROTO_API.widgetExit;


/**
 * 
 * @author kensan
 *
 */
public class Widget_Controller {
	
	
		/**
		 * 
		 * @param client from com.testingtech.ttworkbench.play.dashboard.widget.AbstractDashboardWidget.getActionsClient()
		 * @throws IOException
		 */
		public void startEngine(ActionsClient client) throws IOException{	
			
			try {
				onOffEngineType.Builder request = onOffEngineType.newBuilder();
				request.setEngineStatus(true);
				
				client.getActionsService().aPIOnOffEngineType(client.getController(), request.build());
				// TODO response check
				
			} catch (ServiceException e) {
				
				e.printStackTrace();
			}
		}
		
		/**
		 * 
		 * @param client from com.testingtech.ttworkbench.play.dashboard.widget.AbstractDashboardWidget.getActionsClient()
		 * @throws IOException
		 */
		public void stopEngine(ActionsClient client) throws IOException{	
			
			try {
				onOffEngineType.Builder request = onOffEngineType.newBuilder();
				request.setEngineStatus(false);
				client.getActionsService().aPIOnOffEngineType(client.getController(), request.build());
				// TODO response check
			} catch (ServiceException e) {
				
				e.printStackTrace();
			}
			
		}
		
		/**
		 * 
		 * @param client
		 */
		public void enableLightSensor(ActionsClient client){
			try {
				carInitType.Builder request = carInitType.newBuilder();
				
				client.getActionsService().aPICarInitType(client.getController(), request.build());
				request.setLightSensorExists(true);
				
			} catch (ServiceException e) {
				
				e.printStackTrace();
			}
		}
		
		/**
		 * 
		 * @param client
		 */
		public void disableLightSensor(ActionsClient client){
			try {
				carInitType.Builder request = carInitType.newBuilder();
				
				client.getActionsService().aPICarInitType(client.getController(), request.build());
				request.setLightSensorExists(false);
				
			} catch (ServiceException e) {
				
				e.printStackTrace();
			}
		}
		
		/**
		 * 
		 * @param client
		 */
		public void enableFogLight(ActionsClient client){
			try {
				carInitType.Builder request = carInitType.newBuilder();
				
				client.getActionsService().aPICarInitType(client.getController(), request.build());
				request.setFogLightSensorExists(true);
				
			} catch (ServiceException e) {
				
				e.printStackTrace();
			}
		}
		
		/**
		 * 
		 * @param client
		 */
		
		public void disableFogLight(ActionsClient client){
			try {
				carInitType.Builder request = carInitType.newBuilder();
				
				client.getActionsService().aPICarInitType(client.getController(), request.build());
				request.setFogLightSensorExists(false);
				
			} catch (ServiceException e) {
				
				e.printStackTrace();
			}
		}
		
		/**
		 * 
		 * @param client
		 * @param speed
		 */
		public void setMaxSpeed(ActionsClient client, float speed){
			try {
				carInitType.Builder request = carInitType.newBuilder();
				
				client.getActionsService().aPICarInitType(client.getController(), request.build());
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
		public void changeSpeed(ActionsClient client, float speed){
			try {
				speedType.Builder request = speedType.newBuilder();
				
				client.getActionsService().aPISpeedType(client.getController(), request.build());
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
		public void setTrack(ActionsClient client, String value){
			try {
				trackType.Builder request = trackType.newBuilder();
				
				client.getActionsService().aPITrackType(client.getController(), request.build());
				request.setTrackName(value);
				
			} catch (ServiceException e) {
				
				e.printStackTrace();
			}
		}
		
		/**
		 * 
		 * @param client
		 */
		public void enableABS(ActionsClient client){
			try {
				carInitType.Builder request = carInitType.newBuilder();
				
				client.getActionsService().aPICarInitType(client.getController(), request.build());
				request.setAbsSensorExists(true);
				
			} catch (ServiceException e) {
				
				e.printStackTrace();
			}
		}
		
		/**
		 * 
		 * @param client
		 */
		public void disableABS(ActionsClient client){
			try {
				carInitType.Builder request = carInitType.newBuilder();
				
				client.getActionsService().aPICarInitType(client.getController(), request.build());
				request.setAbsSensorExists(false);
				
			} catch (ServiceException e) {
				
				e.printStackTrace();
			}
		}
		
		/**
		 * 
		 * @param client
		 */
		public void enableESP(ActionsClient client){
			try {
				carInitType.Builder request = carInitType.newBuilder();
				
				client.getActionsService().aPICarInitType(client.getController(), request.build());
				request.setEspSensorExists(true);
				
			} catch (ServiceException e) {
				
				e.printStackTrace();
			}
		}
	
		/**
		 * 
		 * @param client
		 */
		public void disableESP(ActionsClient client){
			try {
				carInitType.Builder request = carInitType.newBuilder();
				
				client.getActionsService().aPICarInitType(client.getController(), request.build());
				request.setEspSensorExists(false);
				
			} catch (ServiceException e) {
				
				e.printStackTrace();
			}
		}
		
		/**
		 * 
		 * @param client
		 */
		public void exitWidget(ActionsClient client){
			try {
				widgetExit.Builder request = widgetExit.newBuilder();
				
				client.getActionsService().aPIWidgetExit(client.getController(), request.build());
				
			} catch (ServiceException e) {
				
				e.printStackTrace();
			}
		}
		
		
}
