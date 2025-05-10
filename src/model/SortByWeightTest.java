package model;

import org.junit.Test;

public class SortByWeightTest {

	@Test
	public void testAllEdgesDistinctWeight() {
		
		Graph g = new Graph(4);
		
		g.addEdge(0, 1, 6);
		g.addEdge(1, 2, 3);
		g.addEdge(2, 3, 4);
		
		int[] expected = {3, 4, 6};
		
		Assert.equalArray(expected, g.sortByWeight());
	}
	
	@Test
	public void teestDuplicatedWeight() {
		
		Graph g = new Graph(5);
		
		g.addEdge(0, 1, 6);
		g.addEdge(1, 2, 3);
		g.addEdge(2, 3, 4);
		g.addEdge(3, 4, 3);
		
		int[] expected = {3, 3, 4, 6};
		
		Assert.equalArray(expected, g.sortByWeight());
	}

	@Test
	public void testSameWeight() {
		
		Graph g = new Graph(5);
		
		g.addEdge(0, 1, 4);
		g.addEdge(1, 2, 4);
		g.addEdge(2, 3, 4);
		g.addEdge(3, 4, 4);
		
		int[] expected = {4, 4, 4, 4};
		
		Assert.equalArray(expected, g.sortByWeight());
	}
	
	@Test
	public void testIsolatedNodes() {
		
		Graph g = new Graph(5);
		
		int[] expected = {};
		
		Assert.equalArray(expected, g.sortByWeight());
	}
}
