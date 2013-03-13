package ttworkbench.play.widget.car.ui.html;

import org.eclipse.swt.browser.Browser;

public class UIController implements Runnable {
	
	Browser browser;
	
	public UIController(Browser browser) {
		this.browser = browser;
	}
	
	public void carSpeed(int speed) {
		System.out.println("Speed: " + speed);
	}
	
	public void simpleSwitch(boolean isOn) {
		if(isOn) {
			System.out.println("SWITCH is on");
		} else {
			System.out.println("SWITCH is off");
		}
	}
	
	public void flipSwitch() {
		browser.execute("flipSwitch()");
	}
	
	public void getPosition(double lat, double lng) {
		System.out.println("Position: " + lat + ", " + lng);
	}
	
	public void run() {}
}
