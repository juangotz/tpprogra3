package model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class Graph
{
	// Representamos el grafo por su matriz de adyacencia
	private ArrayList<HashSet<Integer>> neighbors;
	
	// La cantidad de vertices esta predeterminada desde el constructor
	public Graph(int vertex)
	{
		/*A = new boolean[vertex][vertex];*/
		neighbors = new ArrayList<HashSet<Integer>>();
		for (int i = 0; i < vertex; i++) {
			neighbors.add(new HashSet<Integer>());
		}
	}
	
	// Agregado de aristas
	public void addEdge(int i, int j, int weight)
	{
		verifyEdge(i);
		verifyEdge(j);
		verifyDistinctNodes(i, j);

		neighbors.get(i).add(j);
		neighbors.get(j).add(i);
		
	}

	// Eliminacion de aristas
	public void deleteEdge(int i, int j)
	{
		verifyEdge(i);
		verifyEdge(j);
		verifyDistinctNodes(i, j);
		
		/*
		A[i][j] = false;
		A[j][i] = false;
		*/

		neighbors.get(i).remove(j);
		neighbors.get(j).remove(i);
	}

	// Informa si existe la arista especificada
	public boolean edgeExists(int i, int j)
	{
		verifyEdge(i);
		verifyEdge(j);
		verifyDistinctNodes(i, j);

		//return A[i][j];
		
		return neighbors.get(i).contains(j);
	}

	// Cantidad de vertices
	public int size()
	{
		return neighbors.size();
	}
	
	// Vecinos de un vertice
	public Set<Integer> getNeighbors(int i)
	{
		verifyEdge(i);
		
		Set<Integer> ret = new HashSet<Integer>();
		for(int j = 0; j < size(); ++j) if( i != j )
		{
			if( this.edgeExists(i,j) )
				ret.add(j);
		}
		
		return ret;		
	}
	
	// Verifica que sea un vertice valido
	private void verifyEdge(int i)
	{
		if( i < 0 )
			throw new IllegalArgumentException("El vertice no puede ser negativo: " + i);
		
		if( i >= size() )
			throw new IllegalArgumentException("Los vertices deben estar entre 0 y |V|-1: " + i);
	}

	// Verifica que i y j sean distintos
	private void verifyDistinctNodes(int i, int j)
	{
		if( i == j )
			throw new IllegalArgumentException("No se permiten loops: (" + i + ", " + j + ")");
	}
}
