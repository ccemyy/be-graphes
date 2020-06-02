package org.insa.graphs.algorithm.shortestpath;

import java.io.IOException;
import java.util.Arrays;

import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.RoadInformation;
import org.insa.graphs.model.RoadInformation.RoadType;
import org.junit.BeforeClass;
import org.junit.Test;

public class AStarAlgorithmTest {
	   // Small graph use for tests
    private static Graph graph;

    // List of nodes
    private static Node[] nodes;

    // List of arcs in the graph, a2b is the arc from node A (0) to B (1).
    @SuppressWarnings("unused")
    private static Arc a2b, a2c, a2e, b2c, c2d_1, c2d_2, c2d_3, c2a, d2a, d2e, e2d;

   /*// Some paths...
    private static Path emptyPath, singleNodePath, shortPath, longPath, loopPath, longLoopPath,
            invalidPath; */

    @BeforeClass
    public static void initAll() throws IOException {

        // Road
        RoadInformation RoadInfo = new RoadInformation(RoadType.UNCLASSIFIED, null, true, 1, "");

        // Create nodes
        nodes = new Node[5];
        for (int i = 0; i < nodes.length; ++i) {
            nodes[i] = new Node(i, null);
        }

        // Add arcs...
        a2b = Node.linkNodes(nodes[0], nodes[1], 10, RoadInfo, null);
        a2c = Node.linkNodes(nodes[0], nodes[2], 15, RoadInfo, null);
        a2e = Node.linkNodes(nodes[0], nodes[4], 15, RoadInfo, null);
        b2c = Node.linkNodes(nodes[1], nodes[2], 10, RoadInfo, null);
        c2d_1 = Node.linkNodes(nodes[2], nodes[3], 20, RoadInfo, null);
        c2d_2 = Node.linkNodes(nodes[2], nodes[3], 10, RoadInfo, null);
        c2d_3 = Node.linkNodes(nodes[2], nodes[3], 15, RoadInfo, null);
        d2a = Node.linkNodes(nodes[3], nodes[0], 15, RoadInfo, null);
        d2e = Node.linkNodes(nodes[3], nodes[4], 22.8f, RoadInfo, null);
        e2d = Node.linkNodes(nodes[4], nodes[0], 10, RoadInfo, null);

        graph = new Graph("ID", "", Arrays.asList(nodes), null);
    }

	@Test
	public void testDoScenarioDistanceHG() throws Exception {

		String mapName = "D:\\3MIC\\Semester2\\be-graphes\\be-graphes\\maps\\haute-garonne.mapgr";
		
		AStarMapTest test = new AStarMapTest();
		int origine;
		int destination;
		
		System.out.println("#####----- Test de validité avec oracle sur une carte-----######");
		System.out.println("#####----- Carte : Haute-Garonne -------------------------######");
		System.out.println("#####----- Mode : DISTANCE -------------------------------######");
		System.out.println();
		
		System.out.println("----- Cas d'un chemin nul ------");
		origine = 0 ;
		destination = 0;
		test.testScenario(mapName, 1,origine,destination);    
		
		System.out.println("----- Cas d'un chemin simple ------");
		origine = 99426;
		destination = 61702;
		test.testScenario(mapName, 1,origine,destination);    	
	
		System.out.println("----- Cas de sommets inexistants ------");
		
		System.out.println("----- Origine : N'existe pas; Destination : Existe ----------");
		origine = -1;
		destination = 61702;
		test.testScenario(mapName, 1,origine,destination);    	

		System.out.println("----- Origine : Existe; Destination : N'existe pas ----------------");
		origine = 99426;
		destination = 800000;
		test.testScenario(mapName, 1,origine,destination);    	
		
		System.out.println("----- Origine : N'existe pas; Destination : N'existe pas ----------");
		origine = -1;
		destination = 800000;
		test.testScenario(mapName, 1,origine,destination);    	
	}

	
	@Test
	public void testDoScenarioTempsHG() throws Exception {

		String mapName = "D:\\3MIC\\Semester2\\be-graphes\\be-graphes\\maps\\haute-garonne.mapgr";

		AStarMapTest test = new AStarMapTest();
		int origine;
		int destination;
		
		System.out.println("#####----- Test de validité avec oracle sur une carte-----######");
		System.out.println("#####----- Carte : Haute-Garonne -------------------------######");
		System.out.println("#####----- Mode : TEMPS ----------------------------------######");
		System.out.println();
		
		System.out.println("----- Cas d'un chemin nul ------");
		origine = 0 ;
		destination = 0;
		test.testScenario(mapName, 0,origine,destination);    
		
		System.out.println("----- Cas d'un chemin simple ------");
		origine = 99426;
		destination = 61702;
		test.testScenario(mapName, 0,origine,destination);    	
	
		System.out.println("----- Cas de sommets inexistants ------");
		
		System.out.println("----- Origine : N'existe pas; Destination : Existe ----------");
		origine = -1;
		destination = 61702;
		test.testScenario(mapName, 0,origine,destination);    	

		System.out.println("----- Origine : Existe; Destination : N'existe pas ----------------");
		origine = 99426;
		destination = 800000;
		test.testScenario(mapName, 0,origine,destination);    	
		
		System.out.println("----- Origine : N'existe pas; Destination : N'existe pas ----------");
		origine = -1;
		destination = 800000;
		test.testScenario(mapName, 0,origine,destination);    	
	}

