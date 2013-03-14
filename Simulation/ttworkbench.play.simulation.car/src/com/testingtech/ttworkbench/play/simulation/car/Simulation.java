package com.testingtech.ttworkbench.play.simulation.car;

import java.io.IOException;
import java.util.ArrayList;
import java.util.concurrent.ThreadPoolExecutor;

public class Simulation {
	
	
	private static boolean usingArgs(String[] args){
		if(args.length > 0){
			return true;
		}else{
			return false;
		}
	}
	private static ArrayList<GPSposition> getMap(String location, boolean args){
		if(args){
			return null;
		}else{
			try {
				return KMLparser.parseKML("path/to/file");
			} catch (NumberFormatException e) {
				System.out.println("The file format was wrong");
				e.printStackTrace();
			} catch (IOException e) {
				System.out.println("Some I/O operations went wrong, does the file exist?");
				e.printStackTrace();
			}
		}
		return null;
	}
	
	public static void main(String[] args) {
		String host;
		//parse map file
		ArrayList<GPSposition> map = getMap(args[2], usingArgs(args));
		
		if(usingArgs(args)){
			
		}else{	
			
			Socket testCar1 = new Socket(new Car(100, 200, 2.5, 100, 6, true, true, true, true, true, true, true, true, map));
			testCar1.run();			
		}
	}

}
