package ttworkbench.play.widget.car.ui.model;

/**
 * 
 * @author kensan
 *
 */
public enum Warnings {
	DEER, RAIN, ICE, ACCIDENT, FOG, SNOW;
	
	/**
	 * 
	 * @param value
	 * @return Warnings
	 */
	public static Warnings getWarning(int value){
		switch(value){
		case 0: return DEER;
		case 1: return RAIN;
		case 2: return ICE;
		case 3: return ACCIDENT;
		case 4: return FOG;
		case 5: return SNOW;
		default: return null;
		}
		
	}
}
