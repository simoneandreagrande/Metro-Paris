package it.polito.tdp.metroparis;

import java.util.List;

import org.jgrapht.Graph;
import org.jgrapht.Graphs;
import org.jgrapht.graph.DefaultEdge;
import org.jgrapht.graph.SimpleGraph;

public class ProvaGrafo {

	public static void main(String[] args) {
		
		// grafo semplice, non orientato non pesato
		Graph<String, DefaultEdge> grafo =
				new SimpleGraph<String, DefaultEdge>(DefaultEdge.class);
		
		
		grafo.addVertex("r");
		grafo.addVertex("s");
		grafo.addVertex("t");
		grafo.addVertex("v");
		grafo.addVertex("w");
		grafo.addVertex("x");
		// altri vertici
		
//		addAllVertices(grafo: "r", "s", "t", "u", "v");
		
		
		grafo.addEdge("r", "s");
		grafo.addEdge("r", "v");
		grafo.addEdge("s", "w");
		grafo.addEdge("t", "x");
		grafo.addEdge("t", "w");
		grafo.addEdge("w", "x");
		//	altri archi
		
		System.out.println(grafo.toString());
		
		
		System.out.println("Vertici: " + grafo.vertexSet().size());
		System.out.println("Vertici: " + grafo.edgeSet().size());
		
		
		for(String v : grafo.vertexSet()) {
			System.out.println("Vertice " + v + " ha grafo " + grafo.degreeOf(v));
			for (DefaultEdge arco : grafo.edgesOf(v)) {
//				System.out.println(arco);
//				if(v.equals(grafo.getEdgeSource(arco))) {
//					String arrivo = grafo.getEdgeTarget(arco);
//					System.out.println("\tè connesso a " + arrivo);
//				} else {
//					String arrivo = grafo.getEdgeSource(arco);
//					System.out.println("\tè connesso a " + arrivo);
//				}
			
				String arrivo = Graphs.getOppositeVertex(grafo, arco, v);
				System.out.println("\tè connesso a " + arrivo);
			}
				
			
			List<String> vicini = Graphs.neighborListOf(grafo, v);
			System.out.println("\tI vicini sono " + vicini);
		}
		
	}

}
