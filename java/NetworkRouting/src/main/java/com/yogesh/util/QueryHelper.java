package com.yogesh.util;

import java.util.List;

import org.springframework.stereotype.Component;

import com.yogesh.routing.NetworkMap;
import com.yogesh.routing.Station;

/**
 * @author Yogesh Sharma
 * 
 *         This is the helper class to build and execute query for given
 *         network.
 * 
 */
@Component("queryHelper")
public class QueryHelper {

	private final String ROUTE = "route";
	private final String NEARBY = "nearby";

	public void executeQuery(List<String> queries, NetworkMap baseNetworkMap)
			throws Exception {
		for (String qry : queries) {
			qry = qry.replaceAll("\\s", "");
			if (!isValidQuery(qry)) {
				throw new Exception(
						"invalid query... query must contain route or nearby clauce");
			}
			if (qry.contains(ROUTE)) {
				String source = qry.substring(5, qry.indexOf("->"));
				String destination = qry.substring(qry.indexOf("->") + 2,
						qry.length());
				if (findRoute(source, destination, baseNetworkMap) == -1) {
					System.out.println("No route found from " + source + " -> "
							+ destination);
				}
			}
			if (qry.contains(NEARBY)) {

				String source = qry.substring(6, qry.indexOf(","));
				double travelTime = Double.valueOf(qry.substring(
						qry.indexOf(",") + 1, qry.length()));
				findNearBy(source, travelTime, baseNetworkMap);
			}
		}
	}

	private static boolean isValidQuery(String qry) {
		if (qry == null)
			return false;
		if (!(qry.contains("route") || qry.contains("nearby"))) {
			return false;
		}
		if (qry.contains("route") && !qry.contains("->")) {
			return false;
		}
		return true;
	}

	public double findRoute(String source, String destination,
			NetworkMap baseNetworkMap) {
		RoutingHelper.findAllBestRouteForSource(baseNetworkMap.getStations()
				.get(source));
		double minTime = RoutingHelper.printRoute(baseNetworkMap, destination);
		if (minTime == -1) {
			System.out
					.println("Error: No route from " + source + " -> " + destination);
		}
		return minTime;
	}

	public List<Station> findNearBy(String source, double travelTime,
			NetworkMap baseNetworkMap) {
		RoutingHelper.findAllBestRouteForSource(baseNetworkMap.getStations()
				.get(source));
		return RoutingHelper.printNearByStations(baseNetworkMap, travelTime);
	}
}
