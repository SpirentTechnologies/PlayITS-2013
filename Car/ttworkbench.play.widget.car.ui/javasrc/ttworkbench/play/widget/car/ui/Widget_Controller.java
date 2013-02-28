package ttworkbench.play.widget.car.ui;

import java.io.IOException;
import java.util.ArrayList;

import ttworkbench.play.widget.car.ui.model.CarStatusModel;
import ttworkbench.play.widget.car.ui.model.WarningType;

import com.google.protobuf.ServiceException;
import com.testingtech.ttworkbench.play.dashboard.widget.AbstractActionsClient;
import com.testingtech.ttworkbench.play.generated.PROTO_API.onOffEngineType;



public class Widget_Controller {
	
		private CarStatusModel model;
		private ArrayList<WarningType> warnings = new ArrayList<WarningType>();

		public Widget_Controller(){
			model = new CarStatusModel();
		}
		
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
			
			if(!model.isEngineStarted()) return;
			try {
				onOffEngineType.Builder request = onOffEngineType.newBuilder();
				request.setEngineStatus(false);
				client.getActionsService().aPIOnOffEngineType(client.getController(), request.build());
				// TODO response check
			} catch (ServiceException e) {
				
				e.printStackTrace();
			}
			
		}
	
}
