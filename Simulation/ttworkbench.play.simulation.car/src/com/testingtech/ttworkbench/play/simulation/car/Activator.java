package com.testingtech.ttworkbench.play.simulation.car;

import java.io.IOException;

import org.eclipse.jdt.launching.SocketUtil;
import org.eclipse.ui.plugin.AbstractUIPlugin;
import org.osgi.framework.BundleContext;


public class Activator extends AbstractUIPlugin {

	public void start(BundleContext context) throws Exception {
		super.start(context);
		
		startSimulation();
	}

	public void stop(BundleContext context) throws Exception {
		super.stop(context);
		
		stopSimulation();
	}

	private static Simulation simulation;
	
	public static synchronized void startSimulation() throws IOException {
		if (simulation == null) {
			int serverPort = SocketUtil.findFreePort();
			simulation = new Simulation();
			simulation.startServer(serverPort);
		}
	}
	
	public static synchronized void stopSimulation() throws IOException {
		if (simulation != null) {
			simulation.stopServer();
			simulation = null;
		}
	}
	
	public static int getSimulationPort() throws IOException {
		startSimulation();
		return simulation.getServerPort();
	}
}
