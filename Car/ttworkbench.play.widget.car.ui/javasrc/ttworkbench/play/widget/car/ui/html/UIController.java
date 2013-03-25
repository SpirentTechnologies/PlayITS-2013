package ttworkbench.play.widget.car.ui.html;

import org.eclipse.swt.browser.Browser;
import ttworkbench.play.widget.car.ui.model.WarningType;
import ttworkbench.play.widget.car.ui.model.Warnings;

public class UIController {
	
	Browser browser;
	
	public UIController(Browser browser) {
		this.browser = browser;
	}
	
	public void warning(WarningType warning) {
		browser.execute("warning(" + Warnings.getId(warning.getWarning()) + ", " + warning.getGpsPosition().getLatitude() + ", " + warning.getGpsPosition().getLongitude() + ", " + warning.getPriority() + ")");
	}
	
	public void updatePosition(double latitude, double longitude) {
		browser.execute("carPosition(" + latitude + ", " + longitude + ")");
	}
	
	public void updateSpeed(double speed) {
		browser.execute("carSpeed(" +speed+ ")");
	}
	public void updateABS(boolean abs) {
		browser.execute("carABS(" +abs+ ")");
	}
	public void updateESP(boolean esp) {
		browser.execute("carESP(" +esp+ ")");
	}
	public void updateFuel(boolean fuel) {
		browser.execute("carFuel(" +fuel+ ")");
	}
	public void updateLight(boolean light) {
		browser.execute("carLight(" +light+ ")");
	}
	public void updateFogLight(boolean foglight) {
		browser.execute("carFogLight(" +foglight+ ")");
	}
}
