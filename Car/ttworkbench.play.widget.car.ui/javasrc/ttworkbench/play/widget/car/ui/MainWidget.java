package ttworkbench.play.widget.car.ui;

import java.io.File;
import java.io.IOException;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import ttworkbench.play.widget.car.ui.html.CarWidget;
import com.google.protobuf.BlockingService;
import com.testingtech.ttworkbench.play.dashboard.widget.AbstractDashboardWidget;
import com.testingtech.ttworkbench.play.dashboard.widget.IDashboard;
import com.testingtech.ttworkbench.play.dashboard.widget.IDashboardWidgetFactory;
import com.testingtech.ttworkbench.play.generated.PROTO_API;
import com.testingtech.ttworkbench.play.generated.PROTO_API.ACTIONS.BlockingInterface;

/**
 * 
 * @author kensan
 *
 */
public class MainWidget extends AbstractDashboardWidget<CarModel, PROTO_API.ACTIONS.BlockingInterface> implements ICarModelListener {

	private CarModel model = new CarModel();
	private CarWidget carWidget;
	
	public MainWidget(
			IDashboardWidgetFactory<CarModel, BlockingInterface> dashboardWidgetFactory,
			IDashboard dashboard) {
		super(dashboardWidgetFactory, dashboard);

	}
	
	public BlockingService createEventsService(int eventsServicePortNumber) {
	    BlockingService eventsService = 
	        PROTO_API.EVENTS.newReflectiveBlockingService(new EventsServiceImpl(getModel()));
	    return eventsService;
	  }


	@Override
	public Control createWidgetControl(Composite parent) {
		File wwwRoot = new File(Activator.getDefault().getBundle().getLocation().replace("reference:file:/", ""), "/www");
		//System.out.println(wwwRoot.toString()+"\n"+wwwRoot.exists());
		carWidget = new CarWidget(wwwRoot);
		//TODO
		//f.e.: carWidget.setActionClient(this.getActionsClient());
		return carWidget.createControl(parent);
	}

	@Override
	public CarModel getModel() {
		return model;
	}

	public ActionsClient createActionsClient(String host, int actionsServicePort) throws IOException{
			ActionsClient actionsClient = new ActionsClient();
		    actionsClient.connect(host, actionsServicePort);
		    return actionsClient;
	}


	@Override
	protected void disableActions() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void enableActions() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notifyEngineStatusChange() {
		// TODO Auto-generated method stub
		//f.e.: CarWidget.setEngineStatus(model.getStatus().isEngineStarted());
		
	}

	@Override
	public void notifyABSStatusChange() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notifyESPStatusChange() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void notifyGpsPositionChange() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void notifyFillingStatusChange() {
		// TODO Auto-generated method stub
		
	}



	@Override
	public void notifySpeedChange() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notifyWarningAdded() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notifyFogLightChange() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void notifyLightChange() {
		// TODO Auto-generated method stub
		
	}


}
