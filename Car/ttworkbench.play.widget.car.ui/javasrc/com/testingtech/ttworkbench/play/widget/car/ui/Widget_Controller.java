package com.testingtech.ttworkbench.play.widget.car.ui;

import java.io.IOException;

import com.google.protobuf.ServiceException;
import com.testingtech.ttworkbench.play.generated.PROTO_API.onOffEngineType;
import com.testingtech.ttworkbench.play.widget.car.ui.model.CarStatusModel;




public class Widget_Controller {
	
		private CarStatusModel model;
	
		public Widget_Controller(){
			model = new CarStatusModel();
		}
		
		/**
		 * 
		 * @param client from com.testingtech.ttworkbench.play.dashboard.widget.AbstractDashboardWidget.getActionsClient()
		 * @throws IOException
		 */
		public void startEngine(ActionsClient client) throws IOException{	
			
			if(model.isEngineStarted()) return;
			
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
