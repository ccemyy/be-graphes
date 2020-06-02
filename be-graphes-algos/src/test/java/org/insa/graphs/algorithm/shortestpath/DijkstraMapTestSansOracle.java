package org.insa.graphs.algorithm.shortestpath;

import static org.junit.Assert.assertTrue;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;

import org.insa.graphs.algorithm.ArcInspector;
import org.insa.graphs.algorithm.ArcInspectorFactory;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.io.BinaryGraphReader;
import org.insa.graphs.model.io.GraphReader;
import org.junit.Test;

public class DijkstraMapTestSansOracle {


	@Test
	/* Verification le temps du chemin le plus rapide < le temps du chemin le plus court */
	/* Et verifie que la distance du chemin le plus rapide > a la distance du chemin le plus court */
	public void testScenarioSansOracle(String mapName, int origine, int destination) throws Exception {

		double costFastestSolutionInTime = Double.POSITIVE_INFINITY;
		double costFastestSolutionInDistance = Double.POSITIVE_INFINITY;
		double costShortestSolutionInTime = Double.POSITIVE_INFINITY;
		double costShortestSolutionInDistance = Double.POSITIVE_INFINITY;

		// Create a graph reader.
		GraphReader reader = new BinaryGraphReader(
				new DataInputStream(new BufferedInputStream(new FileInputStream(mapName))));

		// Read the graph.
		Graph graph = reader.read();

		if (origine>=0 || destination>=0) { // On est hors du graphe. / Sommets inexistants
			
			System.out.println("Origine : " + origine);
			System.out.println("Destination : " + destination);
			
			if(origine!=destination) {

				/** Recherche du chemin le plus rapide **/
				ArcInspector arcInspectorDijkstra = ArcInspectorFactory.getAllFilters().get(2);
		
				ShortestPathData data = new ShortestPathData(graph, graph.get(origine),graph.get(destination), arcInspectorDijkstra);
		
				DijkstraAlgorithm D = new DijkstraAlgorithm(data);
		
				/* Recuperation de la solution de Dijkstra */
				ShortestPathSolution solution = D.run();
							
				/** Recherche du chemin le plus court **/
		
				arcInspectorDijkstra = ArcInspectorFactory.getAllFilters().get(0);
		
				data = new ShortestPathData(graph, graph.get(origine),graph.get(destination), arcInspectorDijkstra);
		
				D = new DijkstraAlgorithm(data);
		
				/* Recuperation de la solution de Dijkstra */
				solution = D.run();
	
				/* Pas de chemin trouve */
				if (solution.getPath() != null) {
					/* Calcul du cout de la solution (en temps et en distance) */
					costFastestSolutionInTime = solution.getPath().getMinimumTravelTime();
					costFastestSolutionInDistance = solution.getPath().getLength();
				}
				/* Un plus court chemin trouve */
				else {
					System.out.println("PAS DE CHEMIN SOLUTION EN TEMPS");
				}
		
				/* Pas de chemin trouve */
				if (solution.getPath() == null) {
					System.out.println("PAS DE CHEMIN SOLUTION EN DISTANCE");
				}
				/* Un plus court chemin trouve */
				else {				
					/* Calcul du cout de la solution (en temps et en distance) */
					costShortestSolutionInTime = solution.getPath().getMinimumTravelTime();
					costShortestSolutionInDistance = solution.getPath().getLength();
				}
			
			
				/* Verifie que le temps du chemin le plus rapide est inferieur au temps du chemin le plus court */
				assertTrue(costFastestSolutionInTime <= costShortestSolutionInTime);
				System.out.println("Cout en temps du chemin le plus rapide : " + costFastestSolutionInTime);
				System.out.println("Cout en temps du chemin le plus court  : " + costShortestSolutionInTime);
		
				/* Et verifie que la distance du chemin le plus rapide est superieur a la distance du chemin le plus court */
				assertTrue(costFastestSolutionInDistance >= costShortestSolutionInDistance);
				System.out.println("Cout en distance du chemin le plus rapide : " + costFastestSolutionInDistance);
				System.out.println("Cout en distance du chemin le plus court  : " + costShortestSolutionInDistance);
	
				
			} else {
				System.out.println("les couts sont = 0 car Origine et Destination identiques");}
			
		} else {
			System.out.println("ERREUR : Paramètres invalides ");
		}
		System.out.println();
		System.out.println();
	
}

}