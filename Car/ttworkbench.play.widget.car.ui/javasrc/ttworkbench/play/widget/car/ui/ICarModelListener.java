package ttworkbench.play.widget.car.ui;
/**
 * 
 * @author kensan
 *
 */
public interface ICarModelListener {
	
	/**
	 * Called, when Engine is started or stoped
	 */
	public void notifyEngineStatusChange();
	
	/**
	 * Called, when ABS is enabled or disabled
	 */
	public void notifyABSStatusChange();
	
	/**
	 * Called, when ESP is enabled or disabled
	 */
	public void notifyESPStatusChange();
	
	/**
	 * Called, when GPS position are changed
	 */
	public void notifyGpsPositionChange();
	
	/**
	 * Called, when fuel status is changed
	 */
	public void notifyFillingStatusChange();
	
	/**
	 * Called, when speed is changed
	 */
	public void notifySpeedChange();
	
	/**
	 * Called, when warning is added
	 */
	public void notifyWarningAdded();
	
	/**
	 * Called, when foglight is enabled or disabled
	 */
	public void notifyFogLightChange();
	
	/**
	 * Called, when light is enabled or disabled
	 */
	public void notifyLightChange();
}
