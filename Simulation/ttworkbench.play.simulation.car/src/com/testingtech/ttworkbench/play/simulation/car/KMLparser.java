package com.testingtech.ttworkbench.play.simulation.car;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class KMLparser {

	public static ArrayList<GPSposition> positions = new ArrayList<>();

	public static void main(String[] args) {

		File file = new File("Z:\\route.txt");
		StringBuilder contents = new StringBuilder();
		BufferedReader reader = null;

		try {
			reader = new BufferedReader(new FileReader(file));
			String text = null;

			// repeat until all lines is read
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

		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		//GPSpositionOfCar porsche = new GPSpositionOfCar(new GPSposition(10.1, 1),new GPSposition(10, 10),positions);
		GPSposition current = positions.get(0);
		GPSposition destination = positions.get(1);
		GPSpositionOfCar porsche = new GPSpositionOfCar(current,destination,positions);
		double tankFill = 100;

		int i = 2;
		
		while (true) {
			current = porsche.updateEverything(100, 12, 200).first;
			System.out.println(current.longitude + "\t\t" + current.latitude + "\t\t" + porsche.getAngle());
			
			/*
			 * current - destination => check what direction to drive
			 * save direction and handle with switch case
			 * 
			 * */
			
			
			/*if(current.longitude - destination.longitude < 0){
				if(current.latitude - destination.latitude < 0){
					current = destination;
					destination = positions.get(i);
					i++;
				}
			}*/
			
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				System.out.println("not sleeping");
			}
		}
	}
}
