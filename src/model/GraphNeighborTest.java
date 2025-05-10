package model;

import static org.junit.Assert.*;
import org.junit.Test;

public class GraphNeighborTest
{
	@Test(expected = IllegalArgumentException.class)
	public void testNegativeNode()
	{
		Graph g = new Graph(5);
		g.getNeighbors(-1);
	}

	@Test(expected = IllegalArgumentException.class)
	public void testExceededNode()
	{
		Graph g = new Graph(5);
		g.getNeighbors(5);
	}

	@Test
	public void testAllIsolatedNode()
	{
		Graph g = new Graph(5);
		assertEquals(0, g.getNeighbors(2).size());
	}
	
	@Test
	public void testUniversalNode()
	{
		Graph g = new Graph(4);
		g.addEdge(1, 0, 0);
		g.addEdge(1, 2, 0);
		g.addEdge(1, 3, 0);
		
		int[] expected = {0, 2, 3};
		Assert.equalArray(expected, g.getNeighbors(1));
	}
	
	@Test
	public void testNormalNode()
	{
		Graph g = new Graph(5);
		g.addEdge(1, 3, 0);
		g.addEdge(2, 3, 0);
		g.addEdge(2, 4, 0);
		
		int[] expected = {1, 2};
		Assert.equalArray(expected, g.getNeighbors(3));
	}
}