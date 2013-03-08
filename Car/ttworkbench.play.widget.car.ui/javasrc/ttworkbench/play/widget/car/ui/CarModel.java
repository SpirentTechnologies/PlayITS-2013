package ttworkbench.play.widget.car.ui;

import java.util.Iterator;
import java.util.LinkedList;

import ttworkbench.play.widget.car.ui.model.CarStatusModel;
import ttworkbench.play.widget.car.ui.model.GPSposition;
import ttworkbench.play.widget.car.ui.model.NotifyAttributes;
import ttworkbench.play.widget.car.ui.model.WarningType;

/**
 * 
 * @author kensan
 *
 */
public class CarModel {
	
	private CarStatusModel status = new CarStatusModel();;
	private LinkedList<WarningType> warnings = new LinkedList<WarningType>();
	private LinkedList<ICarModelListener> listeners = new LinkedList<ICarModelListener>();
	

	/**
	 * Returns CarStatusModel, consisting attributes.
	 * @return
	 */
	public CarStatusModel getStatus(){
		return status;
	}
	
	/**
	 * Adds Warning to Warninglist
	 * @param warning
	 */
	public void addWarning(WarningType warning){
		warnings.add(warning);
	}
	
	/**
	 * Sets GPS latitude and longitude to model.
	 * Delete Warnings with the same Coords.
	 * @param latitude
	 * @param longitude
	 */
	public void setGPSPostion(float latitude, float longitude){
		Iterator<WarningType> iterator = warnings.iterator();
		
		while(iterator.hasNext()){
			WarningType warning = iterator.next();
			if(warning.getGpsPosition().equals(new GPSposition(latitude, longitude))){
				warnings.remove(warning);
			}
		}
		
		status.setGpsPosition(new GPSposition(latitude, longitude));
	}
	
	/**
	 * Add Listener, that import ICarModelListener to List.
	 * @param listener
	 */
	public void addListener(ICarModelListener listener){
		this.listeners.add(listener);
	}
	
	/**
	 * Remove Listener from List.
	 * @param listener
	 */
	public void removeListener(ICarModelListener listener){
		this.listeners.remove(listener);
	}
	
	/**
	 * Notfies every Listener in List.
	 */
	public void notifyListener(NotifyAttributes attr){
		for(ICarModelListener listener: listeners){
			switch(attr){
			case GPS: listener.notifyGpsPositionChange(); break;
			case ESP: listener.notifyESPStatusChange(); break;
			case ABS: listener.notifyABSStatusChange(); break;
			case SPEED: listener.notifySpeedChange(); break;
			case ENGINE: listener.notifyEngineStatusChange(); break;
			case WARNING: listener.notifyWarningAdded(); break;
			case FOG: listener.notifyFogLightChange(); break;
			case LIGHT: listener.notifyLightChange(); break;
			case FUEL: listener.notifyFillingStatusChange(); break;			
			default:;
			}
			
		}
	}
	
}
