package controller;

import java.util.List;

import FileReader.FileReader;
import algorithm.AlgorithmTimer;
import model.Edge;
import model.Park;
import model.Station;

public class MapController {
	private Park p;
	private AlgorithmTimer t = new AlgorithmTimer();
	private FileReader reader;
	
	public void loadPark() {
		reader = new FileReader("src/FileReader/mapa.xml");
		reader.readFile();
		p = new Park(reader.getStations(), reader.getEdges());
	}
	
	public double getFocusCoordinateLat() {
        return reader.getFocusCoordinateLat();
    }

    public double getFocusCoordinateLon() {
        return reader.getFocusCoordinateLon();
    }
	
    public List<Edge> doMSTWithKruskal() {
        return p.doMSTWithKruskal();
    }
	
    public List<Edge> doMSTWithPrim() {
        return p.doMSTWithPrim();
    }
	
    public Station getStationData(int x) {
        return p.getStationData(x);
    }
	
	public double getKruskalTime() {
		return t.getKruskalTime(p.getGraph());
	}
	
	public double getPrimTime() {
		return t.getPrimTime(p.getGraph());
	}

}
