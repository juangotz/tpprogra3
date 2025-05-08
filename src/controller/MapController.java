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
	
	public void loadPark() {
		p = new Park();
	}
	
	public ArrayList<Triplet<Integer,Integer,Integer>> doMSTWithKruskal() {
		return p.doMSTWithKruskal();
	}
	
	public ArrayList<Triplet<Integer,Integer,Integer>> doMSTWithPrim() {
		return p.doMSTWithPrim();
	}
	
	public Triplet<Double, Double, String> getStationData(int x) {
		return p.getStationData(x);
		
	}

}
