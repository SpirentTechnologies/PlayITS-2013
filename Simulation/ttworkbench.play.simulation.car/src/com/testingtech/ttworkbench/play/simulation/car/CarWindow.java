package com.testingtech.ttworkbench.play.simulation.car;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

public class CarWindow {

	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			CarWindow window = new CarWindow();
			window.open(null);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Open the window.
	 */
	public void open(Car car) {
		Display display = new Display();
		Shell shell = new Shell(display);
		shell.setSize(550, 550);
		shell.setText("Simulation");
		new CarWidgetFrame(shell, SWT.NO_BACKGROUND, car, display).setBounds(10, 10, 500, 500);
		shell.open();
		shell.layout();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
	}

}
