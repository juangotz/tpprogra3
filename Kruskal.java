package algorithm;

import java.util.ArrayList;
import java.util.List;
import model.Graph;
import model.Edge;

public class Kruskal {
	
	public static List<Edge> getMST(Graph graph) {
		
		int n = graph.size();
	    int[] parent = new int[n];  // Representa el padre de cada nodo
	
	    // Inicializar: cada nodo es su propio padre
	    for (int i = 0; i < n; i++) {
	        parent[i] = i;
	    }
	
	    List<Edge> result = new ArrayList<>();
	    List<Edge> sortedEdges = graph.sortByWeight();
	
	    for (Edge edge : sortedEdges) {
	        int u = edge.getFrom();
	        int v = edge.getTo();
	
	        if (!find(u, v, parent)) {
	            result.add(edge);
	            union(u, v, parent);
	        }
	
	        if (result.size() == n - 1) {
	            break;
	        }
	    }
	    
	    if (result.size() != n - 1) {
	        throw new IllegalStateException("El grafo no es conexo: no se puede construir un MST completo.");
	    }
	    return result;
	}
		 
	 private static int root(int i, int[] parent) {
		 
		 while (parent[i] != i) {
            i = parent[i];
         }
         return i;
	 }

	 private static boolean find(int i, int j, int[] parent) {
		 return root(i, parent) == root(j, parent);
	 }

	 private static void union(int i, int j, int[] parent) {
		 
		 int rootI = root(i, parent);
	     int rootJ = root(j, parent);
	     parent[rootI] = rootJ;
	 }
}
