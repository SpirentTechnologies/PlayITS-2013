package com.testingtech.ttworkbench.play.simulation.car;

import java.util.List;

import com.testingtech.ttworkbench.play.generated.PROTO_API.carStatusType;
import com.testingtech.ttworkbench.play.generated.PROTO_API.carStatusType.Builder;
import com.testingtech.ttworkbench.play.generated.PROTO_API.gpsPosition;
import com.testingtech.ttworkbench.play.generated.PROTO_API.warning;
import com.testingtech.ttworkbench.play.generated.PROTO_API.warning.EnumValue;
import com.testingtech.ttworkbench.play.generated.PROTO_API.warningType;

public class CarStatusTypeParser {
	public static carStatusType parseToStatusType(Car car) {
		// Build the message with the updated car
		Builder cst = carStatusType.newBuilder();
		cst.setCarId(car.customID);
		cst.setAbsSensor(car.sensors.abs);
		cst.setEngineStatus(car.isEngine());
		cst.setEspSensor(car.sensors.esp);
		cst.setFogLightSensor(car.sensors.fogLight);
		cst.setFuelFilling(new Double(car.getTankFill()).floatValue());
		// set gps position
		com.testingtech.ttworkbench.play.generated.PROTO_API.gpsPosition.Builder gps = gpsPosition
				.newBuilder();
		gps.setLatitude((float) car.getGPSPosition().latitude);
		gps.setLongitude((float) car.getGPSPosition().longitude);
		cst.setGpsPos(gps.build());
		// set the rest of the fields
		cst.setLightSensor(car.sensors.light);
		cst.setSpeed(new Double(car.speed).floatValue());

		// warningTyp
		// -> GPSposition
		// -> warning
		// -> priority
		com.testingtech.ttworkbench.play.generated.PROTO_API.warningType.Builder warningBuilder = warningType
				.newBuilder();
		com.testingtech.ttworkbench.play.generated.PROTO_API.warning.Builder warn = warning
				.newBuilder();
		com.testingtech.ttworkbench.play.generated.PROTO_API.gpsPosition.Builder gpsPos = gpsPosition
				.newBuilder();



		// create Warning
		List<WarningType> allWarnings = car.position.getAllWarnings();
		for (WarningType nextWarning : allWarnings) {
			//if the warning is in a 1km radius add warning to status
			GPSposition gpsPosition = nextWarning.getGpsPosition();
			double distance = GPSpositionOfCar.calculateDistance(car.getGPSPosition(), gpsPosition);
			
			//if a warning is passed, don't add it anymore, but leave it stored
			if(distance > nextWarning.getDistance()){
				nextWarning.setDistance(distance);
				continue;
			}
			
			nextWarning.setDistance(distance);
			
			if (distance < 0.0) {
				System.out.println("dst: "+distance);
				
			} else
			if (distance < 1.0) {

				EnumValue ev = convertWarn(nextWarning.getWarning());
				warn.setEnumValue(ev);
				System.out.println(ev);
				// create GPS-Position
				gpsPos.setLatitude((float) gpsPosition.latitude);
				gpsPos.setLongitude((float) gpsPosition.longitude);
				// set the values
				warningBuilder.setWarningName(warn.build());
				warningBuilder.setCarId(car.customID);
				warningBuilder.setGpsPos(gpsPos.build());
				warningBuilder.setPriority((long) Warnings.getPriority(nextWarning.getWarning()));

				cst.addWarning(warningBuilder.build());
			}
		}
		
		return cst.build();
	}

	private static EnumValue convertWarn(Warnings warning) {
		EnumValue enumValue;
		switch (warning) {
		case ACCIDENT:
			enumValue = EnumValue.accident;
			break;
		case DEER:
			enumValue = EnumValue.deer;
			break;
		case FOG:
			enumValue = EnumValue.fog;
			break;
		case ICE:
			enumValue = EnumValue.ice;
			break;
		case RAIN:
			enumValue = EnumValue.rain;
			break;
		case SNOW:
			enumValue = EnumValue.snow;
			break;
		default:
			throw new IllegalArgumentException("Unknown warning type "+warning);
		}
		return enumValue;
	}
}
