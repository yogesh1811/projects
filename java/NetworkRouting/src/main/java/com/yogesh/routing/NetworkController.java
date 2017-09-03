package com.yogesh.routing;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.stereotype.Controller;

import com.yogesh.util.AppConfig;
import com.yogesh.util.QueryHelper;
import com.yogesh.util.RoutingHelper;

/**
 * @author Yogesh Sharma
 * 
 *         -- this is driver/main controller class, entry point to use
 *         application
 */

@Controller
public class NetworkController {

	/**
	 * This is driver/main controller class, entry point to use application
	 */
	public static void main(String[] args) {

		AbstractApplicationContext context = new AnnotationConfigApplicationContext(
				AppConfig.class);

		RoutingHelper routingHelper = (RoutingHelper) context
				.getBean("routingHelper");
		QueryHelper queryHelper = (QueryHelper) context.getBean("queryHelper");

		int numberOfStations = 0;
		Scanner in = new Scanner(System.in);
		NetworkMap baseNetworkMap = new NetworkMap();
		System.out.println("Enter number of stations...");

		try {
			numberOfStations = in.nextInt();
			in.nextLine();// just to discart '\n'
		} catch (InputMismatchException ife) {
			System.out.println("enter +ve integer number only");
		}
		List<String> inputs = new ArrayList<String>();
		for (int arr_i = 0; arr_i < numberOfStations; arr_i++) {
			inputs.add(in.next());
		}
		in.nextLine();// just to discart '\n'
		List<String> queries = new ArrayList<String>();
		for (int arr_i = 0; arr_i < 2; arr_i++) {
			queries.add(in.nextLine());

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
