package ttworkbench.play.widget.car.rt;

import java.io.IOException;

import com.testingtech.ttcn.annotation.ExternalFunction;
import com.testingtech.ttcn.tri.AnnotationsExternalFunctionPlugin;
import com.testingtech.ttworkbench.play.simulation.car.Simulation;

@ExternalFunction.Definitions(SUTRunner.class)
public class SUTRunner extends AnnotationsExternalFunctionPlugin {

	private static Simulation sut;
	private Object sutCreationLock = new Object();

	/**
	 * Start the simulator on given port
	 * @param p_serverPort the server port
	 * @return -1 on error, 0 means OK, -2 already running
	 */
	// external function xf_startSimulation(integer p_serverPort) return integer;
	@ExternalFunction(name = "xf_startSimulation", module = "CONFIG_CAR")
	public int xf_startSimulation(int p_serverPort) {
		synchronized (sutCreationLock) {
			if (sut == null) {
				sut = new Simulation();
				try {
					sut.startServer(p_serverPort);
					return 0;
				} catch (IOException e) {
					logError("Cannot start Simulation: "+stackTrace(e));
					return -1;
				}
			} else {
				logWarn("Simulation already running");
				return -2;
			}
		}
	}

	/**
	 * Stops the simulator on given port
	 * @param p_serverPort the server port
	 * @return -1 on error, 0 means OK, -2 not running
	 */
	// external function xf_stopSimulation(integer p_serverPort) return integer;
	@ExternalFunction(name = "xf_stopSimulation", module = "CONFIG_CAR")
	public int xf_stopSimulation(int p_serverPort) {
		synchronized (sutCreationLock) {
			if (sut != null) {
				try {
					sut.stopServer();
					sut = null;
					return 0;
				} catch (IOException e) {
					logError("Cannot stop Simulation: "+stackTrace(e));
					return -1;
				}
			} else {
				logWarn("Simulation not running");
				return -2;
			}
		}
	}

}
