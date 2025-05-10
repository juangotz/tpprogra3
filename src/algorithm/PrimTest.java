package algorithm;

import model.Edge;
import model.Graph;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class PrimTest {

    @Test
    public void testPrimMSTConnectedGraph() {
        Graph graph = createGraph(4, new int[][]{
            {0, 1, 1}, {1, 2, 2}, {2, 3, 3}, {0, 3, 4}
        });
        List<Edge> mst = Prim.getMST(graph, 0);
        assertMST(mst, 3, 6);
    }

    @Test(expected = IllegalStateException.class)
    public void testPrimMSTDisconnectedGraph() {
        Graph graph = createGraph(4, new int[][]{
            {0, 1, 2}, {2, 3, 1}
        });
        Prim.getMST(graph, 0);
    }

    @Test
    public void testPrimEdgesWithSameWeight() {
        Graph g = createGraph(4, new int[][]{
            {0, 1, 1}, {0, 2, 1}, {1, 3, 1}, {2, 3, 1}
        });
        List<Edge> mst = Prim.getMST(g, 0);
        assertMST(mst, 3, 3);
    }

    @Test
    public void testPrimGraphWithCycle() {
        Graph g = createGraph(4, new int[][]{
            {0, 1, 10}, {1, 2, 15}, {2, 3, 4}, {3, 0, 6}
        });
        List<Edge> mst = Prim.getMST(g, 0);
        assertMST(mst, 3, 20);
    }

    @Test
    public void testPrimLinearGraph() {
        Graph g = createGraph(5, new int[][]{
            {0, 1, 2}, {1, 2, 3}, {2, 3, 4}, {3, 4, 5}
        });
        List<Edge> mst = Prim.getMST(g, 0);
        assertMST(mst, 4, 14);
    }
    
    private Graph createGraph(int nodes, int[][] edges) {
        Graph g = new Graph(nodes);
        for (int[] edge : edges) {
            g.addEdge(edge[0], edge[1], edge[2]);
        }
        return g;
    }

    private int calculateTotalWeight(List<Edge> edges) {
        return edges.stream().mapToInt(Edge::getWeight).sum();
    }

    private void assertMST(List<Edge> mst, int expectedSize, int expectedWeight) {
        assertEquals(expectedSize, mst.size());
        assertEquals(expectedWeight, calculateTotalWeight(mst));
    }
}
