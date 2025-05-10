package controller;

import java.util.ArrayList;
import java.util.List;

import org.openstreetmap.gui.jmapviewer.Coordinate;

import FileReader.FileReader;
import algorithm.Kruskal;
import algorithm.AlgorithmTimer;
import model.Edge;
import model.Graph;
import model.Park;
import model.Station;

public class MapController {
	private Park p;
	private AlgorithmTimer t = new AlgorithmTimer();
	private Coordinate focusCoordinate;
	
	public void loadPark() {
		FileReader reader = new FileReader("src/FileReader/mapa.xml");
		reader.readFile();
		p = new Park(reader.getStations(), reader.getEdges());
		focusCoordinate = reader.getFocusCoordinate(); // nueva parte
	}
	
	public Coordinate getFocusCoordinate() {
	    return focusCoordinate;
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
