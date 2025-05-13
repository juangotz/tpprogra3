package model;

import static org.junit.Assert.*;
import org.junit.Test;

public class EdgeEditingTest {
	@Test(expected = IllegalArgumentException.class)
	public void testNegativeFirstNode() {
		Graph Graph = new Graph(5);
		Graph.addEdge(-1, 3, 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testExceededFirstNode() {
		Graph Graph = new Graph(5);
		Graph.addEdge(5, 2, 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testNegativeSecondNode() {
		Graph Graph = new Graph(5);
		Graph.addEdge(2, -1, 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testExceededSecondNode() {
		Graph Graph = new Graph(5);
		Graph.addEdge(2, 5, 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testLoop() {
		Graph Graph = new Graph(5);
		Graph.addEdge(2, 2, 0);
	}

	@Test
	public void testExistingEdge() {
		Graph Graph = new Graph(5);
		Graph.addEdge(2, 3, 0);
		assertTrue(Graph.edgeExists(2, 3));
	}

	@Test
	public void testOppositeEdge() {
		Graph Graph = new Graph(5);
		Graph.addEdge(2, 3, 0);
		assertTrue(Graph.edgeExists(3, 2));
	}

	@Test
	public void testNonExistingEdge() {
		Graph Graph = new Graph(5);
		Graph.addEdge(2, 3, 0);
		assertFalse(Graph.edgeExists(1, 4));
	}

	@Test
	public void testRepeatedEdges() {
		Graph Graph = new Graph(5);
		Graph.addEdge(2, 3, 0);
		Graph.addEdge(2, 3, 0);

		assertTrue(Graph.edgeExists(2, 3));
	}
}