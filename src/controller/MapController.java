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
	
	public Triplet<Double, Double, String> getStationData(Object x) {
		return p.getStationData(x);
		
	}
	
	public void loadTestPark() { //Por propositos de testeo. **ELIMINAR LUEGO**
		p = new Park();
		ArrayList<Station> aux = generateTemplateStations();
		p.loadStations(aux);
		loadTestGraph();
	}

	private ArrayList<Station> generateTemplateStations() { //Por propositos de testeo. **ELIMINAR LUEGO**
		ArrayList<Station> stations = new ArrayList<Station>();
		Station a = new Station (-34.521, -58.7008, 0, "A");
		Station b = new Station (-34.519, -58.7000, 1, "B");
		Station c = new Station (-34.518, -58.7021, 2, "C");
		Station d = new Station (-34.516, -58.7012, 3, "D");
		stations.add(a);
		stations.add(b);
		stations.add(c);
		stations.add(d);
		return stations;
	}
	
	private void loadTestGraph() { //Por propositos de testeo. **ELIMINAR LUEGO**
		p.loadTestGraph();
	}
	
	

}
