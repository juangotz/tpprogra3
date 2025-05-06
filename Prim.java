package algorithm;

import java.util.*;
import model.Graph;
import model.Edge;

public class Prim {

    public static List<Edge> getMST(Graph graph, int startNode) {
        int n = graph.size();
        Set<Integer> visited = new HashSet<>();
        PriorityQueue<Edge> minHeap = new PriorityQueue<>(Comparator.comparingInt(Edge::getWeight));
        List<Edge> mst = new ArrayList<>();

        visited.add(startNode);
        addEdgesToMinHeap(startNode, visited, minHeap, graph);

        while (!minHeap.isEmpty()) {
            Edge edge = minHeap.poll();
            int to = edge.getTo();

            if (visited.contains(to)) {
                continue;
            }

            visited.add(to);
            mst.add(edge);

            addEdgesToMinHeap(to, visited, minHeap, graph);
        }
        if (mst.size() < n - 1) {
            throw new IllegalStateException("El grafo no es conexo: no se puede construir un MST completo.");
        }
        return mst;
    }

    private static void addEdgesToMinHeap(int node, Set<Integer> visited, PriorityQueue<Edge> minHeap, Graph graph) {
        for (Edge edge : graph.getEdgesFrom(node)) {
            if (!visited.contains(edge.getTo())) {
                minHeap.add(edge);
            }
        }
    }
    
}