	@Test
	public void testDoScenarioMinTempsDistHG() throws Exception {
		
		String mapName = "D:\\3MIC\\Semester2\\be-graphes\\be-graphes\\maps\\haute-garonne.mapgr";
		
		AStarMapTest test = new AStarMapTest();
		int origine;
		int destination;
		
		System.out.println("######### Test de validité sans oracle sur une carte############");
		System.out.println("#####----- Carte : Haute-Garonne -------------------------######");
		System.out.println();

		System.out.println("----- Cas d'un chemin nul ------");
		origine = 0 ;
		destination = 0;
		test.testScenarioSansOracle(mapName,origine,destination);   
		
		System.out.println("----- Cas d'un chemin simple ------");
		origine = 99426;
		destination = 61702;
		test.testScenarioSansOracle(mapName,origine,destination);    	
	
		
		System.out.println("----- Cas de sommets inexistants ------");
		
		System.out.println("----- Origine : N'existe pas; Destination : Existe ----------");
		origine = -1;
		destination = 61702;
		test.testScenarioSansOracle(mapName,origine,destination);   	

		System.out.println("----- Origine : Existe; Destination : N'existe pas ------");
		origine = 99426;
		destination = 800000;
		test.testScenarioSansOracle(mapName,origine,destination);    	
		
		System.out.println("----- Origine : N'existe pas; Destination : N'existe pas ------");
		origine = -1;
		destination = 800000; 
		test.testScenarioSansOracle(mapName,origine,destination);   
	}
	/*************************************************************************************************************/
	
