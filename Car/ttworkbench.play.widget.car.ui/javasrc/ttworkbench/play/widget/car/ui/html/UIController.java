package ttworkbench.play.widget.car.ui.html;

import org.eclipse.swt.browser.Browser;

import ttworkbench.play.widget.car.ui.model.WarningType;
import ttworkbench.play.widget.car.ui.model.enumWarning;

public class UIController {
	
	Browser browser;
	
	public UIController(Browser browser) {
		this.browser = browser;
	}
	
	public void warning(WarningType warning) {
		browser.execute("warning(" + enumWarning.getId(warning.getWarning()) +")");
		int id = enumWarning.getId(warning.getWarning());
		if (id < 0) {
			// backup show only this
			id = enumWarning.getId(enumWarning.ACCIDENT);
		}
		browser.execute("warning(" + id + ")");
//				+ ", " + warning.getGpsPosition().getLatitude() + ", " + warning.getGpsPosition().getLongitude() + ", " + warning.getPriority() + ")");
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
	public void updateFuel(double fuel) {
		browser.execute("carFuel(" +fuel+ ")");
	}
	public void updateLight(boolean light) {
		browser.execute("carLight(" +light+ ")");
	}
	public void updateFogLight(boolean foglight) {
		browser.execute("carFogLight(" +foglight+ ")");
	}
	public void updateEngine(boolean engine){
        browser.execute("carEngine(" + engine + ")");
    }
}
