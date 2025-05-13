package controller;

import java.util.List;

import org.openstreetmap.gui.jmapviewer.Coordinate;
import org.openstreetmap.gui.jmapviewer.interfaces.ICoordinate;

import FileReader.FileReader;
import algorithm.AlgorithmTimer;
import model.Edge;
import model.Park;
import model.Station;

public class AlgorithmController {

	private AlgorithmTimer timer;
	private Park park;
	private FileReader reader;
	
	public AlgorithmController(String route) {
		reader = new FileReader(route);
		reader.readFile();
		park = new Park(reader.getStations(), reader.getEdges());
		timer = new AlgorithmTimer();
	}

	public List<Edge> doMSTWithKruskal() {
	        return park.doMSTWithKruskal();
	    }
	
    public List<Edge> doMSTWithPrim() {
        return park.doMSTWithPrim();
    }
	
	public double getKruskalTime() {
		return timer.getKruskalTime(park.getGraph());
	}
	
	public double getPrimTime() {
		return timer.getPrimTime(park.getGraph());
	}

}
