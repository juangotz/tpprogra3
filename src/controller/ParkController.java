package controller;

import java.util.ArrayList;
import java.util.List;

import org.openstreetmap.gui.jmapviewer.Coordinate;

import algorithm.Kruskal;
import algorithm.Prim;
import model.Edge;
import model.Graph;
import model.Park;
import model.Station;

public class ParkController {

	private Park park;
	
	public boolean parkExists() {
		if(this.park != null) {
			return true;
		}
		return false;
	}

	public void createNewPark() {
		
		if(!parkExists()) {
			List<Station> stations = new ArrayList<Station>();
			List<Edge> paths = new ArrayList<Edge>();
			Park p = new Park(stations, paths);
			this.park = p;
			System.out.println("nuevo parque creado");
		}else {
			throw new RuntimeException("Ya hay un parque cargado.");
		}
	}
	
	public void addNewStation(String name, Coordinate coordinate) {
		
		if(park.availableCoordinate(coordinate)) {
			this.park.addStation(name, coordinate);
			System.out.println("estacion " + name + " con coord: " + coordinate.getLat() + ", " + coordinate.getLon() + " agregada.");
		}else {
			throw new IllegalArgumentException("no se puede poner dos estaciones en la misma coordenada.");
		}
		
	}

	public Park getPark() {
		return park;
	}
	
	public Graph getGraph() {
	    return park.getGraph();
	}

	public void addNewPath(int from, int to, int weight) {
		this.park.addPath(from, to, weight);
		System.out.println("sendero de " + from + " a " + to + " agregado.");
	}
	
	public List<Station> getStationList() {
		List<Station> stations = new ArrayList<Station>();
		
		for (Station s: this.park.getStations().values()) {
			stations.add(s);
		}
		return stations;
	}

	public void removePark() {
		this.park = null;
	}

	public void createNewParkFromGraph(Graph mstGraph, List<Station> originalStations) {
		this.park = new Park(originalStations, mstGraph.getAllEdges());
	}

	public void addNewParkFromFile(Park park) {
		List<Station> stations = new ArrayList<Station>();
		
		for (Station s: park.getStations().values()) {
			stations.add(s);
		}
	
		List<Edge> paths = park.getGraph().getAllEdges();
		Park newPark = new Park(stations, paths);
		this.park = newPark;
	}
	
	public int calculateEnviromentalDamage() {
        return this.park.calculateEnviromentalDamage();
    }
}
