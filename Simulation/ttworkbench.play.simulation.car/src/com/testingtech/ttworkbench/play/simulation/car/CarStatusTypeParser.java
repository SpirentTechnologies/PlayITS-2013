package com.testingtech.ttworkbench.play.simulation.car;

import com.testingtech.ttworkbench.play.generated.PROTO_API.carStatusType;
import com.testingtech.ttworkbench.play.generated.PROTO_API.carStatusType.Builder;
import com.testingtech.ttworkbench.play.generated.PROTO_API.gpsPosition;
import com.testingtech.ttworkbench.play.generated.PROTO_API.warning;
import com.testingtech.ttworkbench.play.generated.PROTO_API.warning.EnumValue;
import com.testingtech.ttworkbench.play.generated.PROTO_API.warningType;

public class CarStatusTypeParser {
	public static carStatusType parseToStatusType(Car car){
		//Build the message with the updated car
		Builder cst = carStatusType.newBuilder();
		cst.setCarId(car.customID);
		cst.setAbsSensor(car.sensors.abs);
		cst.setEngineStatus(car.engine);
		cst.setEspSensor(car.sensors.esp);
		cst.setFogLightSensor(car.sensors.fogLight);
		cst.setFuelFilling((float) car.getTankFill());
		cst.setCarId(car.customID);
		//set gps position
		com.testingtech.ttworkbench.play.generated.PROTO_API.gpsPosition.Builder gps = gpsPosition.newBuilder();
		gps.setLatitude((float) car.currentPosition.latitude);
		gps.setLongitude((float) car.currentPosition.longitude);
		cst.setGpsPos(gps.build());
		//set the rest of the fields
		cst.setLightSensor(car.sensors.light);
		cst.setSpeed((float)car.speed);
		
		//warningTyp
		//	-> GPSposition
		//	-> warning
		//	-> priority
		com.testingtech.ttworkbench.play.generated.PROTO_API.warningType.Builder warningBuilder = warningType.newBuilder();
		com.testingtech.ttworkbench.play.generated.PROTO_API.warning.Builder warn = warning.newBuilder();
		com.testingtech.ttworkbench.play.generated.PROTO_API.gpsPosition.Builder gpsPos = gpsPosition.newBuilder();
		
		//create GPS-Position
		gpsPos.setLatitude((float) car.currentPosition.latitude);
		gpsPos.setLongitude((float) car.currentPosition.longitude);
		
		
		//create Warning
		WarningType nextWarning = car.position.getNextWarning();
		if (nextWarning != null) {
			
			EnumValue ev = EnumValue.valueOf(nextWarning.toString());
			warn.setEnumValue(ev);
			
			//set the values
			warningBuilder.setWarningName(warn.build());
			warningBuilder.setCarId(car.customID);
			warningBuilder.setGpsPos(gpsPos.build());
			warningBuilder.setPriority((long) Warnings.getId(nextWarning.getWarning()));
			
			
			cst.addWarning(warningBuilder.build());
		}
		return cst.build();
	}
}
