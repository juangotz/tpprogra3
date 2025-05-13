package controller;

import java.util.List;

import FileReader.FileReader;
import model.Edge;
import model.Park;
import model.Station;

public class MapDataLoader {
	private Park park;
	private FileReader reader;
	
	public MapDataLoader(String route) {
		reader = new FileReader(route);
	}
    
	public Park loadPark(String route){
		
		if(reader.verifyFile(route)) {
			reader.readFile();
			park = new Park(reader.getStations(), reader.getEdges());
		}
		return park;
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
    
    public int getEnviromentalDamage(List<Edge> aux) {
    	return park.calculateEnviromentalDamage(aux);
    }
    
    public Object getMessage() {
		return this.reader.getMessage();
	}

	public Park getPark() {
		return this.park;
	}
}
