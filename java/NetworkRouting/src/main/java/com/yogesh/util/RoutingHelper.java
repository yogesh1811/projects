package com.yogesh.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jdk.nashorn.internal.runtime.regexp.RegExp;

import org.springframework.stereotype.Component;

import com.yogesh.routing.NetworkMap;
import com.yogesh.routing.Route;
import com.yogesh.routing.Station;

/**
 * @author Yogesh Sharma
 * 
 *         This is the helper class initialize networkmap and find distance and
 *         other helper methods
 * 
 */
@Component("routingHelper")
public class RoutingHelper {

	/**
	 * this method to initialize network take input from stdin and add routes to
	 * network map
	 * 
	 * @param inputs
	 *            - List string which represent route like
	 *            <source>-><destination>:traveltime
	 * @param networkMap
	 *            - base network map
	 * @throws Exception
	 *             - throw exceptions if any input validation fails or other
	 */
	public void initNetworkMap(List<String> inputs, NetworkMap networkMap)
			throws Exception {
		String source = null;
		String destination = null;
		double travelTime = 0;
		String ttStr = null;

		for (String input : inputs) {
			try {
				source = input.substring(0, input.indexOf("->"));
				if (!isValidStaion(source)) {
					throw new Exception(
							source
									+ " -- is invalid source, special characters not allowed");
				}
				destination = input.substring(input.indexOf("->") + 2,
						input.indexOf(":"));
				if (!isValidStaion(destination)) {
					throw new Exception(
							destination
									+ " -- is invalid destination, special characters not allowed");
				}
				ttStr = input.substring(input.indexOf(":") + 1, input.length());
				travelTime = Double.valueOf(ttStr);
				if (travelTime < 0) {
					throw new Exception(
							ttStr
									+ " -- is invalid travel time, travel time must be +ve number");
				}

			} catch (NumberFormatException ne) {
				throw new Exception(ttStr + " -- is invalid travel time");
			}
			networkMap.addRoute(source, destination, travelTime);
		}
	}

	private boolean isValidStaion(String station) {
		Pattern p = Pattern.compile("[a-zA-Z0-9]");
		return p.matcher(station).matches();
	}

	static NetworkMap findAllBestRouteForSource(Station source) {
		NetworkMap map = new NetworkMap();
		// Algo:
		// 1. Take the unvisited node with minimum weight.
		// 2. Visit all its neighbours.
		// 3. Update the distances for all the neighbours (In the Priority
		// Queue).
		// Repeat the process till all the connected nodes are visited.

		source.minDistance = 0;
		PriorityQueue<Station> queue = new PriorityQueue<Station>();
		queue.add(source);

		while (!queue.isEmpty()) {

			Station u = queue.poll();

			for (Route neighbour : u.neighbours) {
				Double newDist = u.minDistance + neighbour.travelTime;

				if (neighbour.target.minDistance > newDist) {
					// Remove the node from the queue to update the distance
					// value.
					queue.remove(neighbour.target);
					neighbour.target.minDistance = newDist;

					// Take the path visited till now and add the new node.s
					neighbour.target.path = new LinkedList<Station>(u.path);
					neighbour.target.path.add(u);

					// Reenter the node with new distance.
					queue.add(neighbour.target);
				}
			}
		}
		map.getStations().put(source.name, source);
		return map;

	}

	/**
	 * @param networkMap
	 * @param travelTime
	 */
	static List<Station> printNearByStations(NetworkMap networkMap,
			double travelTime) {
		List<Station> nearByStations = new ArrayList<Station>();
		if (networkMap.getStations() == null
				|| networkMap.getStations().isEmpty()) {
			System.out.println("Empty network map...");
		}
		List<Station> Stations = new LinkedList<Station>(networkMap
				.getStations().values());
		// Sort all station to have near one 1st
		Collections.sort(Stations);
		StringBuffer sb = new StringBuffer();
		for (Station station : Stations) {
			if (station.minDistance > 0 && station.minDistance <= travelTime) {
				sb.append(station + ": " + station.minDistance + ", ");
				nearByStations.add(station);
			}
		}
		System.out.println(sb.substring(0, sb.length() - 2));
		return nearByStations;
	}

	/**
	 * @param networkMap
	 * @param destination
	 */
	static double printRoute(NetworkMap networkMap, String destination) {
		if (networkMap.getStations() == null
				|| networkMap.getStations().isEmpty()) {
			System.out.println("Empty network map...");
		}
		for (Entry<String, Station> entry : networkMap.getStations().entrySet()) {
			if (entry.getValue().name.equals(destination)) {
				LinkedList<Station> path = entry.getValue().path;
				for (Station station : path) {
					System.out.print(station + " -> ");
				}
				System.out.print(entry.getValue() + ": "
						+ entry.getValue().minDistance + "\n");
				return entry.getValue().minDistance;
			}
		}

		return -1;
	}

	static void printNetworkMap(NetworkMap networkMap) {
		if (networkMap.getStations() == null
				|| networkMap.getStations().isEmpty()) {
			System.out.println("Empty network map...");
		}
		for (Entry<String, Station> s : networkMap.getStations().entrySet()) {
			LinkedList<Station> path = s.getValue().path;
			for (Station station : path) {
				System.out.print(station + " -> ");
			}
			System.out.print(s.getValue() + ": " + s.getValue().minDistance
					+ "\n");
		}
	}

}
