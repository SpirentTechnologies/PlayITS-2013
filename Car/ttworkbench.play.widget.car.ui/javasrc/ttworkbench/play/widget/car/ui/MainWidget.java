package ttworkbench.play.widget.car.ui;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Set;

import org.eclipse.jdt.launching.SocketUtil;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import ttworkbench.play.widget.car.ui.html.CarWidget;
import ttworkbench.play.widget.car.ui.html.UIController;

import com.google.protobuf.BlockingService;
import com.testingtech.ttworkbench.core.ui.SWTUtil;
import com.testingtech.ttworkbench.core.util.ResourceUtil;
import com.testingtech.ttworkbench.play.dashboard.widget.AbstractDashboardWidget;
import com.testingtech.ttworkbench.play.dashboard.widget.IDashboard;
import com.testingtech.ttworkbench.play.dashboard.widget.IDashboardWidgetFactory;
import com.testingtech.ttworkbench.play.generated.PROTO_API;
import com.testingtech.ttworkbench.play.generated.PROTO_API.ACTIONS.BlockingInterface;
import com.testingtech.tworkbench.ttman.server.api.Parameter;
import com.testingtech.util.SetUtil;

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
			
			uiController = carWidget.getUiController();
			
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
		carWidget.disableActions();
	}

	@Override
	protected void enableActions() {
		carWidget.enableActions();
	}

	@Override
	public void notifyEngineStatusChange() {
		
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
		if (uiController != null) {
			// dropped update if no GUI initialized yet
			uiController.updatePosition(model.getStatus().getGpsPosition().getLatitude(), model.getStatus().getGpsPosition().getLatitude());
		}
	}



	@Override
	public void notifyFillingStatusChange() {
		// TODO Auto-generated method stub

	}



	@Override
	public void notifySpeedChange() {

	}

	@Override
	public void notifyWarningAdded() {
		uiController.warning(model.getWarning());
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

	@Override
	protected void initializeCommunication() {
		
		try {
      com.testingtech.ttworkbench.play.simulation.car.Activator.startSimulation();
    } catch (IOException e) {
      SWTUtil.createErrorDialogAsync("Error", "Error starting Simulation", e, null, getClass().getName());
    }
		
		super.initializeCommunication();
	}

	public Set<Parameter> getModuleParameters() {
	  int simulationPort = 0;
	  try {
	    simulationPort = com.testingtech.ttworkbench.play.simulation.car.Activator.getSimulationPort();
	  } catch (IOException e) {
	    SWTUtil.createErrorDialogAsync("Error", "Error starting Simulation", e, null, getClass().getName());
	  }
	  return SetUtil.set(
	      newModuleParameter("Parameters.EVENTS_WIDGET_TCP_PORT", getEventsServicePort()),
	      newModuleParameter("Parameters.ACTIONS_WIDGET_TCP_PORT", getActionsServicePort()),
	      newModuleParameter("Parameters.EVENTS_CAR_TCP_PORT", SocketUtil.findFreePort()),
	      newModuleParameter("Parameters.ACTIONS_CAR_TCP_PORT", simulationPort)
	      );
	}
	
}
