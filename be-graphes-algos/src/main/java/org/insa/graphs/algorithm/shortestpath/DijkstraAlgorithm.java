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

    @Override
    protected ShortestPathSolution doRun() {
        final ShortestPathData data = getInputData();
        ShortestPathSolution solution = null;
        // TODO:
        boolean fin = false;
        Graph graph = data.getGraph();
        int sizeGraph = graph.size();
        
        Label [] tabLabel = new Label[sizeGraph];
        BinaryHeap<Label> heap = new BinaryHeap<Label>();
        Arc [] predecessorArcs = new Arc[sizeGraph];
        
        Label start = new Label(data.getOrigin());
        tabLabel[start.getNode().getId()] = start;
        heap.insert(start);
        start.setInHeap();
        start.setCost(0);

		// when there are unmarked nodes 
		while(!heap.isEmpty() && !fin){      	

			Label current= heap.deleteMin();
			current.setMark();
			// stop when we reach the destination
			if (current.getNode() == data.getDestination()) {
				fin = true;
			}
			// traversing successors of the current node 
			for(Arc arc : current.getNode().getSuccessors()) {
				// checking that the current arc can be taken
				if (!data.isAllowed(arc)) {
					continue;
				}

				Node successeur = arc.getDestination();

				// Recovering the label corresponding to the node in the label table
				Label successeurLabel = tabLabel[successeur.getId()];

				// If the label does not yet exist
				// Creating it
				if (successeurLabel == null) {
					successeurLabel = newLabel(successeur, data);
					tabLabel[successeurLabel.getNode().getId()] = successeurLabel;
					// incrementing the number of vertices visited for the performance test
					this.nbPassNode++;
				}

				// If the successor is not yet marked 
				if (!successeurLabel.isMark()) {
					// If we get a better cost
					// So updating it
					if((successeurLabel.getCost()>(current.getCost()+data.getCost(arc)
						+(successeurLabel.getCost()-successeurLabel.getCost()))) 
						|| (successeurLabel.getCost()==Float.POSITIVE_INFINITY)){
						successeurLabel.setCost(current.getCost()+(float)data.getCost(arc));
						successeurLabel.setPredecessor(current.getNode());;
						// If the label is already in the heap
						// So we update its position in the heap
						if(successeurLabel.isInHeap()) {
							heap.remove(successeurLabel);
						}
						// Otherwise we add it to the heap
						else {
							successeurLabel.setInHeap();
						}
						heap.insert(successeurLabel);
						predecessorArcs[arc.getDestination().getId()] = arc;
					}
				}

			}
		}

		// Destination has no predecessor, the solution is infeasible...
		if (predecessorArcs[data.getDestination().getId()] == null) {
			solution = new ShortestPathSolution(data, Status.INFEASIBLE);
		} else {

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

		return solution;
	}

	// Creating and return the Label corresponding to the Node
	protected Label newLabel(Node node, ShortestPathData data) {
		return new Label(node);
	}
	
	// Return the number of nodes visited
	public int getNbPassNode() {
		return this.nbPassNode;
	}

}

