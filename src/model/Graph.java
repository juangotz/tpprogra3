package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import algorithm.MergeSort;

public class Graph{
	
	// Representamos el grafo por su matriz de adyacencia
	private ArrayList<HashSet<Edge>> neighbors;
	
	public Graph(int nodes)
	{
		 neighbors = new ArrayList<>();
	        for (int i = 0; i < nodes; i++) {
	            neighbors.add(new HashSet<>());
		}
	}
	
	public void addEdge(int from, int to, int weight)
	{
		verifyEdge(from);
		verifyEdge(to);
		verifyDistinctNodes(from, to);
		
		Edge edge = new Edge(from, to, weight);
		Edge reverse = new Edge(to, from, weight);

		neighbors.get(from).add(edge);
	    neighbors.get(to).add(reverse); 
		
	}
	
	public boolean edgeExists(int from, int to)
	{
		verifyEdge(from);
		verifyEdge(to);
		verifyDistinctNodes(from, to);

		for(Edge edge : neighbors.get(from)) {
			if(edge.getTo() == to) {
				return true;
			}
		}
		return false;
	}

	public int size()
	{
		return neighbors.size();
	}
	
	public Set<Integer> getNeighbors(int i) {
        verifyEdge(i);
        
        Set<Integer> ret = new HashSet<>();
        for(Edge edge : neighbors.get(i)) {
        	ret.add(edge.getTo());
        }
        return ret;
	}
	
	public List<Edge> sortByWeight() {
	
		Set<String> seen = new HashSet<>();
		List<Edge> ret = new ArrayList<>();
		
		
		for (int from = 0; from < neighbors.size(); from++) {
            for (Edge edge : neighbors.get(from)) {
            	
                int to = edge.getTo();
                String key = Math.min(from, to) + "-" + Math.max(from, to);
                
                if (!seen.contains(key)) {
                    ret.add(edge);
                    seen.add(key);
                }
            }
        }
		
		MergeSort.sort(ret, 0, ret.size()-1);
		return ret;
	}
	
	public Set<Edge> getEdgesFrom(int node) {
	    verifyEdge(node);
	    return neighbors.get(node);
	}
	
	public List<Edge> getAllEdges() {
		
		Set<String> seen = new HashSet<>();
		List<Edge> ret = new ArrayList<>();
		
		
		for (int from = 0; from < neighbors.size(); from++) {
            for (Edge edge : neighbors.get(from)) {
            	
                int to = edge.getTo();
                String key = Math.min(from, to) + "-" + Math.max(from, to);
                
                if (!seen.contains(key)) {
                    ret.add(edge);
                    seen.add(key);
                }
            }
        }
		return ret;
	}

	private void verifyEdge(int i)
	{
		if( i < 0 )
			throw new IllegalArgumentException("El vertice no puede ser negativo: " + i);
		
		if( i >= size() )
			throw new IllegalArgumentException("Los vertices deben estar entre 0 y |V|-1: " + i);
	}

	private void verifyDistinctNodes(int i, int j)
	{
		if( i == j )
			throw new IllegalArgumentException("No se permiten loops: (" + i + ", " + j + ")");
	}

	public void addNode() {
		neighbors.add(new HashSet<>());
	}
	
	
}
