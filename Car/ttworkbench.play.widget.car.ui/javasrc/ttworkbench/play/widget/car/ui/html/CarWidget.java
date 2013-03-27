package ttworkbench.play.widget.car.ui.html;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTError;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.BrowserFunction;
import org.eclipse.swt.browser.ProgressAdapter;
import org.eclipse.swt.browser.ProgressEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;

import ttworkbench.play.widget.car.ui.WidgetController;
import ttworkbench.play.widget.car.ui.model.EnumWarning;
import ttworkbench.play.widget.car.ui.model.GPSposition;
import ttworkbench.play.widget.car.ui.model.WarningType;

import com.testingtech.ttworkbench.core.ui.preferences.common.AbstractConfigurationBlock;
import com.testingtech.ttworkbench.play.dashboard.widget.DashboardWidgetFactoryDescriptor;

/**
 * 
 * @author Hirsch, Cypko
 *
 */
/**
 * CarWidget UI 
 * - creating browser window including car.html 
 * - data processing from javascript to java
 */

public class CarWidget {
	
	/**
	 * @param wwwRoot		root URL for the www folder
	 * @param widgetController		for the communication path from UI to the Model 
	 * @param browser		to set a web browser - for communication: html/javascript <--> Java
	 * @param uiController		to initialize communication path form Model to UI  	
	 */
	private final File wwwRoot;
	protected WidgetController widgetController;
	private final DashboardWidgetFactoryDescriptor descriptor;
	private Browser browser;
	private UIController uiController;

	/**
	 * to initialize object of CarWidget
	 * @param wwwRoot
	 * @param descriptor
	 */
	public CarWidget(File wwwRoot, DashboardWidgetFactoryDescriptor descriptor) {
		this.wwwRoot = wwwRoot;
		this.descriptor = descriptor;
	}

	/**
	 * 
	 * @param args
	 * @param wwwRoot
	 * @param shell - a window(/shell) to display the browser
	 * @param display
	 */
	public static void main (String [] args) {

		Display display = new Display();
		int shellWidth = 864;
		int shellHeight = 534 + 22;

		Shell shell = new Shell(display, SWT.CLOSE | SWT.MIN);
		shell.setSize(shellWidth, shellHeight);
		shell.setLayout(new FillLayout());

		Monitor primary = display.getPrimaryMonitor();
		Rectangle screenBounds = primary.getBounds();
		Rectangle shellSize = shell.getBounds();
		int xPosition = screenBounds.x + (screenBounds.width - shellSize.width) / 2;
		int yPosition = screenBounds.y + (screenBounds.height - shellSize.height) / 2;

		shell.setLocation (xPosition, yPosition);

		try {
			File wwwRoot = new File(new URL(CarWidget.class.getResource("/").toExternalForm()).getFile(), "../www");
			new CarWidget(wwwRoot.getAbsoluteFile(), new DashboardWidgetFactoryDescriptor("", "", "", null, null, null, null)).createControl(shell);
		} catch (MalformedURLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		shell.open();

		while (!shell.isDisposed ()) {
			if (!display.readAndDispatch ()) {
				display.sleep ();
			}
		}
		display.dispose ();
	}

	/**
	 * @param parent
	 * @return group
	 */
	public Control createControl(Composite parent) {
		Group group = AbstractConfigurationBlock.addGroup(parent, descriptor.getName());
		((GridData)group.getLayoutData()).widthHint = 500;
		((GridData)group.getLayoutData()).heightHint = 400;
		group.setToolTipText(descriptor.getDescription());
		GridData gd = new GridData(GridData.FILL_BOTH);
		gd.horizontalSpan = 2;
		group.setLayoutData(gd);

		try {
			browser = new Browser (group, SWT.WEBKIT);
		} catch (SWTError e) {
			browser = new Browser (group, SWT.NONE);
			System.out.println ("Could not instantiate Browser: " + e.getMessage ());
		}

		uiController = new UIController(browser);
		browser.setLayoutData(new GridData(GridData.FILL_BOTH));

		browser.addProgressListener (new ProgressAdapter () {
			public void completed (ProgressEvent event) {}
		});

		//Define each JavaScript function
		/** Init of javascript function used at initialization time */
		new CustomFunction(browser, "carType");
		new CustomFunction(browser, "trackType");

		/** declaration of javascript function used at later time */
		new CustomFunction(browser, "motor");
		new CustomFunction(browser, "abs");
		new CustomFunction(browser, "esp");
		new CustomFunction(browser, "speed");
		new CustomFunction(browser, "fogLight");
		new CustomFunction(browser, "lightSensor");
		new CustomFunction(browser, "sendWarning");
		new CustomFunction(browser, "widgetExit");

		new CustomFunction(browser, "initialize");

		/** URL root to the car.html */
		browser.setUrl(new File(wwwRoot, "car.html").toURI().toString());
		return group;
	}

	//Define JS Functions
	class CustomFunction extends BrowserFunction {
		/** @param browser
		 * @param name
		 * @param uiController
		 */
		CustomFunction (Browser browser, String name) {
			super (browser, name);
		}

		/* (non-Javadoc)
		 * @see org.eclipse.swt.browser.BrowserFunction#function(java.lang.Object[])
		 */
		/**
		 * function to catch (previously declared) javascript functions
		 */
		public Object function(final Object[] args) {

			//Get the name of the function and invoke that function in JAVA
			new Thread(new Runnable() {
				public void run() {
					execFunction(args);
				}
			}).start();
			return null;
		}

		private void execFunction(Object[] args) {
			if("motor".equals(this.getName())) {

				boolean motorOn = (Boolean)args[0];
				if(motorOn) {
					widgetController.startEngine();
				} else {
					widgetController.stopEngine();
				}
			}
			else if("initialize".equals(this.getName())){
				widgetController.initializeCar(
						(Boolean) args[0], 
						(Boolean) args[1], 
						(Boolean) args[2], 
						(Boolean) args[3], 
						((Double)args[4]).floatValue(), 
						((Double)args[5]).floatValue(), 
						((Double)args[6]).floatValue(), 
						(String)args[7]
						);
			}
			else if("speed".equals(this.getName())){
				float speed;
				try {
					speed = Float.parseFloat((String)args[0]);
				} catch (NumberFormatException e) {
					speed = 0;
				}
				widgetController.changeSpeed(speed);
			}
			else if("sendWarning".equals(this.getName())){
				WarningType warningType = new WarningType();
				warningType.setWarning(EnumWarning.getWarning(Integer.parseInt((String)args[2])));
				warningType.setGpsPosition(new GPSposition((Double)args[0], (Double)args[1]));
				widgetController.sendWarning(warningType);
			}
		}
	}

	/**
	 * @param widgetController
	 */
	public void setController(WidgetController widgetController) {
		this.widgetController = widgetController;
	}
	
	public void disableActions() {
		if (browser.isDisposed()) {
			return;
		}
		// TODO disable HTML buttons
		browser.setEnabled(false);
	}

	public void enableActions() {
		// TODO enable HTML buttons
		browser.setEnabled(true);
	}

	/**
	 * @return the uiController
	 */
	public UIController getUiController() {
		return uiController;
	}


}