	//@Test
	public void testDoScenarioDistanceTLS() throws Exception {

		String mapName = "D:\\3MIC\\Semester2\\be-graphes\\be-graphes\\maps\\toulouse.mapgr";
		
		AStarMapTest test = new AStarMapTest();
		int origine;
		int destination;
		
		System.out.println("#####----- Test de validité avec oracle sur une carte-----######");
		System.out.println("#####----- Carte : Toulouse -------------------------######");
		System.out.println("#####----- Mode : DISTANCE -------------------------------######");
		System.out.println();
		
		System.out.println("----- Cas d'un chemin nul ------");
		origine = 0 ;
		destination = 0;
		test.testScenario(mapName, 1,origine,destination);    
		
		System.out.println("----- Cas d'un chemin simple ------");
		origine = 1;
		destination = 50;
		test.testScenario(mapName, 1,origine,destination);    	
	
		System.out.println("----- Cas de sommets inexistants ------");
		
		System.out.println("----- Origine : N'existe pas; Destination : Existe ----------");
		origine = -1;
		destination = 50;
		test.testScenario(mapName, 1,origine,destination);    	

		System.out.println("----- Origine : Existe; Destination : N'existe pas ----------------");
		origine = 1;
		destination = 800000;
		test.testScenario(mapName, 1,origine,destination);    	
		
		System.out.println("----- Origine : N'existe pas; Destination : N'existe pas ----------");
		origine = -1;
		destination = 800000;
		test.testScenario(mapName, 1,origine,destination);    	
	}

	
	//@Test
	public void testDoScenarioTempsTLS() throws Exception {

		String mapName = "D:\\3MIC\\Semester2\\be-graphes\\be-graphes\\maps\\toulouse.mapgr";

		AStarMapTest test = new AStarMapTest();
		int origine;
		int destination;
		
		System.out.println("#####----- Test de validité avec oracle sur une carte-----######");
		System.out.println("#####----- Carte : Toulouse -------------------------######");
		System.out.println("#####----- Mode : TEMPS ----------------------------------######");
		System.out.println();
		
		System.out.println("----- Cas d'un chemin nul ------");
		origine = 0 ;
		destination = 0;
		test.testScenario(mapName, 0,origine,destination);    
		
		System.out.println("----- Cas d'un chemin simple ------");
		origine = 1;
		destination = 50;
		test.testScenario(mapName, 0,origine,destination);    	
	
		System.out.println("----- Cas de sommets inexistants ------");
		
		System.out.println("----- Origine : N'existe pas; Destination : Existe ----------");
		origine = -1;
		destination = 50;
		test.testScenario(mapName, 0,origine,destination);    	

		System.out.println("----- Origine : Existe; Destination : N'existe pas ----------------");
		origine = 1;
		destination = 50;
		test.testScenario(mapName, 0,origine,destination);    	
		
		System.out.println("----- Origine : N'existe pas; Destination : N'existe pas ----------");
		origine = -1;
		destination = 800000;
		test.testScenario(mapName, 0,origine,destination);    	
	}


	//@Test
	public void testDoScenarioMinTempsDistTLS() throws Exception {
		
		String mapName = "D:\\3MIC\\Semester2\\be-graphes\\be-graphes\\maps\\toulouse.mapgr";
		
		AStarMapTest test = new AStarMapTest();
		int origine;
		int destination;
		
		System.out.println("######### Test de validité sans oracle sur une carte############");
		System.out.println("#####----- Carte : Toulouse -------------------------######");
		System.out.println();

		System.out.println("----- Cas d'un chemin nul ------");
		origine = 0 ;
		destination = 0;
		test.testScenarioSansOracle(mapName,origine,destination);   
		
		System.out.println("----- Cas d'un chemin simple ------");
		origine = 1;
		destination = 50;
		test.testScenarioSansOracle(mapName,origine,destination);    	
	
		
		System.out.println("----- Cas de sommets inexistants ------");
		
		System.out.println("----- Origine : N'existe pas; Destination : Existe ----------");
		origine = -1;
		destination = 50;
		test.testScenarioSansOracle(mapName,origine,destination);   	

		System.out.println("----- Origine : Existe; Destination : N'existe pas ------");
		origine = 1;
		destination = 800000;
		test.testScenarioSansOracle(mapName,origine,destination);    	
		
		System.out.println("----- Origine : N'existe pas; Destination : N'existe pas ------");
		origine = -1;
		destination = 800000; 
		test.testScenarioSansOracle(mapName,origine,destination);   
	}
	
	/******************************************************************************************************/
	

