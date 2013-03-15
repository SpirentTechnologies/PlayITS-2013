package com.testingtech.ttworkbench.play.simulation.car;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class KMLparser {

	
	public static ArrayList<GPSposition> parseKML(File file) throws NumberFormatException, IOException {
		ArrayList<GPSposition> positions = new ArrayList<GPSposition>();

		StringBuilder contents = new StringBuilder();
		BufferedReader reader = null;

			reader = new BufferedReader(new FileReader(file));
			String text = null;

			// repeat until all lines are read
			while ((text = reader.readLine()) != null) {
				contents.append(text).append(
						System.getProperty("line.separator"));

				String[] splitted = text.replace("\t ", "").replace(" ", "")
						.split(",");
				GPSposition gps = new GPSposition(
						Double.parseDouble(splitted[0]),
						Double.parseDouble(splitted[1]));
				positions.add(gps);
			}


			return positions;
	}
}
