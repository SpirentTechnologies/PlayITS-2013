package ttworkbench.play.widget.car.ui;

import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.wb.swt.SWTResourceManager;


public class InitializeFrame extends Composite{
	private Text fuel;
	private Text maxspeed;
	Button btnAbsSensor,btnInitialize,btnEspSensor,btnFoglightSensor,btnLightSensor;
	WidgetController controller;
	private Label lblFuelConsumption;
	private Text consumption;
	private Button btnTrackfile;
	private File trackFile;
	private Label lblFileName;
	private InitializeFrame me;
	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 * @param comm 
	 */
	public InitializeFrame(Composite parent, int style, ICommunication comm) {
		super(parent, style);
		setBackground(SWTResourceManager.getColor(SWT.COLOR_WIDGET_LIGHT_SHADOW));
		me = this;
		controller = comm.getWidgetController();
		setLayout(new GridLayout(3, false));
		 
		 Label lblInitializeCar = new Label(this, SWT.SHADOW_IN | SWT.CENTER);
		 lblInitializeCar.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 3, 1));
		 lblInitializeCar.setFont(SWTResourceManager.getFont("Segoe UI", 14, SWT.NORMAL));
		 lblInitializeCar.setText("Initialize a Car");
		 
		 btnAbsSensor = new Button(this, SWT.CHECK | SWT.CENTER);
		 btnAbsSensor.setText("ABS Sensor");
		 
		 Label lblFuel = new Label(this, SWT.CENTER);
		 lblFuel.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		 lblFuel.setAlignment(SWT.CENTER);
		 lblFuel.setText("fuel \r\n[l]");
		 
		 fuel = new Text(this, SWT.BORDER | SWT.CENTER);
		 GridData gd_fuel = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		 gd_fuel.widthHint = 41;
		 fuel.setLayoutData(gd_fuel);
		 fuel.setText("50");
		 
		 btnEspSensor = new Button(this, SWT.CHECK | SWT.CENTER);
		 btnEspSensor.setText("ESP Sensor");
		 
		 Label lblSpeed = new Label(this, SWT.CENTER);
		 lblSpeed.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		 lblSpeed.setText("max. speed \r\n[km/h]");
		 lblSpeed.setAlignment(SWT.CENTER);
		 
		 maxspeed = new Text(this, SWT.BORDER | SWT.CENTER);
		 GridData gd_maxspeed = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		 gd_maxspeed.widthHint = 42;
		 maxspeed.setLayoutData(gd_maxspeed);
		 maxspeed.setText("120");
		 
		 btnFoglightSensor = new Button(this, SWT.CHECK | SWT.CENTER);
		 btnFoglightSensor.setText("Foglight Sensor");
		 
		 lblFuelConsumption = new Label(this, SWT.CENTER);
		 lblFuelConsumption.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		 lblFuelConsumption.setText("consumption\r\n[l/100km]");
		 
		 consumption = new Text(this, SWT.BORDER | SWT.CENTER);
		 GridData gd_consumption = new GridData(SWT.LEFT, SWT.CENTER, false, false, 1, 1);
		 gd_consumption.widthHint = 42;
		 consumption.setLayoutData(gd_consumption);
		 consumption.setText("6.3");
		 consumption.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		
		 btnLightSensor = new Button(this, SWT.CHECK | SWT.CENTER);
		 btnLightSensor.setText("Light Sensor");
		
		btnTrackfile = new Button(this, SWT.NONE);
		btnTrackfile.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		btnTrackfile.setText("Trackfile");
		
		btnTrackfile.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				JFileChooser chooser = new JFileChooser();
				chooser.setDialogType(JFileChooser.OPEN_DIALOG);
		        chooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		        final File file = new File("/home");

		        chooser.setCurrentDirectory(file);

		        chooser.setVisible(true);
		        final int result = chooser.showOpenDialog(null);

		        if (result == JFileChooser.APPROVE_OPTION) {
		            trackFile = chooser.getSelectedFile();
		            lblFileName.setText(trackFile.getName());

		        }else{
		        	System.out.println("Abbruch");
		        }
		        chooser.setVisible(false); 
			}
		});
		
		lblFileName = new Label(this, SWT.NONE);
		lblFileName.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		lblFileName.setAlignment(SWT.CENTER);
		lblFileName.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
		lblFileName.setText("none");
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		trackFile = new File("E:\\Users\\kensan.Phenom-PC\\git\\PlayITS\\Car\\ttworkbench.play.widget.car.ui\\maps\\RoutenachArnimallee.txt");
		lblFileName.setText(trackFile.getName());
		Button btnInitialize_1 = new Button(this, SWT.NONE);
		btnInitialize_1.setText("initialize");
		
				btnInitialize_1.addSelectionListener(new SelectionAdapter() {
					@Override
					public void widgetSelected(SelectionEvent e) {
						float fmaxSpeed = 0, ffuel = 0, fuelConsumption = 0;
						try{
							fmaxSpeed = Float.parseFloat(maxspeed.getText());
							ffuel = Float.parseFloat(fuel.getText());
							fuelConsumption = Float.parseFloat(consumption.getText());
						}catch(NumberFormatException e1){
							JOptionPane.showConfirmDialog(null, "Fill textfields with numbers, please!", "Input Error", JOptionPane.OK_OPTION);
							return;
						}
						
						if(!trackFile.exists()){
							JOptionPane.showConfirmDialog(null, "Choose a trackfile, please!","Input Error", JOptionPane.OK_OPTION);
							return;
						}
						controller.initializeCar(btnAbsSensor.getSelection(), btnEspSensor.getSelection(), 
						btnLightSensor.getSelection(), btnFoglightSensor.getSelection(), fmaxSpeed, ffuel, fuelConsumption, trackFile);
						me.setVisible(false);
						me.dispose();
					}
				});
		new Label(this, SWT.NONE);
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
