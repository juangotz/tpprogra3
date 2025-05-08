package controller;

import java.util.ArrayList;
import java.util.List;

import algorithm.Kruskal;
import algorithm.AlgorithmTimer;
import model.Edge;
import model.Graph;
import model.Park;
import model.Station;
import model.Triplet;

public class MapController {
	private Park p;
	private AlgorithmTimer t = new AlgorithmTimer();
	
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
	
	public double getKruskalTime() {
		return t.getKruskalTime(p.getGraph());
	}
	
	public double getPrimTime() {
		return t.getPrimTime(p.getGraph());
	}

}
