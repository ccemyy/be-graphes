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
        Arc [] predecessorArc = new Arc[sizeGraph];
        
        Label start = new Label(data.getOrigin());
        tabLabel[start.getNode().getId()] = start;
        heap.insert(start);
        start.setInHeap();
        start.setCost(0);

		/* Tant qu'il existe des sommets non marqués */
		while(!heap.isEmpty() && !fin){      	

			Label current= heap.deleteMin();
			current.setMark();
			/* Quand on a atteint la destination, on s'arrête */
			if (current.getNode() == data.getDestination()) {
				fin = true;
			}
			/* Parcours des successeurs du sommet courant */
			Iterator<Arc> arc = current.getNode().getSuccessors().iterator();
			while (arc.hasNext()) {
				Arc arcIter = arc.next();

				// On vérifie que l'on peut réellement prendre cet arc
				if (!data.isAllowed(arcIter)) {
					continue;
				}

				Node successeur = arcIter.getDestination();

				/* On recupere le label correspondant au noeud dans le tableau de labels */
				Label successeurLabel = tabLabel[successeur.getId()];

				/* Si le label n'existe pas encore */
				/* Alors on le crée */
				if (successeurLabel == null) {
					successeurLabel = newLabel(successeur, data);
					tabLabel[successeurLabel.getNode().getId()] = successeurLabel;
					/* On incrémente le nombre de sommets visités pour le test de performance */
					this.nbPassNode++;
				}

				/* Si le successeur n'est pas encore marqué */
				if (!successeurLabel.isMark()) {
					/* Si on obtient un meilleur coût */
					/* Alors on le met à jour */

					if((successeurLabel.getCost()>(current.getCost()+data.getCost(arcIter)
						+(successeurLabel.getCost()-successeurLabel.getCost()))) 
						|| (successeurLabel.getCost()==Float.POSITIVE_INFINITY)){
						successeurLabel.setCost(current.getCost()+(float)data.getCost(arcIter));
						successeurLabel.setPredecessor(current.getNode());;
						/* Si le label est déjà dans le tas */
						/* Alors on met à jour sa position dans le tas */
						if(successeurLabel.isInHeap()) {
							heap.remove(successeurLabel);
						}
						/* Sinon on l'ajoute dans le tas */
						else {
							successeurLabel.setInHeap();
						}
						heap.insert(successeurLabel);
						predecessorArc[arcIter.getDestination().getId()] = arcIter;
					}
				}

			}
		}

		// Destination has no predecessor, the solution is infeasible...
		if (predecessorArc[data.getDestination().getId()] == null) {
			solution = new ShortestPathSolution(data, Status.INFEASIBLE);
		} else {

			// Create the path from the array of predecessors...
			ArrayList<Arc> arcs = new ArrayList<>();
			Arc arc = predecessorArc[data.getDestination().getId()];

			while (arc != null) {
				arcs.add(arc);
				arc = predecessorArc[arc.getOrigin().getId()];
			}

			// Reverse the path...
			Collections.reverse(arcs);

			// Create the final solution.
			solution = new ShortestPathSolution(data, Status.OPTIMAL, new Path(graph, arcs));

		}

		return solution;
	}

	/* Crée et retourne le Label correspondant au Node */
	protected Label newLabel(Node node, ShortestPathData data) {
		return new Label(node);
	}
	
	/* Retourne le nombre de sommets visités */
	public int getNbPassNode() {
		return this.nbPassNode;
	}

}

