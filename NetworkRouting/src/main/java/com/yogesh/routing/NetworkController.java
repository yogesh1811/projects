package com.yogesh.routing;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.yogesh.util.QueryHelper;
import com.yogesh.util.RoutingHelper;

/**
 * @author Yogesh Sharma
 * 
 *         -- this is driver/main controller class, entry point to use
 *         application
 */

public class NetworkController {
	
	@Autowired
	static RoutingHelper routingHelper;
	@Autowired
	static QueryHelper queryHelper;
	

	/**
	 * This is driver/main controller class, entry point to use application
	 */
	public static void main(String[] args) {
		int numberOfStations = 0;
		Scanner in = new Scanner(System.in);
		NetworkMap baseNetworkMap = new NetworkMap();
		System.out.println("Enter number of stations...");
		try {
			numberOfStations = in.nextInt();
		} catch (InputMismatchException ife) {
			System.out.println("enter +ve integer number only");
		}
		List<String> inputs = new ArrayList<String>();
		for (int arr_i = 0; arr_i < numberOfStations; arr_i++) {
			inputs.add(in.next());
		}
		List<String> queries = new ArrayList<String>();
		for (int arr_i = 0; arr_i < 2; arr_i++) {
			queries.add(in.next());
		}
		in.close();
		try {
			routingHelper.initNetworkMap(inputs, baseNetworkMap);
			queryHelper.executeQuery(queries, baseNetworkMap);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
		// RoutingHelper.printNetworkMap(baseNetworkMap);
	}

}
