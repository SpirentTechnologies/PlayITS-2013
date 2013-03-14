package com.testingtech.ttworkbench.play.simulation.car;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.text.MessageFormat;
import java.util.ArrayList;

public class Simulation {
	
	private static ArrayList<GPSposition> getMap(File mapFile){
		try {
			return KMLparser.parseKML(mapFile);
		} catch (NumberFormatException e) {
			System.out.println(MessageFormat.format("The file format was wrong", mapFile.getAbsolutePath()));
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			System.out.println(MessageFormat.format("Map file does the file exist: ", mapFile.getAbsolutePath()));
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("Some I/O operations went wrong");
			e.printStackTrace();
		}
		return null;
	}
	
	public static void main(String[] args) {
		//parse map file
		
		if (args.length == 4) {
			int index = 0;

			int clientPort = parsePort(args, index++);
			String clientHost = args[index++];
			int serverPort = parsePort(args, index++);
			
			File mapFile = new File(args[index++]);
			ArrayList<GPSposition> map = getMap(mapFile);
			
			Socket testCar1 = new Socket(new Car(100, 200, 2.5, 100, 6, true, true, true, true, true, true, true, true, map));
			testCar1.run(clientPort, clientHost, serverPort);
		} else {
			System.out.println("Usage: java -jar Simulation.jar <clientPort> <clientHost> <serverPort> <kmlFile>");
		}
	}
	private static int parsePort(String[] args, int argIndex) throws IllegalArgumentException {
		if (argIndex >= args.length) {
			throw new IllegalArgumentException("Missing port argument at index "+argIndex);
		}
		try {
			int portNumber = Integer.parseInt(args[argIndex]);
			return portNumber;
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException("Invalid port "+args[argIndex]);
		}
	}

}
