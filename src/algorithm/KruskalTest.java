package algorithm;

import static org.junit.Assert.assertEquals;
import java.util.List;
import org.junit.Test;
import model.Edge;
import model.Graph;

public class KruskalTest {

    @Test
    public void testKruskalMSTConnectedGraph() {
        Graph graph = new Graph(4);
        graph.addEdge(0, 1, 1);
        graph.addEdge(1, 2, 2);
        graph.addEdge(2, 3, 3);
        graph.addEdge(0, 3, 4);

        List<Edge> mst = Kruskal.getMST(graph);

        int pesoTotal = mst.stream().mapToInt(Edge::getWeight).sum();
        assertEquals(6, pesoTotal); // 1 + 2 + 3
        assertEquals(3, mst.size());
    }

    @Test(expected = IllegalStateException.class)
    public void testKruskalMSTDisconnectedGraph() {
        Graph graph = new Graph(4);
        graph.addEdge(0, 1, 2);
        graph.addEdge(2, 3, 1);  

        Kruskal.getMST(graph); // Debería lanzar excepción si el grafo no es conexo
    }

    @Test
    public void testKruskalEdgesWithSameWeight() {
        Graph g = new Graph(4);
        g.addEdge(0, 1, 1);
        g.addEdge(0, 2, 1);
        g.addEdge(1, 3, 1);
        g.addEdge(2, 3, 1);

        List<Edge> mst = Kruskal.getMST(g);
        assertEquals(3, mst.size());
        int totalPeso = mst.stream().mapToInt(Edge::getWeight).sum();
        assertEquals(3, totalPeso); // 3 aristas de peso 1
    }

    @Test
    public void testKruskalGraphWithCycle() {
        Graph g = new Graph(4);
        g.addEdge(0, 1, 10);
        g.addEdge(1, 2, 15);
        g.addEdge(2, 3, 4);
        g.addEdge(3, 0, 6);

        List<Edge> mst = Kruskal.getMST(g);
        assertEquals(3, mst.size());
        int totalPeso = mst.stream().mapToInt(Edge::getWeight).sum();
        assertEquals(20, totalPeso); // 4 + 6 + 10
    }

    @Test
    public void testKruskalLinearGraph() {
        Graph g = new Graph(5);
        g.addEdge(0, 1, 2);
        g.addEdge(1, 2, 3);
        g.addEdge(2, 3, 4);
        g.addEdge(3, 4, 5);

        List<Edge> mst = Kruskal.getMST(g);
        assertEquals(4, mst.size());
        int totalPeso = mst.stream().mapToInt(Edge::getWeight).sum();
        assertEquals(14, totalPeso); // 2 + 3 + 4 + 5
    }
}


