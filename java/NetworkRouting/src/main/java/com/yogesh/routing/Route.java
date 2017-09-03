/**
 * 
 */
package com.yogesh.routing;

/**
 * @author Yogesh
 * 
 *         -- Route class to hold any route to reach target station and travel
 *         time
 * 
 */
public class Route {

	public final Station target;
	public final double travelTime;

	public Route(Station target, double travelTime) {
		this.target = target;
		this.travelTime = travelTime;
	}

}
