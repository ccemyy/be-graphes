package org.insa.graphs.algorithm.shortestpath;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import org.insa.graphs.algorithm.ArcInspector;
import org.insa.graphs.algorithm.ArcInspectorFactory;
import org.insa.graphs.model.Graph;

import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.FileInputStream;

import org.insa.graphs.model.io.BinaryGraphReader;
import org.insa.graphs.model.io.GraphReader;

public class DijkstraMapTest {

	@Test
	public void testScenario(String mapName, int typeEvaluation, int origine, int destination) throws Exception {

		// Create a graph reader.
		GraphReader reader = new BinaryGraphReader(
				new DataInputStream(new BufferedInputStream(new FileInputStream(mapName))));

		// Read the graph.
		Graph graph = reader.read();

		if (typeEvaluation!=0 && typeEvaluation!=1) {
			System.out.println("Argument invalide");
		} else {
			// out of the graph or nonexistent vertices
			if (origine<0 || destination<0 || origine>=(graph.size()-1) || destination>=(graph.size()-1)) { 
				System.out.println("ERREUR : Paramètres invalides ");
				
			} else {
				ArcInspector arcInspectorDijkstra;
				
				if (typeEvaluation == 0) {  // typeEvaluation : 0 = Mode temps, 1 = Mode distance
					System.out.println("En Mode Temps");
					arcInspectorDijkstra = ArcInspectorFactory.getAllFilters().get(2);
				} else {
					System.out.println("En Mode Distance");
					arcInspectorDijkstra = ArcInspectorFactory.getAllFilters().get(0);
				}
				
				System.out.println("Chemin de la carte : "+mapName);
				System.out.println("Origine : " + origine);
				System.out.println("Destination : " + destination);
				
				if(origine==destination) {
					System.out.println("Origine et Destination identiques");
					System.out.println("Cout = 0");
					
				} else {			
					ShortestPathData data = new ShortestPathData(graph, graph.get(origine),graph.get(destination), arcInspectorDijkstra);
		
					BellmanFordAlgorithm B = new BellmanFordAlgorithm(data);
					DijkstraAlgorithm D = new DijkstraAlgorithm(data);
					
					// Recovery of Bellman and Dijkstra solutions to compare
					ShortestPathSolution solution = D.run();
					ShortestPathSolution expected = B.run();
	
					
					if (solution.getPath() == null) {
						assertEquals(expected.getPath(), solution.getPath());
						System.out.println("PAS DE SOLUTION");
						System.out.println("(infini) ");
					}
					else {
						double costSolution;
						double costExpected;
						if(typeEvaluation == 0) { //Temps
							costSolution = solution.getPath().getMinimumTravelTime();
							costExpected = expected.getPath().getMinimumTravelTime();
						} else {
							costSolution = solution.getPath().getLength();
							costExpected = expected.getPath().getLength();
						}
						assertEquals(costExpected, costSolution, 0.001);
						System.out.println("Cout solution: " + costSolution);
					}
				}
			}
		}
		System.out.println();
		System.out.println();
	}


	@Test
	public void testScenarioSansOracle(String mapName, int origine, int destination) throws Exception {

		double costFastestTime = Double.POSITIVE_INFINITY;
		double costFastestDistance = Double.POSITIVE_INFINITY;
		double costShortestTime = Double.POSITIVE_INFINITY;
		double costShortestDistance = Double.POSITIVE_INFINITY;

		// Create a graph reader.
		GraphReader reader = new BinaryGraphReader(
				new DataInputStream(new BufferedInputStream(new FileInputStream(mapName))));

		// Read the graph.
		Graph graph = reader.read();
		
		// out of the graph or nonexistent vertices
		if (origine<0 || destination<0 || origine >=(graph.size()-1) || destination>=(graph.size()-1)) { 
			System.out.println("ERREUR : Paramètres invalides ");			
		} else {
			System.out.println("Origine : " + origine);
			System.out.println("Destination : " + destination);
			
			if(origine==destination) {
				System.out.println("Origine et Destination identiques");
				System.out.println("Tous les couts sont 0.");
				
			} else {
		
				/** Recherche du chemin le plus rapide **/
				ArcInspector arcInspectorDijkstra = ArcInspectorFactory.getAllFilters().get(2);
		
				ShortestPathData data = new ShortestPathData(graph, graph.get(origine),graph.get(destination), arcInspectorDijkstra);
		
				DijkstraAlgorithm D = new DijkstraAlgorithm(data);
		
				//Recovery of the Dijkstra solution
				ShortestPathSolution solution = D.run();
		
				/** Recherche du chemin le plus court **/
				
				arcInspectorDijkstra = ArcInspectorFactory.getAllFilters().get(0);
		
				data = new ShortestPathData(graph, graph.get(origine),graph.get(destination), arcInspectorDijkstra);
		
				D = new DijkstraAlgorithm(data);
		
				//Dijkstra solution
				solution = D.run();
				
				if (solution.getPath() == null) {
					System.out.println("PAS DE CHEMIN SOLUTION EN TEMPS");
				}else {
					
					//Calculation of the cost of the solution
					costFastestTime = solution.getPath().getMinimumTravelTime();
					costFastestDistance = solution.getPath().getLength();
				}
	
	
				if (solution.getPath() == null) {
					System.out.println("PAS DE CHEMIN SOLUTION EN DISTANCE");
				}
				else {				
					costShortestTime = solution.getPath().getMinimumTravelTime();
					costShortestDistance = solution.getPath().getLength();
				}
			
				//Checks that the time / distance of the fastest path is less than / greater than the time / distance of the shortest path
				assertTrue(costFastestTime <= costShortestTime);
				assertTrue(costFastestDistance >= costShortestDistance);
				
				System.out.println("Cost fastest path in time : " + costFastestTime);
				System.out.println("Cost shortest path in time  : " + costShortestTime);
				System.out.println("Cost fastest path in distance : " + costFastestDistance);
				System.out.println("Cost shortest path in distance  : " + costShortestDistance);
	
			}
		}
		System.out.println();
		System.out.println();
	}
}