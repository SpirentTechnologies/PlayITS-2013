package ttworkbench.play.widget.car.ui;

import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Slider;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.browser.Browser;

public class FrameCarWidget extends Composite {

	/**
	 * Create the composite.
	 * @param parent
	 * @param style
	 */
	public FrameCarWidget(Composite parent, int style) {
		super(parent, style);
		setLayout(new GridLayout(11, false));
		
		Label lblAbs = new Label(this, SWT.CENTER);
		lblAbs.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		lblAbs.setText("ABS");
		
		Label lblFoglight = new Label(this, SWT.CENTER);
		lblFoglight.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		lblFoglight.setText("FogLight");
		
		Browser browser = new Browser(this, SWT.NONE);
		browser.setUrl("http://maps.google.de/maps?http://maps.google.de/maps?f=q&amp;source=s_q&amp;hl=de&amp;geocode=&amp;q=berlin&amp;aq=&amp;sll=52.506844,13.424732&amp;sspn=0.707981,2.113495&amp;ie=UTF8&amp;hq=&amp;hnear=Berlin&amp;t=m&amp;z=14&amp;iwloc=A&amp;output=embed");
		GridData gd_browser = new GridData(SWT.FILL, SWT.CENTER, false, false, 2, 9);
		gd_browser.heightHint = 185;
		gd_browser.widthHint = 307;
		browser.setLayoutData(gd_browser);
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
		
		Label lblLight = new Label(this, SWT.CENTER);
		GridData gd_lblLight = new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1);
		gd_lblLight.widthHint = 60;
		lblLight.setLayoutData(gd_lblLight);
		lblLight.setText("Light");
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		Button btnEngine = new Button(this, SWT.NONE);
		btnEngine.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		btnEngine.setText("Engine");
		
		Label lblOff = new Label(this, SWT.NONE);
		lblOff.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		lblOff.setText("Off");
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
		
		Label lblNewLabel_1 = new Label(this, SWT.NONE);
		lblNewLabel_1.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false, false, 1, 1));
		lblNewLabel_1.setText("0");
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		
		Slider slider = new Slider(this, SWT.NONE);
		GridData gd_slider = new GridData(SWT.LEFT, SWT.CENTER, false, false, 2, 1);
		gd_slider.widthHint = 106;
		slider.setLayoutData(gd_slider);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);
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
}
