package ttworkbench.play.widget.car.ui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
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
		setLayout(null);
		
		Button btnInitialize = new Button(this, SWT.NONE);
		btnInitialize.setBounds(176, 254, 75, 25);
		btnInitialize.setText("initialize");
		
		fuel = new Text(this, SWT.BORDER | SWT.CENTER);
		fuel.setBounds(280, 61, 76, 21);
		
		Label lblFuel = new Label(this, SWT.CENTER);
		lblFuel.setBounds(190, 67, 75, 15);
		lblFuel.setAlignment(SWT.CENTER);
		lblFuel.setText("fuel");
		
		Label lblSpeed = new Label(this, SWT.CENTER);
		lblSpeed.setBounds(190, 104, 81, 15);
		lblSpeed.setText("max. speed");
		lblSpeed.setAlignment(SWT.CENTER);
		
		maxspeed = new Text(this, SWT.BORDER | SWT.CENTER);
		maxspeed.setBounds(280, 104, 76, 21);
		
		btnAbsSensor = new Button(this, SWT.CHECK | SWT.CENTER);
		btnAbsSensor.setBounds(70, 67, 93, 16);
		btnAbsSensor.setText("ABS Sensor");
		
		btnEspSensor = new Button(this, SWT.CHECK | SWT.CENTER);
		btnEspSensor.setBounds(70, 109, 93, 16);
		btnEspSensor.setText("ESP Sensor");
		
		btnFoglightSensor = new Button(this, SWT.CHECK | SWT.CENTER);
		btnFoglightSensor.setBounds(70, 155, 103, 16);
		btnFoglightSensor.setText("Foglight Sensor");
		
		 btnLightSensor = new Button(this, SWT.CHECK | SWT.CENTER);
		 btnLightSensor.setBounds(70, 200, 93, 16);
		btnLightSensor.setText("Light Sensor");
		
		Label lblInitializeCar = new Label(this, SWT.SHADOW_IN | SWT.CENTER);
		lblInitializeCar.setBounds(73, 10, 283, 33);
		lblInitializeCar.setFont(SWTResourceManager.getFont("Segoe UI", 14, SWT.NORMAL));
		lblInitializeCar.setText("Initialize Car");
		
		lblFuelConsumption = new Label(this, SWT.CENTER);
		lblFuelConsumption.setBounds(190, 138, 75, 33);
		lblFuelConsumption.setText("fuel \r\nconsumption");
		
		consumption = new Text(this, SWT.BORDER | SWT.CENTER);
		consumption.setBounds(280, 150, 76, 21);
		consumption.setBackground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		
		btnTrackfile = new Button(this, SWT.NONE);
		btnTrackfile.setBounds(190, 200, 75, 25);
		btnTrackfile.setText("Trackfile");
		
		lblFileName = new Label(this, SWT.NONE);
		lblFileName.setBounds(280, 201, 139, 24);
		lblFileName.setAlignment(SWT.CENTER);
		lblFileName.setFont(SWTResourceManager.getFont("Segoe UI", 9, SWT.NORMAL));
		lblFileName.setText("none");

		btnInitialize.addSelectionListener(new SelectionAdapter() {
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
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

}
