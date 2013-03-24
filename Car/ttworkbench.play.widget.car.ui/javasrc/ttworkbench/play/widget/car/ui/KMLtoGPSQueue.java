package ttworkbench.play.widget.car.ui;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Queue;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathExpressionException;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.xml.sax.SAXException;

import ttworkbench.play.widget.car.ui.model.GPSposition;

public class KMLtoGPSQueue {

  private static Queue<GPSposition> positions;

/**
 * @return Queue of gps positions
 */
public static Queue<GPSposition> getPositions() {
	return positions;
}

/**
 * Creates a Queue consisting every GPSposition of a track
 */
public KMLtoGPSQueue(File input){
	try{
	//need to change xmlns to ns, so read file and write a temporary file
	BufferedReader reader = new BufferedReader(new FileReader(input));
	File output = new File(input.getAbsolutePath()+".tmp");
	BufferedWriter writer = new BufferedWriter(new FileWriter(output));
	String line;
	while((line = reader.readLine()) != null){
		line = line.replace("xmlns", "ns");
		line = line.trim();
		writer.append(line);
		writer.newLine();
	}
	writer.flush();
	reader.close();
	writer.close();
	
	//use XPATH to get coordinates
    DocumentBuilderFactory domFactory = DocumentBuilderFactory.newInstance();
    domFactory.setNamespaceAware(true);
    DocumentBuilder builder;

	builder = domFactory.newDocumentBuilder();

    Document doc = builder.parse(output);
   
    XPathFactory factory = XPathFactory.newInstance();
    XPath xpath = factory.newXPath();
    XPathExpression expr = xpath.compile("//coordinates/text()");

    String coords = (String) expr.evaluate(doc, XPathConstants.STRING);

    positions = new LinkedList<GPSposition>();
    
    //use results to create Queue
    String[] lines = coords.split("\n");
    GPSposition gpsPosition;
    for(String tmp:lines){
    	String[] latlong = tmp.split(","); 
    	gpsPosition = new GPSposition(Double.parseDouble(latlong[0]),Double.parseDouble(latlong[1]));
    	positions.offer(gpsPosition);
    }
    
    //delete temporary file
    output.delete();
    
	}catch(IOException e){
		System.err.println("IO Errors during writing, reading Files " + e.getMessage());
	} catch (XPathExpressionException e) {
		System.err.println("Couldn't compile kml with XPATH " + e.getMessage());
	} catch (SAXException e) {
		System.err.println("Sax error " + e.getMessage());
	} catch (ParserConfigurationException e) {
		System.err.println("Couldn't init parser " + e.getMessage());
	}
	
  }
}
