package controller;

import java.util.List;

import algorithm.AlgorithmTimer;
import model.Edge;
import model.Park;

public class AlgorithmController {

	private AlgorithmTimer timer = new AlgorithmTimer();
	private Park park;
	
	public AlgorithmController(Park p) {
		this.park = p;
	}
	

	public List<Edge> doMSTWithKruskal() {
	        return park.doMSTWithKruskal();
	    }
	
    public List<Edge> doMSTWithPrim() {
        return park.doMSTWithPrim();
    }
	
	public double getKruskalTime() {
		return timer.getKruskalTime(park.getGraph());
	}
	
	public double getPrimTime() {
		return timer.getPrimTime(park.getGraph());
	}
}