	//@Test
	public void testDoScenarioDistanceCarre() throws Exception {

		String mapName = "D:\\3MIC\\Semester2\\be-graphes\\be-graphes\\maps\\carre.mapgr";
		
		AStarMapTest test = new AStarMapTest();
		int origine;
		int destination;
		
		System.out.println("#####----- Test de validité avec oracle sur une carte-----######");
		System.out.println("#####----- Carte : Carré -------------------------######");
		System.out.println("#####----- Mode : DISTANCE -------------------------------######");
		System.out.println();
		
		System.out.println("----- Cas d'un chemin nul ------");
		origine = 0 ;
		destination = 0;
		test.testScenario(mapName, 1,origine,destination);    
		
		System.out.println("----- Cas d'un chemin simple ------");
		origine = 1;
		destination = 9;
		test.testScenario(mapName, 1,origine,destination);    	
	
		System.out.println("----- Cas de sommets inexistants ------");
		
		System.out.println("----- Origine : N'existe pas; Destination : Existe ----------");
		origine = -1;
		destination = 9;
		test.testScenario(mapName, 1,origine,destination);    	

		System.out.println("----- Origine : Existe; Destination : N'existe pas ----------------");
		origine = 1;
		destination = 800000;
		test.testScenario(mapName, 1,origine,destination);    	
		
		System.out.println("----- Origine : N'existe pas; Destination : N'existe pas ----------");
		origine = -1;
		destination = 800000;
		test.testScenario(mapName, 1,origine,destination);    	
	}

	
	//@Test
	public void testDoScenarioTempsCarre() throws Exception {

		String mapName = "D:\\3MIC\\Semester2\\be-graphes\\be-graphes\\maps\\carre.mapgr";

		AStarMapTest test = new AStarMapTest();
		int origine;
		int destination;
		
		System.out.println("#####----- Test de validité avec oracle sur une carte-----######");
		System.out.println("#####----- Carte : Carré -------------------------######");
		System.out.println("#####----- Mode : TEMPS ----------------------------------######");
		System.out.println();
		
		System.out.println("----- Cas d'un chemin nul ------");
		origine = 0 ;
		destination = 0;
		test.testScenario(mapName, 0,origine,destination);    
		
		System.out.println("----- Cas d'un chemin simple ------");
		origine = 1;
		destination = 9;
		test.testScenario(mapName, 0,origine,destination);    	
	
		System.out.println("----- Cas de sommets inexistants ------");
		
		System.out.println("----- Origine : N'existe pas; Destination : Existe ----------");
		origine = -1;
		destination = 9;
		test.testScenario(mapName, 0,origine,destination);    	

		System.out.println("----- Origine : Existe; Destination : N'existe pas ----------------");
		origine = 1;
		destination = 9;
		test.testScenario(mapName, 0,origine,destination);    	
		
		System.out.println("----- Origine : N'existe pas; Destination : N'existe pas ----------");
		origine = -1;
		destination = 800000;
		test.testScenario(mapName, 0,origine,destination);    	
	}


	//@Test
	public void testDoScenarioMinTempsDistCarre() throws Exception {
		
		String mapName = "D:\\3MIC\\Semester2\\be-graphes\\be-graphes\\maps\\carre.mapgr";
		
		AStarMapTest test = new AStarMapTest();
		int origine;
		int destination;
		
		System.out.println("######### Test de validité sans oracle sur une carte############");
		System.out.println("#####----- Carte : Toulouse -------------------------######");
		System.out.println();

		System.out.println("----- Cas d'un chemin nul ------");
		origine = 0 ;
		destination = 0;
		test.testScenarioSansOracle(mapName,origine,destination);   
		
		System.out.println("----- Cas d'un chemin simple ------");
		origine = 1;
		destination = 9;
		test.testScenarioSansOracle(mapName,origine,destination);    	
	
		
		System.out.println("----- Cas de sommets inexistants ------");
		
		System.out.println("----- Origine : N'existe pas; Destination : Existe ----------");
		origine = -1;
		destination = 9;
		test.testScenarioSansOracle(mapName,origine,destination);   	

		System.out.println("----- Origine : Existe; Destination : N'existe pas ------");
		origine = 1;
		destination = 800000;
		test.testScenarioSansOracle(mapName,origine,destination);    	
		
		System.out.println("----- Origine : N'existe pas; Destination : N'existe pas ------");
		origine = -1;
		destination = 800000; 
		test.testScenarioSansOracle(mapName,origine,destination);   
	}
	
	/******************************************************************************************************/
	

