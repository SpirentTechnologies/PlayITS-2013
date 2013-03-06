package ttworkbench.play.widget.car.ui.html;


import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTError;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.browser.BrowserFunction;
import org.eclipse.swt.browser.LocationAdapter;
import org.eclipse.swt.browser.LocationEvent;
import org.eclipse.swt.browser.ProgressAdapter;
import org.eclipse.swt.browser.ProgressEvent;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;

public class CarWidget {

	private final File wwwRoot;

	public CarWidget(File wwwRoot) {
		this.wwwRoot = wwwRoot;
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
			new CarWidget(new File(new URL(CarWidget.class.getResource("/").toExternalForm()).getFile(), "../www").getAbsoluteFile()).createControl(shell);
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

	public Control createControl(Composite parent) {

		Browser bw;
		try {
			bw = new Browser (parent, SWT.WEBKIT);
		} catch (SWTError e) {
			bw = new Browser (parent, SWT.NONE);
			bw.setSize(800, 600);
			System.out.println ("Could not instantiate Browser: " + e.getMessage ());
		}

		final BrowserFunction function = new CustomFunction (bw, "theJavaFunction");

		//Define each JavaScript function
		final BrowserFunction function1 = new CustomFunction (bw, "motor");
		final BrowserFunction function2 = new CustomFunction (bw, "abs");
		final BrowserFunction function3 = new CustomFunction (bw, "esp");
		final BrowserFunction function4 = new CustomFunction (bw, "speed");

		final Browser browser = bw;
		browser.addProgressListener (new ProgressAdapter () {
			public void completed (ProgressEvent event) {
				browser.addLocationListener (new LocationAdapter () {
					public void changed (LocationEvent event) {
						browser.removeLocationListener (this);
						System.out.println ("left java function-aware page, so disposed CustomFunction");

						//Dispose JavaScript functions
						function1.dispose ();
						function2.dispose ();
						function3.dispose ();
						function4.dispose ();
					}
				});
			}
		});

		browser.addProgressListener (new ProgressAdapter () {
			double latitude = 52.515;
			double longitude = 13.351;

			public void completed (ProgressEvent event) {

				new Thread(new Runnable() {
					public void run() {
						while (true) {
							try { Thread.sleep(1000);
							Display.getDefault().asyncExec(new Runnable() {
								public void run() {
									browser.execute("updateMarker(" + latitude + ", " + longitude + ")");
									longitude += 0.001;
									longitude = Math.round( (longitude + 0.001) * 1000.0 ) / 1000.0;
									System.out.println(latitude + ", " + longitude);
								}
							});
							} catch (SWTException e) {
								System.out.println("Quit.");
								return;
							} catch (Exception e) { }
						}
					}
				}).start();

				browser.addLocationListener (new LocationAdapter () {
					public void changed (LocationEvent event) {
						browser.removeLocationListener (this);
						System.out.println ("left java function-aware page, so disposed CustomFunction");
						function.dispose ();
					}
				});
			}
		});

		browser.setUrl(new File(wwwRoot, "car.html").toURI().toString());
		return browser;
	}

	static class CustomFunction extends BrowserFunction {
		CustomFunction (Browser browser, String name) {
			super (browser, name);
		}

		/* --------------------- Create the functions: Form JS to Java---------------------------*/
		private Object callMotor(String arg) {
			boolean b= Boolean.parseBoolean(arg);
			System.out.println("Motor: " + b);
			return null;
		}

		private Object callABS(String arg) {
			boolean b= Boolean.parseBoolean(arg);
			System.out.println("ABS: " + b);
			return null;
		}

		private Object callESP(String arg) {
			boolean b= Boolean.parseBoolean(arg);
			System.out.println("ESP: " + b);
			return null;
		}

		private Object callSpeed(String arg) {
			int i=(int) Float.parseFloat(arg);
			System.out.println("Speed: " + i);
			return null;
		}

		public Object function (Object[] args) {

			//Get the name of the function and invoke that function in JAVA
			switch(this.getName()) {

			case "motor": callMotor(args[0].toString());
			break;
			case "abs": callABS(args[0].toString());
			break;
			case "esp": callESP(args[0].toString());
			break;
			case "speed": callSpeed(args[0].toString());
			break;
			default: System.out.println ("??? was called from javascript!");
			break;

			}

			/*
		//Create return object for JavaScript
		Object returnValue = new Object[] {
			new Short ((short)3),
			new Boolean (true),
			null,
			new Object[] {"a string", new Boolean (false)},
			"hi",
			new Float (2.0f / 3.0f),
		};
			 */
			return null;
		}
	}
}
