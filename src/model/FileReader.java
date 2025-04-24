package model;

import java.io.File;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;

public class FileReader {
	private String route;
	
	public void readFile() {
		DocumentBuilder builder;
		try {
			builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
			File f = new File(route);
			Document d = builder.parse(f);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	
}
