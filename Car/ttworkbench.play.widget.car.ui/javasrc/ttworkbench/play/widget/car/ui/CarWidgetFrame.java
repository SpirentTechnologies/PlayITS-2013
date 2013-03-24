package ttworkbench.play.widget.car.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Slider;

import ttworkbench.play.widget.car.ui.model.CarStatusModel;

public class CarWidgetFrame extends Composite implements IUIController{

	private WidgetController controller;
	private Button btnEngine;
	private Slider sliderSpeed;
	private CarStatusModel status;
	private Label lblOnOff;
	private Color red = new Color(Display.getCurrent(),255,0,0);
	private Color green = new Color(Display.getCurrent(),0,255,0);
	private Browser browser;
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public CarWidgetFrame(Composite parent, int style, ICommunication comm) {
		super(parent, style);
		setLayout(new GridLayout(11, false));
		status = comm.getCarModel().getStatus();
		controller = comm.getWidgetController();
		Label lblAbs = new Label(this, SWT.CENTER);
		lblAbs.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		lblAbs.setText("ABS");
		if(status.isABSenabled()){
			lblAbs.setBackground(green);
		}else{
			lblAbs.setBackground(red);
		}
		
		Label lblFoglight = new Label(this, SWT.CENTER);
		lblFoglight.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		lblFoglight.setText("FogLight");
		if(status.isFogLightSensorEnabled()){
			lblFoglight.setBackground(green);
		}else{
			lblFoglight.setBackground(red);
		}
		
		browser = new Browser(this, SWT.NONE);
		browser.setUrl("E:\\Users\\kensan.Phenom-PC\\git\\PlayITS\\Car\\ttworkbench.play.widget.car.ui\\www\\car.html");
		GridData gd_browser = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 6);
		gd_browser.heightHint = 372;
		gd_browser.widthHint = 362;
		browser.setLayoutData(gd_browser);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		Label lblEsp = new Label(this, SWT.CENTER);
		GridData gd_lblEsp = new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1);
		gd_lblEsp.widthHint = 61;
		lblEsp.setLayoutData(gd_lblEsp);
		lblEsp.setText("ESP");
		if(status.isESPenabled()){
			lblEsp.setBackground(green);
		}else{
			lblEsp.setBackground(red);

		}
		
		Label lblLight = new Label(this, SWT.CENTER);
		GridData gd_lblLight = new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1);
		gd_lblLight.widthHint = 60;
		lblLight.setLayoutData(gd_lblLight);
		lblLight.setText("Light");
		
		if(status.isLightSensorEnabled()){
			lblLight.setBackground(green);
		}else{
			lblLight.setBackground(red);
		}
		
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		btnEngine = new Button(this, SWT.NONE);
		btnEngine.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				if(status.isEngineStarted()){
					controller.stopEngine();
				}else{
					controller.startEngine();
				}
			}
		});
		btnEngine.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		btnEngine.setText("Engine");
		
		lblOnOff = new Label(this, SWT.NONE);
		lblOnOff.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		lblOnOff.setText("Off");
		lblOnOff.setBackground(red);
		
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		Label lblNewLabel = new Label(this, SWT.CENTER);
		GridData gd_lblNewLabel = new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1);
		gd_lblNewLabel.widthHint = 65;
		lblNewLabel.setLayoutData(gd_lblNewLabel);
		lblNewLabel.setText("Speed");
		
		Label lblSpeed = new Label(this, SWT.NONE);
		lblSpeed.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		lblSpeed.setText("0");
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		sliderSpeed = new Slider(this, SWT.NONE);
		GridData gd_sliderSpeed = new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1);
		gd_sliderSpeed.widthHint = 106;
		sliderSpeed.setLayoutData(gd_sliderSpeed);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	@Override
	public void changeEngineView(boolean isEnabled) {
		if(isEnabled){
			lblOnOff.setText("On");
			lblOnOff.setBackground(green);
		}else{
			lblOnOff.setText("Off");
			lblOnOff.setBackground(red);
		}
	}

	@Override
	public void changeMapLocation(double latitude, double longitude) {
		browser.execute("carPosition(" + latitude + ", " + longitude + ")");
		
	}
}