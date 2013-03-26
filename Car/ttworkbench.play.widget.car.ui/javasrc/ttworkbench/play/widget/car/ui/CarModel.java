package ttworkbench.play.widget.car.ui;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

import ttworkbench.play.widget.car.ui.model.CarStatusModel;
import ttworkbench.play.widget.car.ui.model.GPSposition;
import ttworkbench.play.widget.car.ui.model.NotifyAttributes;
import ttworkbench.play.widget.car.ui.model.WarningType;

/**
 * 
 * @author kensan, andre
 *
 */
public class CarModel {
	
	private CarStatusModel status = new CarStatusModel();
	
	private List<WarningType> warnings = new ArrayList<WarningType>();
	
	private LinkedList<ICarModelListener> listeners = new LinkedList<ICarModelListener>();
	
	

	/**
	 * Returns CarStatusModel, consisting attributes.
	 * @return
	 */
	public CarStatusModel getStatus(){
		return status;
	}
	
	/**
	 * Adds warning to warninglist
	 * @param warning
	 */
	public synchronized void addWarning(WarningType warning){
		warnings.add(warning);
	}

	/**
	 * sets more important and short distance Warnings on top of list
	 */
	public void refreshWarningList(){
		//sort List
		Collections.sort(warnings, new Comparator<WarningType>() {

			@Override
			public int compare(WarningType o1, WarningType o2) {

				double o1Distance = calculateDistance(o1.getGpsPosition(), status.getGpsPosition());
				double o2Distance = calculateDistance(o2.getGpsPosition(), status.getGpsPosition());
				if(Math.abs(o2Distance - o1Distance) < 1E-5) {
					return (int) (o2.getPriority() - o1.getPriority());
				}else{
					return (int) Math.signum(o1Distance - o2Distance);
				}
			}
		});
	}

	/**
	 * Returns warning with highest priority
	 * @return
	 */
	public WarningType getWarning(){
		refreshWarningList();
		return warnings.get(0);
	}
	
	/**
	 * Sets GPS latitude and longitude to model.
	 * Delete Warnings with the same coordinates.
	 * @param latitude
	 * @param longitude
	 */
	public synchronized void setGPSPostion(float latitude, float longitude){

		status.setGpsPosition(new GPSposition(latitude, longitude));
	}
	
	/**
	 * Add Listener, that imports ICarModelListener to List.
	 * @param listener
	 */
	public synchronized void addListener(ICarModelListener listener){
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
	 * Notifies every Listener about changes in model.
	 * @param attr Enumeration of NotifyAttributes
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
	
	/**
	 * Calculate the air distance of two GPS Positions 
	 * @param src
	 * @param dest
	 * @return distance
	 */
	public double calculateDistance(GPSposition src, GPSposition dest){
		double r = 6371; // Earthradius in km
		double dLat = Math.toRadians(dest.getLatitude()-src.getLatitude());
		double dLon = Math.toRadians(dest.getLongitude()-src.getLongitude());
		double srcLat = Math.toRadians(src.getLatitude());
		double destLat = Math.toRadians(dest.getLatitude());

		double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
		        Math.sin(dLon/2) * Math.sin(dLon/2) * Math.cos(srcLat) * Math.cos(destLat); 
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)); 
		return r * c;
	}

	/**
	 * clears the warning list
	 */
	public void clearWarnings() {
		warnings.clear();
		
	}
}