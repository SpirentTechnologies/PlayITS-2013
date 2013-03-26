package ttworkbench.play.widget.car.ui.model;

/**
 * 
 * @author Björn, Andre
 *
 */
public enum EnumWarning {
	DEER, RAIN, ICE, ACCIDENT, FOG, SNOW;
	
	/**
	 * 
	 * @param value 0-5
	 * @return warning of given id
	 */
	public static EnumWarning getWarning(int value){
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
	
	/**
	 * @param warning
	 * @return id of given waring
 	 */
	public static int getId(EnumWarning warning) {
		switch(warning) {
			case DEER:		return 0;
			case RAIN:		return 1;
			case ICE:		return 2;
			case ACCIDENT:	return 3;
			case FOG:		return 4;
			case SNOW:		return 5;
			default: 		return -1;
		}
	}
}
