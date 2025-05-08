package algorithm;

import model.Graph;

public class AlgorithmTimer {

	public double getKruskalTime(Graph graph) {
		long start = System.currentTimeMillis();
		for (int i = 0; i <50000; i++) { // Para medir el tiempo.
			Kruskal.getMST(graph);
		}
		long finish = System.currentTimeMillis();
		return (finish - start) / 1000.0;
	}

	public double getPrimTime(Graph graph) {
		long start = System.currentTimeMillis();
		for (int i = 0; i <50000; i++) { // Para medir el tiempo.
			Prim.getMST(graph, 0);
		}
		long finish = System.currentTimeMillis();
		return (finish - start) / 1000.0;
	}
}
