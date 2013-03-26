package ttworkbench.play.widget.car.ui;
/**
 * 
 * @author kensan from controller,  andre
 *
 */
public interface ICarModelListener {
	
	/**
	 * Called from controller,  when Engine is started or stopped
	 */
	public void notifyEngineStatusChange();
	
	/**
	 * Called from controller,  when ABS is enabled or disabled
	 */
	public void notifyABSStatusChange();
	
	/**
	 * Called from controller,  when ESP is enabled or disabled
	 */
	public void notifyESPStatusChange();
	
	/**
	 * Called from controller,  when GPS position are changed
	 */
	public void notifyGpsPositionChange();
	
	/**
	 * Called from controller,  when fuel status is changed
	 */
	public void notifyFillingStatusChange();
	
	/**
	 * Called from controller,  when speed is changed
	 */
	public void notifySpeedChange();
	
	/**
	 * Called from controller,  when warning is added
	 */
	public void notifyWarningAdded();
	
	/**
	 * Called from controller,  when foglight is enabled or disabled
	 */
	public void notifyFogLightChange();
	
	/**
	 * Called from controller,  when light is enabled or disabled
	 */
	public void notifyLightChange();
	

	
}
