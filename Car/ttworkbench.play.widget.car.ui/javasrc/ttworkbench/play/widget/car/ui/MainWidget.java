package ttworkbench.play.widget.car.ui;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;

import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import ttworkbench.play.widget.car.ui.html.CarWidget;
import ttworkbench.play.widget.car.ui.html.UIController;

import com.google.protobuf.BlockingService;
import com.testingtech.ttworkbench.core.util.ResourceUtil;
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
public class MainWidget extends AbstractDashboardWidget<CarModel, PROTO_API.ACTIONS.BlockingInterface> implements ICarModelListener, ICommunication {

	private CarModel model = new CarModel();
	private CarWidget carWidget;
	private UIController uiController = null;

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
		try {
			URL wwwLocation = ResourceUtil.getLocation(Activator.getDefault().getBundle().getSymbolicName(), "/www");
			File wwwRoot = new File(wwwLocation.getFile());
			//System.out.println(wwwRoot.toString()+"\n"+wwwRoot.exists());
			carWidget = new CarWidget(wwwRoot, getFactory().getDescriptor());
			carWidget.setController(new WidgetController(this));
			Control control = carWidget.createControl(parent);
			model.addListener(this);
			
			if(control instanceof Browser){
				uiController = new UIController((Browser)control);
			}else{
				System.err.println("Can't initiate incoming Eventservice!");
			}
			
			return control;
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new RuntimeException("Cannot instantiate car", e);
		} catch (Throwable e) {
			e.printStackTrace();
			throw new RuntimeException("Cannot instantiate car", e);
		}
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
		uiController.motor(model.getStatus().isEngineStarted());
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
		
		uiController.carSpeed(new Double(model.getStatus().getActualSpeed()).intValue());

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

	public ActionsClient getActionsClient() {
		return (ActionsClient)super.getActionsClient();
	}
}
