package model;

import java.util.*;
import algorithm.Kruskal;
import algorithm.Prim;

public class Park {
    private HashMap<Integer, Station> stations;
    private Graph graph;

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
            throw new IllegalArgumentException("Estación no encontrada");
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
    
    public int calculateEnviromentalDamage(List<Edge> pathData) {
    	int result = 0;
    	for (Edge e : pathData) {
    		result += e.getWeight();
    	}
    	return result;
    }
    private void verifyValidParameters(List<Station> stationList, List<Edge> edgeList) {
        if (stationList.isEmpty()) {
            throw new IllegalArgumentException("Parque debe contener estaciones");
        }
        if (edgeList.isEmpty() && stationList.size()!=1) {
            throw new IllegalArgumentException("Grafo debe tener aristas para resolver Sendero Minimo");
        }
    }
}

