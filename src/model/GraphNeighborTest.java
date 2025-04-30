package model;
import static org.junit.Assert.*;
import org.junit.Test;


public class GraphNeighborTest {
	@Test(expected = IllegalArgumentException.class)
	public void negativeEdge()
	{
		Graph grafo = new Graph(5);
		grafo.getNeighbors(-1);
	}

}
