package model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import algorithm.Kruskal;

public class Park {
	HashMap<Integer, Station> stations;
	Graph graph;
	
	public Park() {
		stations = new HashMap<Integer, Station>();
	}
	
	public void loadStations(ArrayList<Station> stationList) {
		for (Station s : stationList) {
			this.stations.put(s.getNodeIndex(), s);
		}
	}
	
	public Triplet<Double, Double, String> getStationData(Object x) {
		Station aux = stations.get(x);
		Triplet<Double, Double, String> data = new Triplet<Double, Double, String>(aux.getXCoordinate(),aux.getYCoordinate(),aux.getName());
		return data; //X = X coordinate, Y = Y Coordinate, Z = Name;
	}
	
	public ArrayList<Triplet<Integer,Integer,Integer>> doMSTWithKruskal() {
		ArrayList<Triplet<Integer,Integer,Integer>> tripletList = new ArrayList<Triplet<Integer,Integer,Integer>>();
		List<Edge> mst = Kruskal.getMST(this.graph);
		for(Edge edge : mst) {
			Triplet<Integer, Integer, Integer> trip = new Triplet<>(edge.getFrom(), edge.getTo(), edge.getWeight());
			tripletList.add(trip); //X=Station A, Y = Station B, Z = Weight
		}
		return tripletList;
	}

	public void loadTestGraph() { //Por propositos de testeo. **ELIMINAR LUEGO**
		graph = new Graph(4);
		graph.addEdge(0, 1, 10);
		graph.addEdge(0, 2, 6);
		graph.addEdge(0, 3, 5);
		graph.addEdge(1, 3, 15);
		graph.addEdge(2, 3, 4);
	}
}
