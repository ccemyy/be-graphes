package org.insa.graphs.algorithm.shortestpath;

import org.insa.graphs.model.Arc;
import org.insa.graphs.model.Node;

public class Label implements Comparable<Label>{
	
	private Node node;
	private boolean mark;
	private float cost;
	private Node predecessor;
	private boolean inHeap;
	
	public Label(Node node) {
		//super();
		this.node = node;
		this.mark = false;
		this.cost = Float.POSITIVE_INFINITY;
		this.predecessor = null;
		this.inHeap = false;
	}
	public Node getNode() {
		return this.node;
	}

	public void setNode(Node node) {
		this.node = node;
	}

	public boolean isMark() {
		return this.mark;
	}

	public void setMark() {
		this.mark = true;
	}

	public float getCost() {
		return this.cost;
	}

	public void setCost(double d) {
		this.cost = (float) d;
	}

	public Node getPredecessor() {
		return this.predecessor;
	}

	public void setPredecessor(Node node2) {
		this.predecessor = node2;
	}

	public boolean isInHeap() {
		return this.inHeap;
	}
	public void setInHeap() {
		this.inHeap = true;
	}

	@Override 
	public String toString() {
		return ""+this.getCost();
	}
	
	@Override
	public int compareTo(Label other) {
		// TODO Auto-generated method stub
		int result;
		if (this.getCost() < other.getCost()) {
			result = -1;
		}
		else if (this.getCost() == other.getCost()) {
			result = 0;
		}
		else {
			result = 1;
		}
		return result;
	}

	
	
	
}
								