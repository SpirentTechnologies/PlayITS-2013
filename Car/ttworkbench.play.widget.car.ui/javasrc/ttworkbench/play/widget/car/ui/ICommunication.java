package ttworkbench.play.widget.car.ui;

/**
 * 
 * @author Björn
 *
 */
public interface ICommunication {
	
	/**
	 * @return ActionsClient from super class
	 */
	ActionsClient getActionsClient();
	
	/**
	 * 
	 * @return current instance of CarModel
	 */
	CarModel getCarModel();

	/**
	 * disable actionfields in View
	 */
	void disableActions();
	
	/**
	 * enable actionfields in View
	 */
	void enableActions();
}
