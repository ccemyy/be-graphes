package org.insa.graphs.algorithm.shortestpath;
import org.insa.graphs.algorithm.AbstractInputData;
import org.insa.graphs.model.Node;
import org.insa.graphs.model.Point;

public class LabelStar extends Label implements Comparable<Label>{
	//getTotalCost
	private double estim_cost;

	public LabelStar(Node node, ShortestPathData data) {
		
		super(node);
		
		if(data.getMode() == AbstractInputData.Mode.LENGTH) {
			this.estim_cost = Point.distance(node.getPoint(), data.getDestination().getPoint());
		}else {
			int speed = Math.max(data.getMaximumSpeed(), data.getGraph().getGraphInformation().getMaximumSpeed());
			this.estim_cost = Point.distance(node.getPoint(), data.getDestination().getPoint())/speed*3.6;
		}	
	}
	
	@Override
	// Return origin cost to node + node cost to destination

	public float getTotalCost() {
		return (float) this.estim_cost + getCost();
	}
}
