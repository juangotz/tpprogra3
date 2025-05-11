package controller;

import FileReader.FileReader;
import model.Park;
import model.Station;

public class MapDataLoader {
	private Park park;
	private FileReader reader;
	
	public void loadPark() {
		reader = new FileReader("src/FileReader/mapa.xml");
		reader.readFile();
		park = new Park(reader.getStations(), reader.getEdges());
	}
	
	public double getFocusCoordinateLat() {
        return reader.getFocusCoordinateLat();
    }

    public double getFocusCoordinateLon() {
        return reader.getFocusCoordinateLon();
    }
    
    public Station getStationData(int x) {
        return park.getStationData(x);
    }

}
