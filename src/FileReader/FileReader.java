package FileReader;

import org.w3c.dom.*;
import javax.xml.parsers.*;
import java.io.*;
import java.util.*;
import org.w3c.dom.Node;

import model.Edge;
import model.Station;

public class FileReader {
    private String route;
    private String loadErrorMessage;
    private List<Station> stations;
    private List<Edge> edges;
    private double focusCoordinateLat;
    private double focusCoordinateLon;

    public FileReader(String route) {
        this.route = route;
        this.stations = new ArrayList<>();
        this.edges = new ArrayList<>();
    }

    public void readFile() {
        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            File f = new File(route);
            Document doc = builder.parse(f);
            
            NodeList parkCoords = doc.getElementsByTagName("focusCoordinate");
            if (parkCoords.getLength() > 0) {
                Node node = parkCoords.item(0);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element coordElement = (Element) node;
                    double lat = Double.parseDouble(coordElement.getElementsByTagName("lat").item(0).getTextContent());
                    double lon = Double.parseDouble(coordElement.getElementsByTagName("lon").item(0).getTextContent());
                   
                    focusCoordinateLat = lat;
                    focusCoordinateLon = lon;
                }
            }
            
            NodeList stationNodes = doc.getElementsByTagName("station");
            for (int i = 0; i < stationNodes.getLength(); i++) {
                Node node = stationNodes.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element stationElement = (Element) node;
                    double lat = Double.parseDouble(stationElement.getElementsByTagName("lat").item(0).getTextContent());
                    double lon = Double.parseDouble(stationElement.getElementsByTagName("lon").item(0).getTextContent());
                    int id = Integer.parseInt(stationElement.getElementsByTagName("id").item(0).getTextContent());
                    String name = stationElement.getElementsByTagName("name").item(0).getTextContent();

                    Station station = new Station(lat, lon, id, name);
                    stations.add(station);
                }
            }

            NodeList edgeNodes = doc.getElementsByTagName("edge");
            for (int i = 0; i < edgeNodes.getLength(); i++) {
                Node node = edgeNodes.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element edgeElement = (Element) node;
                    int from = Integer.parseInt(edgeElement.getElementsByTagName("from").item(0).getTextContent());
                    int to = Integer.parseInt(edgeElement.getElementsByTagName("to").item(0).getTextContent());
                    int weight = Integer.parseInt(edgeElement.getElementsByTagName("weight").item(0).getTextContent());

                    Edge edge = new Edge(from, to, weight);
                    edges.add(edge);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public boolean verifyFile(String route) {
		if (!verifyFileExists(route)) {
			loadErrorMessage = "Archivo no existe";
			return false;
		}
		if (!verifyXmlFileType(route)) {
			loadErrorMessage = "Archivo no es .xml";
			return false;
		}
		return true;
	}

	private boolean verifyFileExists(String route) {
		File f = new File(route);
		return f.exists();
	}
	
	private boolean verifyXmlFileType(String route) {
		StringBuilder aux = new StringBuilder(route);
		aux.delete(0, (route.length()-4));
		String result = aux.toString();
		return result.compareTo(".xml")==0;
		
	}
	
	public Object getMessage() {
		return loadErrorMessage;
	}
    
    public double getFocusCoordinateLat() {
        return focusCoordinateLat;
    }
    
    public double getFocusCoordinateLon() {
        return focusCoordinateLon;
    }
    
    public List<Station> getStations() {
        return stations;
    }

    public List<Edge> getEdges() {
        return edges;
    }
    

}
