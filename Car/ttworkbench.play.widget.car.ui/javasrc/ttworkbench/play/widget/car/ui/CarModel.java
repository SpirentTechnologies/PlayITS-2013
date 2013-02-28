package ttworkbench.play.widget.car.ui;

import java.util.Iterator;
import java.util.LinkedList;

import ttworkbench.play.widget.car.ui.model.CarStatusModel;
import ttworkbench.play.widget.car.ui.model.GPSposition;
import ttworkbench.play.widget.car.ui.model.WarningType;


public class CarModel {
	
	private CarStatusModel status = new CarStatusModel();;
	private LinkedList<WarningType> warnings = new LinkedList<WarningType>();
	private LinkedList<ICarModelListener> listeners = new LinkedList<ICarModelListener>();
	
	public CarModel(){
	}
	
	public CarStatusModel getStatus(){
		return status;
	}
	
	public void addWarning(WarningType warning){
		warnings.add(warning);
	}
	
	public void setGPSPostion(double latitude, double longitude){
		Iterator<WarningType> iterator = warnings.iterator();
		
		while(iterator.hasNext()){
			WarningType warning = iterator.next();
			if(warning.getGpsPosition().equals(new GPSposition(latitude, longitude))){
				warnings.remove(warning);
			}
		}
		
		status.setGpsPosition(new GPSposition(latitude, longitude));
	}
	
	
	public void addListener(ICarModelListener listener){
		this.listeners.add(listener);
	}
	
	public void removeListener(ICarModelListener listener){
		this.listeners.remove(listener);
	}
	
	public void notifyListener(){
		for(ICarModelListener listener: listeners){
			listener.notifyModelChange();
		}
	}
	
}
