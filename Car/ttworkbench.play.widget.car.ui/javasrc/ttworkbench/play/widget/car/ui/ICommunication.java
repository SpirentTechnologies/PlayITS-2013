package ttworkbench.play.widget.car.ui;

public interface ICommunication {
	
	ActionsClient getActionsClient();
	
	CarModel getCarModel();

	public void disableActions();
	
	public void enableActions();
}
