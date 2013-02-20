
public class Car implements CarInterface{
	boolean engine,abs,esp,airbagActivated,airbagTriggered,light,foglamp, rainsensor, lightsensor, breaks;
	
	double speed, maxspeed, tirePressure, tank, petrolUsage;
	
	GPSposition pos = new GPSposition(0,0);
	Damage health = Damage.FullHealth;
	Warnings warning[];
	Sensors sensors = new Sensors(true, true, true, true, true, 100, (new Tires(2.5, 2.5, 2.5, 2.5)));
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}

	private void tankFillUpdate(double usage, double speed){
		//calculate 
	}
	
	@Override
	public boolean toggleEngine() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean toggleLight() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public double setSpeed(double speed) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getSpeed() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean toggleESP() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean toggleAirbag() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean toggleWarningSender() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean toggleWarningReceiver() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean toggleLightSensor() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean toggleRainSensor() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public GPSposition getGPSPosition() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GPSposition setGPSPosition(GPSposition position) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public double getTankFill() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public boolean triggerAirbag() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void breaking() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Damage getDamage() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Damage setDamage(Damage d) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub
		/*
		 * check warnings[], Sensors, damage,  
		 * 
		 */
		
	}

	@Override
	public boolean toggleFogLight() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean toggleABS() {
		// TODO Auto-generated method stub
		return false;
	}
}
