package ttworkbench.play.widget.car.ui;

import java.io.IOException;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import ttworkbench.play.widget.car.ui.ActionsClient;

import com.google.protobuf.BlockingService;
import com.testingtech.ttworkbench.play.dashboard.widget.AbstractDashboardWidget;
import com.testingtech.ttworkbench.play.dashboard.widget.IDashboard;
import com.testingtech.ttworkbench.play.dashboard.widget.IDashboardWidgetFactory;
import com.testingtech.ttworkbench.play.generated.PROTO_API;
import com.testingtech.ttworkbench.play.generated.PROTO_API.ACTIONS.BlockingInterface;

public class TestWidget extends AbstractDashboardWidget<ICarModel, PROTO_API.ACTIONS.BlockingInterface> implements ICarModel {

	public TestWidget(
			IDashboardWidgetFactory<ICarModel, BlockingInterface> dashboardWidgetFactory,
			IDashboard dashboard) {
		super(dashboardWidgetFactory, dashboard);

	}

	@Override
	public void updateStatus(int num, long statusCode, String statusMessage) {
		// TODO Auto-generated method stub

	}

	@Override
	public Control createWidgetControl(Composite arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ICarModel getModel() {
		return this;
	}

	public ActionsClient createActionsClient(String host, int actionsServicePort) throws IOException{
			ActionsClient actionsClient = new ActionsClient();
		    actionsClient.connect(host, actionsServicePort);
		    return actionsClient;
	}

	@Override
	public BlockingService createEventsService(int arg0) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected void disableActions() {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void enableActions() {
		// TODO Auto-generated method stub
		
	}

}
