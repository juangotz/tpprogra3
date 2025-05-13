package model;

import java.util.*;

import org.openstreetmap.gui.jmapviewer.Coordinate;

public class Park {
    private HashMap<Integer, Station> stations;
    private int stationIndex;
    private Graph graph;

    public Park(List<Station> stationList, List<Edge> edgeList) {
        //verifyValidParameters(stationList, edgeList);
        stations = new HashMap<>();
        stationIndex = 0;
        
        for (Station s : stationList) {
            stations.put(stationIndex, s); 
            stationIndex++;
        }
        
        graph = new Graph(stationIndex);
        for (Edge e : edgeList) {
            graph.addEdge(e.getFrom(), e.getTo(), e.getWeight());
        }
    }

    public Station getStationData(int index) {
        if (!stations.containsKey(index)) {
            throw new IllegalArgumentException("Estación no encontrada");
        }
        return stations.get(index);
    }

    public Graph getGraph() {
        return this.graph;
    }
    
    public int calculateEnviromentalDamage(List<Edge> pathData) {
    	int result = 0;
    	for (Edge e : pathData) {
    		result += e.getWeight();
    	}
    	return result;
    }
    private void verifyValidParameters(List<Station> stationList, List<Edge> edgeList) {
        if (stationList.isEmpty()) {
            throw new IllegalArgumentException("Parque debe contener estaciones");
        }
        if (edgeList.isEmpty()) {
            throw new IllegalArgumentException("Grafo debe ser conexo. Por ende, debe contener aristas");
        }
    }

	public void addStation(String name, Coordinate coordinate) {
		Station station = new Station(coordinate.getLat(),coordinate.getLon(),stationIndex, name);
		this.stations.put(stationIndex, station);
		this.graph.addNode();
		stationIndex++;
	}

	public boolean availableCoordinate(Coordinate coordinate) {
		double Xcoord = coordinate.getLat();
		double Ycoord = coordinate.getLon();
		for (Station s : stations.values()) {
            if(s.getXCoordinate() == Xcoord && s.getYCoordinate() == Ycoord) {
            	return false;
            }
        }
		return true;
	}

	public HashMap<Integer, Station> getStations() {
		return this.stations;
	}

	public void addPath(int from, int to, int weight) {
		 if (!stations.containsKey(from) || !stations.containsKey(to)) {
		        throw new IllegalArgumentException("Una o ambas estaciones no existen. from=" + from + ", to=" + to);
		    }

		this.graph.addEdge(from,to,weight);
	
	}
	public int calculateEnviromentalDamage() {
        int result = 0;
        for (Edge e : graph.getAllEdges()) {
            result += e.getWeight();
        }
        return result;
    }
	
}

