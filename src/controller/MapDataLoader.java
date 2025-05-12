package controller;

import java.util.List;

import FileReader.FileReader;
import model.Edge;
import model.Park;
import model.Station;

public class MapDataLoader {
	private Park park;
	private FileReader reader;
	
	public Park loadPark(String data) {
		if(data.equals("Mapa 1")) {
			reader = new FileReader("src/FileReader/mapa1.xml");
		} 
		if(data.equals("Mapa 2")){
			reader = new FileReader("src/FileReader/mapa2.xml");
		}
		reader.readFile();
		park = new Park(reader.getStations(), reader.getEdges());
		return park;
	}
    
	 public Station getStationData(int x) {
	        return park.getStationData(x);
	    }
	
	public List<Station> getStations() {
        return reader.getStations();
    }

    public List<Edge> getEdges() {
        return reader.getEdges();
    }
    
    public double getFocusCoordinateLat() {
        return reader.getFocusCoordinateLat();
    }
    
    public double getFocusCoordinateLon() {
        return reader.getFocusCoordinateLon();
    }
}
