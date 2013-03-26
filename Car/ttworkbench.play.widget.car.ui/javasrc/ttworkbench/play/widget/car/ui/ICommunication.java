package ttworkbench.play.widget.car.ui;

public interface ICommunication {
	
	ActionsClient getActionsClient();
	
	CarModel getCarModel();

	void disableActions();
	
	void enableActions();
}
