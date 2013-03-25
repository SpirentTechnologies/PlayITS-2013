package ttworkbench.play.widget.car.ui;

public interface IUIController {

	void changeEngineView(boolean isEnabled);
	
	void changeMapLocation(double latitude, double longitude);
	
	void changeFuelLevel(double fuel);
	
	void changeABS(boolean isEnabled);
	
	void changeESP(boolean isEnabled);
	
	void changeLight(boolean isEnabled);
	
	void changeFogLight(boolean isEnabled);
	
	void changeSpeed(double speed);
	
	
}
