
public class Car implements CarInterface {

    boolean engine, abs, esp, light, foglamp, breaks;
    double speed, maxspeed, tirePressure, tankFill, petrolUsage;
    GPSposition worldStartPosition = new GPSposition(0, 0);
    GPSposition worldNextStopPosition = new GPSposition(10, 10);
    GPSposition newPosition = worldStartPosition;
    GPSposition oldPosition = newPosition;
    private Warnings warning[];
    Sensors sensors = new Sensors(true, true, true, 100, (new Tires(2.5, 2.5, 2.5, 2.5)));

    public Car(boolean engine, boolean esp, boolean light, boolean foglamp, boolean breaks, double speed, double maxspeed, double tirePressure, double tankFill, double petrolUsage) {
        this.engine = engine;
        this.abs = true;
        this.esp = esp;
        this.light = light;
        this.foglamp = foglamp;
        this.breaks = breaks;
        this.speed = speed;
        this.maxspeed = maxspeed;
        this.tirePressure = tirePressure;
        this.tankFill = tankFill;
        this.petrolUsage = petrolUsage;
    }

    /**
     * @param args
     */
    public static void main(String[] args) {
        // TODO main
    }

    private double toRadiants(double a) {
        return a * Math.PI / 180;
    }
    //get distance in km
    private double distance(GPSposition newPosition, GPSposition oldPosition) {
        double R = 6371.0;
        double dLat = toRadiants(newPosition.latitude - oldPosition.latitude);
        double dLon = toRadiants(newPosition.longitude - oldPosition.longitude);
        double lat1 = toRadiants(oldPosition.latitude);
        double lat2 = toRadiants(newPosition.latitude);
        double tmp = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.sin(dLon / 2) * Math.sin(dLon / 2) * Math.cos(lat1) * Math.cos(lat2);

        double tmp2 = 2 * Math.atan2(Math.sqrt(tmp), Math.sqrt(1 - tmp));
        return tmp2 * R;
    }

    private void tankFillUpdate() {
        //calculate the distance driven since last update
        double distance = distance(newPosition, oldPosition);
        //calculate actual distance
        double actualUsage = petrolUsage * distance / 100;
        //update tankfill
        tankFill -= actualUsage;

        oldPosition = newPosition;
    }

    @Override
    public boolean toggleEngine() {
        if (!engine) {
            engine = true;
            sensors.toggleOn();
            return true;
        } else {
            engine = engine = abs = esp = light = foglamp = breaks = false;
            sensors.toggleOff();
            return false;
        }
    }

    @Override
    public boolean toggleFogLight() {
        foglamp = !foglamp;
        return foglamp;
    }

    @Override
    public boolean toggleLight() {
        if (light) {
            foglamp = false;
        }
        light = !light;
        return light;
    }

    @Override
    public double setSpeed(double speed) {
        this.speed = speed;
        return this.speed;
    }

    @Override
    public double getSpeed() {
        return this.speed;
    }

    @Override
    public boolean toggleESP() {
        esp = !esp;
        return esp;
    }

    @Override
    public boolean toggleWarningSender() {
        // TODO toggleWaningSender
        return false;
    }

    @Override
    public boolean toggleWarningReceiver() {
        // TODO toggleWarningReceiver
        return false;
    }

    @Override
    public boolean toggleLightSensor() {
        sensors.light = !sensors.light;
        return sensors.light;
    }

    @Override
    public boolean toggleRainSensor() {
        sensors.rain = !sensors.rain;
        return sensors.rain;
    }

    @Override
    public GPSposition getGPSPosition() {
        return newPosition;
    }

    @Override
    public GPSposition setGPSPosition(GPSposition position) {
        this.newPosition = position;
        return newPosition;
    }

    @Override
    public double getTankFill() {
        return tankFill;
    }

    @Override
    public double breaking() {
        double result = speed * speed / 200;
        return result;
    }

    @Override
    public void update() {
        // TODO update
        // check warnings[], Sensors, damage,

        // TODO change true to a variable so it can be closed reasonable
        while (true) {
            /*
             * TODO calculate distance between the two KML gps coordinates let
             * car drive that distanace
             */
            double distanceWorldCoordinates = distance(worldStartPosition, worldNextStopPosition);
            //transform km/h -> km/s
            double realDistance = speed * 0.000277777778;
            
            
        }
    }
}
