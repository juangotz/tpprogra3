package model;

import static org.junit.Assert.*;
import org.junit.Test;

public class EdgeEditingTest {
	@Test(expected = IllegalArgumentException.class)
	public void primerVerticeNegativoTest() {
		Graph Graph = new Graph(5);
		Graph.addEdge(-1, 3, 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void primerVerticeExcedidoTest() {
		Graph Graph = new Graph(5);
		Graph.addEdge(5, 2, 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void segundoVerticeNegativoTest() {
		Graph Graph = new Graph(5);
		Graph.addEdge(2, -1, 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void segundoVerticeExcedidoTest() {
		Graph Graph = new Graph(5);
		Graph.addEdge(2, 5, 0);
	}

	@Test(expected = IllegalArgumentException.class)
	public void agregarLoopTest() {
		Graph Graph = new Graph(5);
		Graph.addEdge(2, 2, 0);
	}

	@Test
	public void aristaExistenteTest() {
		Graph Graph = new Graph(5);
		Graph.addEdge(2, 3, 0);
		assertTrue(Graph.edgeExists(2, 3));
	}

	@Test
	public void aristaOpuestaTest() {
		Graph Graph = new Graph(5);
		Graph.addEdge(2, 3, 0);
		assertTrue(Graph.edgeExists(3, 2));
	}

	@Test
	public void aristaInexistenteTest() {
		Graph Graph = new Graph(5);
		Graph.addEdge(2, 3, 0);
		assertFalse(Graph.edgeExists(1, 4));
	}

	@Test
	public void addEdgeDosVecesTest() {
		Graph Graph = new Graph(5);
		Graph.addEdge(2, 3, 0);
		Graph.addEdge(2, 3, 0);

		assertTrue(Graph.edgeExists(2, 3));
	}
}