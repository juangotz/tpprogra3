package model;

import java.util.List;

import algorithm.Kruskal;

public class Main {

	public static void main(String[] args) {
		Graph g = new Graph(4);
		g.addEdge(0, 1, 10);
		g.addEdge(0, 2, 6);
		g.addEdge(0, 3, 5);
		g.addEdge(1, 3, 15);
		g.addEdge(2, 3, 4);

		List<Edge> mst = Kruskal.getMST(g);
		Graph MSTgraph = new Graph(g.size());
		for(Edge edge : mst) {
			MSTgraph.addEdge(edge.getFrom(), edge.getTo(), edge.getWeight());
		}
		

		printNeighborList(g);
		System.out.println("\n");
		printNeighborList(MSTgraph);

	}

	private static void printNeighborList(Graph g) {
		for(int i = 0; i < g.size(); i++ ) {
			System.out.print("[");
			for(Integer n : g.getNeighbors(i)) {
				System.out.print(n + " ");
			}
			System.out.print("]");

		}
	}
}
