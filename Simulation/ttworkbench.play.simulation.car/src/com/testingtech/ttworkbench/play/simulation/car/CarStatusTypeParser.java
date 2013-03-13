package com.testingtech.ttworkbench.play.simulation.car;

import com.testingtech.ttworkbench.play.generated.PROTO_API.carStatusType;
import com.testingtech.ttworkbench.play.generated.PROTO_API.carStatusType.Builder;
import com.testingtech.ttworkbench.play.generated.PROTO_API.gpsPosition;
import com.testingtech.ttworkbench.play.generated.PROTO_API.warning;
import com.testingtech.ttworkbench.play.generated.PROTO_API.warning.EnumValue;
import com.testingtech.ttworkbench.play.generated.PROTO_API.warningType;

public class CarStatusTypeParser {
	public static carStatusType parseToStatusType(Car car){
		Builder cst = carStatusType.newBuilder();
	
		cst.setAbsSensor(car.sensors.abs);
		cst.setEngineStatus(car.engine);
		cst.setEspSensor(car.sensors.esp);
		cst.setFogLightSensor(car.sensors.fogLight);
		cst.setFuelFilling((float) car.getTankFill());
		//set gps position
		com.testingtech.ttworkbench.play.generated.PROTO_API.gpsPosition.Builder gps = gpsPosition.newBuilder();
		gps.setLatitude((float) car.currentPosition.latitude);
		gps.setLongitude((float) car.currentPosition.longitude);
		cst.setGpsPos(gps.build());
		//set the rest of the fields
		cst.setLightSensor(car.sensors.light);
		cst.setSpeed((float)car.speed);
		//set warning
		com.testingtech.ttworkbench.play.generated.PROTO_API.warningType.Builder warningBuilder = warningType.newBuilder();
		com.testingtech.ttworkbench.play.generated.PROTO_API.warning.Builder warn = warning.newBuilder();
		warn.build();
		EnumValue ev = EnumValue.valueOf(car.getWarning().toString());
		warn.setEnumValue(ev);
		warningBuilder.setWarningName(warn.build());
		cst.setWarning(warningBuilder.build());
		return cst.build();
	}
}
