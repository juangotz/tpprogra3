package controller;

import java.util.List;
import model.Edge;
import model.Graph;
import model.Station;
import model.Park;
import algorithm.AlgorithmTimer;
import algorithm.Kruskal;
import algorithm.Prim;

public class AlgorithmController {
    

    private AlgorithmTimer timer;

    public AlgorithmController() {
  
        this.timer = new AlgorithmTimer();
    }

    public Graph doMSTWithKruskal(Graph graph) {
    	
        List<Edge> mstEdges = Kruskal.getMST(graph);
        Graph mstGraph = new Graph(graph.size());
        for(Edge e : mstEdges) {
        	mstGraph.addEdge(e.getFrom(), e.getTo(), e.getWeight());
        }
        
        return mstGraph;
    }

    public Graph doMSTWithPrim(Graph graph) {

        List<Edge> mstEdges = Prim.getMST(graph, 0);
        Graph mstGraph = new Graph(graph.size());
        for(Edge e : mstEdges) {
        	mstGraph.addEdge(e.getFrom(), e.getTo(), e.getWeight());
        }
        
        return mstGraph;
    }

    public int calculateEnvironmentalDamage(List<Edge> path) {
        int total = 0;
        for (Edge e : path) total += e.getWeight();
        return total;
    }

	
	public double getKruskalTime(Graph graph) {
		return timer.getKruskalTime(graph);
	}
	
	public double getPrimTime(Graph graph) {
		return timer.getPrimTime(graph);
	}

}
