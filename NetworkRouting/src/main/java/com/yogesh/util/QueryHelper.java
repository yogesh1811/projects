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
@Component
public class QueryHelper {

	private static final String ROUTE = "route";
	private static final String NEARBY = "nearby";

	public static void executeQuery(List<String> queries, NetworkMap baseNetworkMap)
			throws Exception {
		for (String qry : queries) {
			if (!isValidQuery(qry)) {
				throw new Exception(
						"invalid query... query must contain route or nearby clauce");
			}
			if (qry.contains(ROUTE)) {
				String source = qry.substring(6, qry.indexOf("->"));
				String destination = qry.substring(qry.indexOf("->") + 2,
						qry.length());
				findRoute(source, destination, baseNetworkMap);
			}
			if (qry.contains(NEARBY)) {
				String source = qry.substring(7, qry.indexOf(","));
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

	public static double findRoute(String source, String destination,
			NetworkMap baseNetworkMap) {
		RoutingHelper.findAllBestRouteForSource(baseNetworkMap.getStations()
				.get(source));
		return RoutingHelper.printRoute(baseNetworkMap, destination);
	}

	public static List<Station> findNearBy(String source, double travelTime,
			NetworkMap baseNetworkMap) {
		RoutingHelper.findAllBestRouteForSource(baseNetworkMap.getStations()
				.get(source));
		return RoutingHelper.printNearByStations(baseNetworkMap, travelTime);
	}
}
