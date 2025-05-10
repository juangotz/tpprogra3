package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import FileReader.FileReader;
import algorithm.Kruskal;
import algorithm.Prim;

public class Park {
	HashMap<Integer, Station> stations;
	Graph graph;
	
	public Park(List<Station> stationList, List<Edge> edgeList) {
	    stations = new HashMap<>();
	    for (Station s : stationList) {
	        stations.put(s.getNodeIndex(), s);
	    }
	    graph = new Graph(stations.size());
	    for (Edge e : edgeList) {
	        graph.addEdge(e.getFrom(), e.getTo(), e.getWeight());
	    }
	}
	
	public Station getStationData(int x) {
		if (!stations.containsKey(x)) {
			throw new IllegalArgumentException("STATION NOT FOUND");
		}
		return stations.get(x);
	}
	
	public Graph getGraph() {
		return this.graph;
	}
	
	public List<Edge> doMSTWithKruskal() {
		return Kruskal.getMST(this.graph);
	}
	
	public List<Edge> doMSTWithPrim() {
		return Prim.getMST(this.graph, 0);
	}
	
	private void loadStations(ArrayList<Station> stationList) {
		for (Station s : stationList) {
			this.stations.put(s.getNodeIndex(), s);
		}
	}

}
