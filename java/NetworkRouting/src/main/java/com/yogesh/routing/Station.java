/**
 * 
 */
package com.yogesh.routing;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * @author Yogesh Sharma
 * 
 * 
 */

public class Station implements Comparable<Station> {
	public final String name;
	public ArrayList<Route> neighbours;
	public LinkedList<Station> path;
	public double minDistance = Double.POSITIVE_INFINITY;
	public Station previous;

	public int compareTo(Station other) {
		return Double.compare(minDistance, other.minDistance);
	}

	public Station(String name) {
		this.name = name;
		neighbours = new ArrayList<Route>();
		path = new LinkedList<Station>();
	}

	public String toString() {
		return name;
	}
}
