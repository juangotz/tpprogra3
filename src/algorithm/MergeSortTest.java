package algorithm;

import static org.junit.Assert.*;
import java.util.*;
import org.junit.Test;

import model.Edge;

public class MergeSortTest {

	@Test
	public void testEmptyList() {
	    List<Edge> edges = new ArrayList<>();
	    
	    MergeSort.sort(edges, 0, edges.size() - 1);
	    
	    assertTrue(edges.isEmpty());
	}

	@Test
	public void testSingleElementList() {
	    List<Edge> edges = new ArrayList<>();
	    
	    edges.add(new Edge(0, 1, 10));
	    
	    MergeSort.sort(edges, 0, edges.size() - 1);
	    
	    assertEquals(1, edges.size());
	    assertEquals(10, edges.get(0).getWeight());
	}


	@Test
	public void testAlreadySortedList() {
	    List<Edge> edges = new ArrayList<>();
	    
	    edges.add(new Edge(0, 1, 1));
	    edges.add(new Edge(1, 2, 2));
	    edges.add(new Edge(2, 3, 3));

	    MergeSort.sort(edges, 0, edges.size() - 1);
	    
	    assertEquals(1, edges.get(0).getWeight());
	    assertEquals(2, edges.get(1).getWeight());
	    assertEquals(3, edges.get(2).getWeight());
	}

	@Test
	public void testInvertedList() {
	    List<Edge> edges = new ArrayList<>();
	    
	    edges.add(new Edge(0, 1, 30));
	    edges.add(new Edge(1, 2, 20));
	    edges.add(new Edge(2, 3, 10));

	    MergeSort.sort(edges, 0, edges.size() - 1);
	    
	    assertEquals(10, edges.get(0).getWeight());
	    assertEquals(20, edges.get(1).getWeight());
	    assertEquals(30, edges.get(2).getWeight());
	}
	
	@Test
	public void testDuplicatedWeight() {
	    List<Edge> edges = new ArrayList<>();
	    
	    edges.add(new Edge(0, 1, 3));
	    edges.add(new Edge(1, 2, 2));
	    edges.add(new Edge(2, 3, 1));
	    edges.add(new Edge(3, 4, 2));

	    MergeSort.sort(edges, 0, edges.size() - 1);
	    
	    assertEquals(1, edges.get(0).getWeight());
	    assertEquals(2, edges.get(1).getWeight());
	    assertEquals(2, edges.get(2).getWeight());
	    assertEquals(3, edges.get(3).getWeight());
	}
	
	@Test
	public void testGeneral() {
	    List<Edge> edges = new ArrayList<>();
	    
	    edges.add(new Edge(0, 1, 2));
	    edges.add(new Edge(1, 2, 9));
	    edges.add(new Edge(2, 3, 6));
	    edges.add(new Edge(2, 0, 6));
	    edges.add(new Edge(3, 1, 11));
	    edges.add(new Edge(3, 0, 4));

	    MergeSort.sort(edges, 0, edges.size() - 1);
	    
	    assertEquals(2, edges.get(0).getWeight());
	    assertEquals(4, edges.get(1).getWeight());
	    assertEquals(6, edges.get(2).getWeight());
	    assertEquals(6, edges.get(3).getWeight());
	    assertEquals(9, edges.get(4).getWeight());
	    assertEquals(11, edges.get(5).getWeight());
	}
}
