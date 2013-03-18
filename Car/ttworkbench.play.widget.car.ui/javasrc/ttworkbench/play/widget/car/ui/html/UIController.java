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
}
