package controller;

import java.util.ArrayList;
import java.util.List;

import algorithm.Kruskal;
import model.Edge;
import model.Graph;
import model.Park;
import model.Station;
import model.Triplet;

public class MapController {
	private Park p;
	
	public ArrayList<Triplet<Integer,Integer,Integer>> doMSTWithKruskal() {
		return p.doMSTWithKruskal();
	}
	
	public Triplet<Double, Double, String> getStationData(int x) {
		return p.getStationData(x);
		
	}
	
	public void loadTestPark() { //Por propositos de testeo. **ELIMINAR LUEGO**
		p = new Park();
		p.loadStations();
		p.loadTestGraph();
	}
}
