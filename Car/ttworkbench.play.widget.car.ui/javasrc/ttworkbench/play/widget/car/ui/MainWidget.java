package ttworkbench.play.widget.car.ui;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Set;

import org.eclipse.jdt.launching.SocketUtil;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;

import ttworkbench.play.widget.car.ui.html.CarWidget;
import ttworkbench.play.widget.car.ui.html.UIController;
import ttworkbench.play.widget.car.ui.model.GPSposition;

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
				PROTO_API.EVENTS.newReflectiveBlockingService(new EventsServiceImpl(getModel(),this));
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
	public void disableActions() {
		SWTUtil.sync(new Runnable() {
			public void run() {
				carWidget.disableActions();
			}
		});
	}

	@Override
	public void enableActions() {
		SWTUtil.sync(new Runnable() {
			public void run() {
				carWidget.enableActions();
			}
		});
	}

	@Override
	public void notifyEngineStatusChange() {
		SWTUtil.sync(new Runnable() {
			public void run() {
				if (uiController != null) {
					try {
						uiController.updateEngine(model.getStatus().isEngineStarted());
					} catch (Throwable e) {
						e.printStackTrace();
						SWTUtil.showStatusErrorMessage(null, "warning: "+e.toString());
					}
				}
			}
		});
	}

	@Override
	public void notifyABSStatusChange() {
		SWTUtil.sync(new Runnable() {
			public void run() {
				if (uiController != null) {
					try {
						uiController.updateABS(model.getStatus().isABSenabled());
					} catch (Throwable e) {
						e.printStackTrace();
						SWTUtil.showStatusErrorMessage(null, "warning: "+e.toString());
					}
				}
			}
		});

	}

	@Override
	public void notifyESPStatusChange() {
		SWTUtil.sync(new Runnable() {
			public void run() {
				if (uiController != null) {
					try {
						uiController.updateESP(model.getStatus().isESPenabled());
					} catch (Throwable e) {
						e.printStackTrace();
						SWTUtil.showStatusErrorMessage(null, "warning: "+e.toString());
					}
				}
			}
		});
	}



	@Override
	public void notifyGpsPositionChange() {
		SWTUtil.sync(new Runnable() {
			public void run() {
				if (uiController != null) {
					try {
						GPSposition gpsPosition = model.getStatus().getGpsPosition();
						uiController.updatePosition(gpsPosition.getLatitude(), gpsPosition.getLongitude());
					} catch (Throwable e) {
						e.printStackTrace();
						SWTUtil.showStatusErrorMessage(null, "warning: "+e.toString());
					}
				}
			}
		});

	}


	@Override
	public void notifyFillingStatusChange() {
		SWTUtil.sync(new Runnable() {
			public void run() {
				if (uiController != null) {
					try {
						uiController.updateFuel(model.getStatus().getFuel());
					} catch (Throwable e) {
						e.printStackTrace();
						SWTUtil.showStatusErrorMessage(null, "warning: "+e.toString());
					}
				}
			}
		});
	}



	@Override
	public void notifySpeedChange() {
		SWTUtil.sync(new Runnable() {
			public void run() {
				if (uiController != null) {
					try {
						uiController.updateSpeed(model.getStatus().getActualSpeed());
					} catch (Throwable e) {
						e.printStackTrace();
						SWTUtil.showStatusErrorMessage(null, "warning: "+e.toString());
					}
				}
			}
		});
	}

	@Override
	public void notifyWarningAdded() {
		SWTUtil.sync(new Runnable() {
			public void run() {
				if (uiController != null) {
					try {
						uiController.warning(model.getWarning());
					} catch (Throwable e) {
						e.printStackTrace();
						SWTUtil.showStatusErrorMessage(null, "warning: "+e.toString());
					}
				}
			}
		});
	}

	@Override
	public void notifyFogLightChange() {
		SWTUtil.sync(new Runnable() {
			public void run() {
				if (uiController != null) {
					try {
						uiController.updateFogLight(model.getStatus().isFogLightSensorEnabled());
					} catch (Throwable e) {
						e.printStackTrace();
						SWTUtil.showStatusErrorMessage(null, "warning: "+e.toString());
					}
				}
			}
		});
	}

	@Override
	public void notifyLightChange() {
		SWTUtil.sync(new Runnable() {
			public void run() {
				if (uiController != null) {
					try {
						uiController.updateLight(model.getStatus().isLightSensorEnabled());
					} catch (Throwable e) {
						SWTUtil.showStatusErrorMessage(null, "warning: "+e.toString());
					}
				}
			}
		});
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
	public void notifyFirstMessageFromSUT() {
		// TODO to remove

	}

}
