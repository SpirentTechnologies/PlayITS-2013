package ttworkbench.play.widget.car.ui;

import java.io.IOException;
import java.util.Set;

import org.eclipse.jdt.launching.SocketUtil;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;

import ttworkbench.play.widget.car.ui.model.GPSposition;

import com.google.protobuf.BlockingService;
import com.testingtech.ttworkbench.core.ui.CommonColors;
import com.testingtech.ttworkbench.core.ui.SWTUtil;
import com.testingtech.ttworkbench.core.ui.preferences.common.AbstractConfigurationBlock;
import com.testingtech.ttworkbench.play.dashboard.widget.AbstractDashboardWidget;
import com.testingtech.ttworkbench.play.dashboard.widget.DashboardWidgetFactoryDescriptor;
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
	private WidgetController widgetController;
	private InitializeFrame initializeFrame;
	private CarWidgetFrame carWidgetFrame;
	private Group group;

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
			DashboardWidgetFactoryDescriptor descriptor = getFactory().getDescriptor();
			group = AbstractConfigurationBlock.addGroup(parent, descriptor.getName());
			group.setToolTipText(descriptor.getDescription());
			group.setBackground(CommonColors.LOGGING_GREY);
			group.setLayout(null);
			
			widgetController = new WidgetController(this);
			initializeFrame = new InitializeFrame(group,SWT.NO_BACKGROUND,this);
			initializeFrame.setBounds(0, 0, 500, 400);
			carWidgetFrame = new CarWidgetFrame(group, SWT.NO_BACKGROUND,this);
			carWidgetFrame.setBounds(0, 0, 500, 400);
			carWidgetFrame.setVisible(false);
			
			model.addListener(this);
			
			return group;
			
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
		initializeFrame.setEnabled(false);
	}

	@Override
	protected void enableActions() {
		initializeFrame.setEnabled(true);	}

	@Override
	public void notifyEngineStatusChange() {
		Display.getDefault().syncExec(new Runnable() {
		    public void run() {
		    	carWidgetFrame.changeEngineView(model.getStatus().isEngineStarted());				
		    }
		});
		
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
		Display.getDefault().syncExec(new Runnable() {
		    public void run() {
		    	GPSposition gpsPosition = model.getStatus().getGpsPosition();
				carWidgetFrame.changeMapLocation(gpsPosition.getLatitude(), gpsPosition.getLongitude());		
		    }
		});
			// dropped update if no GUI initialized yet
			
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

	@Override
	public CarModel getCarModel() {
		return model;
	}

	@Override
	public WidgetController getWidgetController() {
		// TODO Auto-generated method stub
		return widgetController;
	}

	@Override
	public synchronized void notifyFirstMessageFromSUT() {
		Display.getDefault().syncExec(new Runnable() {
		    public void run() {
				carWidgetFrame.setVisible(true);				
		    }
		});

	}
	
}
