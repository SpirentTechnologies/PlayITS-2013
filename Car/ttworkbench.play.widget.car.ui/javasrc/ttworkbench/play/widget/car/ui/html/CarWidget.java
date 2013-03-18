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

import com.testingtech.ttworkbench.core.ui.preferences.common.AbstractConfigurationBlock;
import com.testingtech.ttworkbench.play.dashboard.widget.DashboardWidgetFactoryDescriptor;

/**
 * @author kensan, andre
 *
 */
public class CarWidget {

	private final File wwwRoot;
	protected WidgetController widgetController;
	private final DashboardWidgetFactoryDescriptor descriptor;
	
	UIController uiController;

	public UIController getUiController() {
		return uiController;
	}

	/**
	 * @param wwwRoot
	 * @param descriptor
	 */
	public CarWidget(File wwwRoot, DashboardWidgetFactoryDescriptor descriptor) {
		this.wwwRoot = wwwRoot;
		this.descriptor = descriptor;
	}

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
	 * @return browser
	 */
	/**
	 * @param parent
	 * @return
	 */
	public Control createControl(Composite parent) {
	    Group group = AbstractConfigurationBlock.addGroup(parent, descriptor.getName());
	    ((GridData)group.getLayoutData()).widthHint = 500;
	    ((GridData)group.getLayoutData()).heightHint = 400;
	    group.setToolTipText(descriptor.getDescription());
	    GridData gd = new GridData(GridData.FILL_BOTH);
	    gd.horizontalSpan = 2;
	    group.setLayoutData(gd);

		Browser bw;
		try {
			bw = new Browser (group, SWT.WEBKIT);
		} catch (SWTError e) {
			bw = new Browser (group, SWT.NONE);
			System.out.println ("Could not instantiate Browser: " + e.getMessage ());
		}
		
		bw.setLayoutData(new GridData(GridData.FILL_BOTH));
		
		final Browser browser = bw;
		browser.addProgressListener (new ProgressAdapter () {
			public void completed (ProgressEvent event) {}
		});
		
		uiController = new UIController(browser);

		//Define each JavaScript function
		//INIT:
		new CustomFunction(bw, "carType");
		new CustomFunction(bw, "trackType");
		
		//DYNAMIC:
		new CustomFunction(bw, "motor");
		new CustomFunction(bw, "abs");
		new CustomFunction(bw, "esp");
		new CustomFunction(bw, "speed");
		new CustomFunction(bw, "fogLight");
		new CustomFunction(bw, "lightSensor");
		new CustomFunction(bw, "warning");
		new CustomFunction(bw, "widgetExit");

		browser.setUrl(new File(wwwRoot, "car.html").toURI().toString());
		return browser;
	}
	
	//Define JS Functions
	class CustomFunction extends BrowserFunction {
	    
	    /**
		 * @param browser
		 * @param name
		 * @param uiController
		 */
		CustomFunction (Browser browser, String name) {
			super (browser, name);
		}
		
		/* (non-Javadoc)
		 * @see org.eclipse.swt.browser.BrowserFunction#function(java.lang.Object[])
		 */
		public Object function(Object[] args) {
			
			Object returnValue = null;
			
			//Get the name of the function and invoke that function in JAVA
			
			/**
			 * MOTOR
			 */
			if(this.getName() == "motor") {
				boolean motorOn = Boolean.parseBoolean(args[0].toString());
				
				try {
					if(motorOn) {
						widgetController.startEngine();
					} else {
						widgetController.stopEngine();
					}
				} catch(Exception e) {
					//TODO: handle exception in widgetController
				}
			
			/**
			 * ABS
			 */
			} else if(this.getName() == "abs") {
				boolean absOn = Boolean.parseBoolean(args[0].toString());
				
				if(absOn) {
					widgetController.enableABS();
				} else {
					widgetController.disableABS();
				}
				
			/**
			 * ESP
			 */
			} else if(this.getName() == "esp") {
				boolean espOn = Boolean.parseBoolean(args[0].toString());
				
				if(espOn) {
					widgetController.enableESP();
				} else {
					widgetController.disableESP();
				}
				
			/**
			 * SPEED
			 */
			} else if(this.getName() == "speed") {
				float speed = Float.parseFloat(args[0].toString());
				widgetController.changeSpeed(speed);
			
			/**
			 * FOG LIGHT
			 */
			} else if(this.getName() == "fogLight") {
				boolean fogLightOn = Boolean.parseBoolean((args[0].toString()));
				
				if(fogLightOn) {
					widgetController.enableFogLight();
				} else {
					widgetController.disableFogLight();
				}
			
			/**
			 * LIGHT SENSOR
			 */
			} else if(this.getName() == "lightSensor") {
				boolean lightSensorOn = Boolean.parseBoolean((args[0].toString()));
				
				if(lightSensorOn) {
					widgetController.enableLightSensor();
				} else {
					widgetController.disableLightSensor();
				}
			
			/**
			 * WARNING
			 */
			} else if(this.getName() == "warning") {
				String warning = args[0].toString();
				
				//deer, rain, accident, snow, fog 
				
				if(warning == "deer") {
					widgetController.enableLightSensor();
				} else {
					widgetController.disableLightSensor();
				}
				
			} else {
				System.out.println("Invalid function!");
			}
			
			return returnValue;
		}
	}

	/**
	 * @param widgetController
	 */
	public void setController(WidgetController widgetController) {
		this.widgetController = widgetController;
	}
}
