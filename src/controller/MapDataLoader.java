package controller;

import FileReader.FileReader;
import model.Park;
import model.Station;

public class MapDataLoader {
	private Park park;
	private FileReader reader;
	
	public Park loadPark(String adress) {
		reader = new FileReader(adress);
		reader.readFile();
		return new Park(reader.getStations(), reader.getEdges());
	}
    
    public Station getStationData(int x) {
        return park.getStationData(x);
    }

}
