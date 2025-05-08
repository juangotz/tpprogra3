package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import algorithm.Kruskal;
import algorithm.Prim;

public class Park {
	HashMap<Integer, Station> stations;
	Graph graph;
	
	public Park() {
		stations = new HashMap<Integer, Station>();
		this.loadStations();
		this.loadGraph();
	}
	
	public Park(ArrayList<Station> stationList) {
		stations = new HashMap<Integer, Station>();
		this.loadStations(stationList);
	}
	
	private void loadStations() { //Estaciones template por propositos de testeo.
		Station a = new Station (-34.521, -58.7008, 0, "Estacion Rivadavia");
		stations.put(a.getNodeIndex(), a);
		Station b = new Station (-34.519, -58.7000, 1, "Estación Perón");
		stations.put(b.getNodeIndex(), b);
		Station c = new Station (-34.518, -58.7021, 2, "Estación Teloresumoasinomás");
		stations.put(c.getNodeIndex(), c);
		Station d = new Station (-34.516, -58.7012, 3, "Estacion Envido");
		stations.put(d.getNodeIndex(), d);
	}
	
	public Triplet<Double, Double, String> getStationData(int x) {
		if (!stations.containsKey(x)) {
			throw new IllegalArgumentException("STATION NOT FOUND");
		}
		Station aux = stations.get(x);
		Triplet<Double, Double, String> data = new Triplet<Double, Double, String>(aux.getXCoordinate(),aux.getYCoordinate(),aux.getName());
		return data; //X = X coordinate, Y = Y Coordinate, Z = Name;
	}
	
	public ArrayList<Triplet<Integer,Integer,Integer>> doMSTWithKruskal() {
		List<Edge> mst = Kruskal.getMST(this.graph);
		return obtainEdgesData(mst);
	}
	
	public ArrayList<Triplet<Integer,Integer,Integer>> doMSTWithPrim() {
		List<Edge> mst = Prim.getMST(this.graph, 0);
		return obtainEdgesData(mst);
	}

	private ArrayList<Triplet<Integer, Integer, Integer>> obtainEdgesData(List<Edge> mst) {
		ArrayList<Triplet<Integer,Integer,Integer>> tripletList = new ArrayList<Triplet<Integer,Integer,Integer>>();
		for(Edge edge : mst) {
			Triplet<Integer, Integer, Integer> trip = new Triplet<>(edge.getFrom(), edge.getTo(), edge.getWeight());
			tripletList.add(trip); //X=Station A, Y = Station B, Z = Weight
		}
		return tripletList;
	}
	
	private void loadStations(ArrayList<Station> stationList) {
		for (Station s : stationList) {
			this.stations.put(s.getNodeIndex(), s);
		}
	}

	private void loadGraph() { 
		graph = new Graph(4);
		graph.addEdge(0, 1, 10);
		graph.addEdge(0, 2, 6);
		graph.addEdge(0, 3, 5);
		graph.addEdge(1, 3, 15);
		graph.addEdge(2, 3, 4);
	}
}
