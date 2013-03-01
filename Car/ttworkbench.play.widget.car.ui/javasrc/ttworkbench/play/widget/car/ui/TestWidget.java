package ttworkbench.play.widget.car.ui;

import java.io.IOException;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import ttworkbench.play.widget.car.ui.ActionsClient;

import com.google.protobuf.BlockingService;
import com.testingtech.ttworkbench.play.dashboard.widget.AbstractDashboardWidget;
import com.testingtech.ttworkbench.play.dashboard.widget.IDashboard;
import com.testingtech.ttworkbench.play.dashboard.widget.IDashboardWidgetFactory;
import com.testingtech.ttworkbench.play.generated.PROTO_API;
import com.testingtech.ttworkbench.play.generated.PROTO_API.ACTIONS.BlockingInterface;
import ttworkbench.play.widget.car.ui.EventsServiceImpl;
import ttworkbench.play.widget.car.ui.html.CarWidget;

public class TestWidget extends AbstractDashboardWidget<CarModel, PROTO_API.ACTIONS.BlockingInterface> implements ICarModelListener {

	private CarModel model = new CarModel();
	private CarWidget carWidget;
	
	public TestWidget(
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
		carWidget = new CarWidget();
		return new Composite(parent, SWT.NONE);
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
	public void notifyModelChange() {
		// TODO Auto-generated method stub
		
	}

}
