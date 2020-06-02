package org.insa.graphs.algorithm.shortestpath;
import org.insa.graphs.algorithm.AbstractSolution.Status;
import org.insa.graphs.algorithm.utils.BinaryHeap;
import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Graph;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Path;

import java.util.*;

public class DijkstraAlgorithm extends ShortestPathAlgorithm {

	private int nbPassNode;
	
    public DijkstraAlgorithm(ShortestPathData data) {
        super(data);
        this.nbPassNode = 0;
    }
    
	public void setNbPassNode(int nbPassNode) {
		this.nbPassNode = nbPassNode;
	}

	public int getNbPassNode() {
		return this.nbPassNode;
	}

    @Override
    protected ShortestPathSolution doRun() {
        final ShortestPathData data = getInputData();
        ShortestPathSolution solution = null;
        // TODO:
        boolean fin = false;
        Graph graph = data.getGraph();
        int nbNodes = graph.size();
        
        Label [] labels = new Label[nbNodes];
        Label start = newLabel(data.getOrigin(),data);
        labels[start.getNode().getId()] = start;
        BinaryHeap<Label> heap = new BinaryHeap<Label>();
        heap.insert(start);
        start.setInHeap();
        start.setCost(0);
        
        // Notify observers about the first event (origin processed).
        notifyOriginProcessed(data.getOrigin());
        
        Arc [] predecessorArcs = new Arc[nbNodes];
      
        //get start time
        long startTime=System.currentTimeMillis();   

		while(!heap.isEmpty() && !fin){      	

			Label current= heap.deleteMin();
			// observer of node is marked
			notifyNodeMarked(current.getNode());

			current.setMark();
			
			/*if(!heap.isValid()) {
				System.out.println("tas non valide");
			}else {
				System.out.println("tas valide");
			}*/
			
			// traversing successors of the current node 
			for(Arc arc : current.getNode().getSuccessors()) {
				// checking that the current arc can be taken
				if (!data.isAllowed(arc)) {
					continue;
				}
				// stop when we reach the destination
				if (current.getNode() == data.getDestination()) {
					fin = true;
				}
				Node successor = arc.getDestination();

				// Recovering the label corresponding to the node in the label table
				Label sucLab = labels[successor.getId()];

				// If the label does not yet exist, creating it
				if (sucLab == null) {
					sucLab = newLabel(successor, data);
					labels[sucLab.getNode().getId()] = sucLab;
					this.nbPassNode++;
				}

				// If the successor is not yet marked 
				if (!sucLab.isMark()) {
					// If we get a better cost, updating it
					if((sucLab.getCost()>(current.getCost()+data.getCost(arc)+(sucLab.getCost()-sucLab.getCost()))) || (sucLab.getCost()==Float.POSITIVE_INFINITY)){
						sucLab.setCost(current.getCost()+(float)data.getCost(arc));
						sucLab.setPredecessor(current.getNode());;
						// If the label is already in the heap
						// So we update its position in the heap
						if(sucLab.isInHeap()) {
							heap.remove(sucLab);
						}
						// Otherwise we add it to the heap
						else {
							sucLab.setInHeap();
						}
						heap.insert(sucLab);
						predecessorArcs[arc.getDestination().getId()] = arc;
					}
				}
			}
			/*// show performance
			System.out.println("cout label marqué : " + current.getCost());
			System.out.println("nb successeurs testés : " + current.getNode().getSuccessors().size());
			System.out.println("taille tas : " + heap.size());*/
		}

        
		// Destination has no predecessor, the solution is infeasible...
		if (predecessorArcs[data.getDestination().getId()] == null) {
			solution = new ShortestPathSolution(data, Status.INFEASIBLE);
		} else {

			// The destination has been found, notify the observes
			notifyDestinationReached(data.getDestination());
			
			// Create the path from the array of predecessors...
			ArrayList<Arc> arcs = new ArrayList<>();
			Arc arc = predecessorArcs[data.getDestination().getId()];

			while (arc != null) {
				arcs.add(arc);
				arc = predecessorArcs[arc.getOrigin().getId()];
			}

			// Reverse the path...
			Collections.reverse(arcs);

			// Create the final solution.
			solution = new ShortestPathSolution(data, Status.OPTIMAL, new Path(graph, arcs));

		}
		//get end time
        long endTime=System.currentTimeMillis(); 
        //System.out.println("execution time： "+(endTime-startTime)+"ms");
		return solution;
	}

	// Creating and return the Label for Dijkstra
	protected Label newLabel(Node node, ShortestPathData data) {
		return new Label(node);
	}
	

}

