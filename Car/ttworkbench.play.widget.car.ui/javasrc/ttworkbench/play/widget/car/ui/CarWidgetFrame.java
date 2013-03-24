package ttworkbench.play.widget.car.ui;

import org.eclipse.swt.SWT;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.ProgressBar;
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
	private Label lblSpeed;
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public CarWidgetFrame(Composite parent, int style, ICommunication comm) {
		super(parent, style);
		setLayout(new GridLayout(4, false));
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
		GridData gd_browser = new GridData(SWT.CENTER, SWT.CENTER, false, false, 2, 11);
		gd_browser.heightHint = 230;
		gd_browser.widthHint = 284;
		browser.setLayoutData(gd_browser);
		
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
		lblOnOff.setAlignment(SWT.CENTER);
		lblOnOff.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		lblOnOff.setText("Off");
		lblOnOff.setBackground(red);
		
		Label lblNewLabel = new Label(this, SWT.CENTER);
		GridData gd_lblNewLabel = new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1);
		gd_lblNewLabel.widthHint = 65;
		lblNewLabel.setLayoutData(gd_lblNewLabel);
		lblNewLabel.setText("Speed");
		
		lblSpeed = new Label(this, SWT.NONE);
		lblSpeed.setAlignment(SWT.CENTER);
		lblSpeed.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		lblSpeed.setText("0");
		
		sliderSpeed = new Slider(this, SWT.NONE);
		sliderSpeed.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseUp(MouseEvent e) {
				float speed = sliderSpeed.getSelection() * status.getMaxSpeed()/100;
				lblSpeed.setText(""+ speed);
				lblSpeed.setBackground(red);
				controller.changeSpeed(speed);
			}
		});

		GridData gd_sliderSpeed = new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 1);
		gd_sliderSpeed.widthHint = 106;
		sliderSpeed.setLayoutData(gd_sliderSpeed);
		
		Label lblFuellevel = new Label(this, SWT.NONE);
		lblFuellevel.setText("fuellevel");
		
		ProgressBar progressBarfuel = new ProgressBar(this, SWT.NONE);
		GridData gd_progressBarfuel = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		gd_progressBarfuel.widthHint = 74;
		progressBarfuel.setLayoutData(gd_progressBarfuel);
		
		Label lblConsumption = new Label(this, SWT.NONE);
		lblConsumption.setText("consumption");
		
		Label consumptionMeter = new Label(this, SWT.NONE);
		consumptionMeter.setAlignment(SWT.CENTER);
		consumptionMeter.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		consumptionMeter.setText("0");
		
		Button btnIce = new Button(this, SWT.NONE);
		btnIce.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		btnIce.setText("Ice");
		
		Button btnNewButton_1 = new Button(this, SWT.NONE);
		btnNewButton_1.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		btnNewButton_1.setText("DEER");
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);

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
