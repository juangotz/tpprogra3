package model;

import java.util.HashMap;
import java.util.List;

import algorithm.Kruskal;
import algorithm.Prim;

public class Park {
	HashMap<Integer, Station> stations;
	Graph graph;
	
	public Park(List<Station> stationList, List<Edge> edgeList) {
		verifyValidParameters(stationList, edgeList);
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
			throw new IllegalArgumentException("Estacion no encontrada");
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
	
	private void verifyValidParameters(List<Station> stationList, List<Edge> edgeList) {
		if(stationList.isEmpty()) {
			throw new IllegalArgumentException ("Parque debe contener estaciones");
		}
		if (edgeList.isEmpty()) {
			throw new IllegalArgumentException ("Grafo debe ser conexo. Por ende, debe contener aristas");
		}
	}

}
