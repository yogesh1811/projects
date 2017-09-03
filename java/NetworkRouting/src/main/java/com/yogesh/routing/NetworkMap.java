/**
 * 
 */
package com.yogesh.routing;

import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

/**
 * @author Yogesh
 * 
 *         -- This class is hold network information all station and routes
 * 
 */
@Component
public class NetworkMap {

	private Map<String, Station> stations;

	public Map<String, Station> getStations() {
		if (stations == null) {
			stations = new HashMap<String, Station>();
		}
		return stations;
	}

	public void setStations(Map<String, Station> stations) {
		this.stations = stations;
	}

	/**
	 * 
	 * Add route to network
	 * 
	 * @param source
	 *            - string source for route
	 * @param destination
	 *            - string destination for route
	 * @param travelTime
	 *            - double type - travel time in (seconds) to reach from source
	 *            to destination
	 */
	public void addRoute(String source, String destination, double travelTime) {
		Station src = getElseCreate(source);
		Station dest = getElseCreate(destination);
		Route route = new Route(dest, travelTime);
		src.neighbours.add(route);

	}

	/**
	 * this method will return station from network if not present it will crate
	 * and add in network and return the same.
	 * 
	 * @param stationKey
	 *            - string name of network
	 * @return Station
	 */
	public Station getElseCreate(String stationKey) {
		if (!this.getStations().containsKey(stationKey)) {
			Station station = new Station(stationKey);
			this.getStations().put(stationKey, station);
		}
		return this.getStations().get(stationKey);
	}

}