	//@Test
	public void testDoScenarioDistanceBelg() throws Exception {

		String mapName = "D:\\3MIC\\Semester2\\be-graphes\\be-graphes\\maps\\belgium.mapgr";
		
		AStarMapTest test = new AStarMapTest();
		int origine;
		int destination;
		
		System.out.println("#####----- Test de validité avec oracle sur une carte-----######");
		System.out.println("#####----- Carte : Belgique -------------------------######");
		System.out.println("#####----- Mode : DISTANCE -------------------------------######");
		System.out.println();
		
		System.out.println("----- Cas d'un chemin nul ------");
		origine = 0 ;
		destination = 0;
		test.testScenario(mapName, 1,origine,destination);    
		
		System.out.println("----- Cas d'un chemin simple ------");
		origine = 587270;
		destination = 878482;
		test.testScenario(mapName, 1,origine,destination);    	
	
		System.out.println("----- Cas de sommets inexistants ------");
		
		System.out.println("----- Origine : N'existe pas; Destination : Existe ----------");
		origine = -1;
		destination = 878482;
		test.testScenario(mapName, 1,origine,destination);    	

		System.out.println("----- Origine : Existe; Destination : N'existe pas ----------------");
		origine = 587270;
		destination = 80000000;
		test.testScenario(mapName, 1,origine,destination);    	
		
		System.out.println("----- Origine : N'existe pas; Destination : N'existe pas ----------");
		origine = -1;
		destination = 80000000;
		test.testScenario(mapName, 1,origine,destination);    	
	}

	
	//@Test
	public void testDoScenarioTempsBelg() throws Exception {

		String mapName = "D:\\3MIC\\Semester2\\be-graphes\\be-graphes\\maps\\belgium.mapgr";

		AStarMapTest test = new AStarMapTest();
		int origine;
		int destination;
		
		System.out.println("#####----- Test de validité avec oracle sur une carte-----######");
		System.out.println("#####----- Carte : Belgique -------------------------######");
		System.out.println("#####----- Mode : TEMPS ----------------------------------######");
		System.out.println();
		
		System.out.println("----- Cas d'un chemin nul ------");
		origine = 0 ;
		destination = 0;
		test.testScenario(mapName, 0,origine,destination);    
		
		System.out.println("----- Cas d'un chemin simple ------");
		origine = 587270;
		destination = 878482;
		test.testScenario(mapName, 0,origine,destination);    	
	
		System.out.println("----- Cas de sommets inexistants ------");
		
		System.out.println("----- Origine : N'existe pas; Destination : Existe ----------");
		origine = -1;
		destination = 878482;
		test.testScenario(mapName, 0,origine,destination);    	

		System.out.println("----- Origine : Existe; Destination : N'existe pas ----------------");
		origine = 587270;
		destination = 878482;
		test.testScenario(mapName, 0,origine,destination);    	
		
		System.out.println("----- Origine : N'existe pas; Destination : N'existe pas ----------");
		origine = -1;
		destination = 80000000;
		test.testScenario(mapName, 0,origine,destination);    	
	}


	//@Test
	public void testDoScenarioMinTempsDistBelg() throws Exception {
		
		String mapName = "D:\\3MIC\\Semester2\\be-graphes\\be-graphes\\maps\\belgium.mapgr";
		
		AStarMapTest test = new AStarMapTest();
		int origine;
		int destination;
		
		System.out.println("######### Test de validité sans oracle sur une carte############");
		System.out.println("#####----- Carte : Belgique -------------------------######");
		System.out.println();

		System.out.println("----- Cas d'un chemin nul ------");
		origine = 0 ;
		destination = 0;
		test.testScenarioSansOracle(mapName,origine,destination);   
		
		System.out.println("----- Cas d'un chemin simple ------");
		origine = 587270;
		destination = 9;
		test.testScenarioSansOracle(mapName,origine,destination);    	
	
		
		System.out.println("----- Cas de sommets inexistants ------");
		
		System.out.println("----- Origine : N'existe pas; Destination : Existe ----------");
		origine = -1;
		destination = 9;
		test.testScenarioSansOracle(mapName,origine,destination);   	

		System.out.println("----- Origine : Existe; Destination : N'existe pas ------");
		origine = 587270;
		destination = 80000000;
		test.testScenarioSansOracle(mapName,origine,destination);    	
		
		System.out.println("----- Origine : N'existe pas; Destination : N'existe pas ------");
		origine = -1;
		destination = 80000000; 
		test.testScenarioSansOracle(mapName,origine,destination);   
	}
}
