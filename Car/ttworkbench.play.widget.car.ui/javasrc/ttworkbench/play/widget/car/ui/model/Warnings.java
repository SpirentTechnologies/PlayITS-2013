package ttworkbench.play.widget.car.ui.model;


public enum Warnings {
	DEER, RAIN, ICE, ACCIDENT, FOG, SNOW;
	
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
