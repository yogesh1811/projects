import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.yogesh.routing.NetworkMap;
import com.yogesh.routing.Station;
import com.yogesh.util.QueryHelper;
import com.yogesh.util.RoutingHelper;

import junit.framework.TestCase;

/**
 * 
 */

/**
 * @author Yogesh
 * 
 */
public class NetworkRoutingTest extends TestCase {

	@Autowired
	static RoutingHelper routingHelper;
	@Autowired
	static QueryHelper queryHelper;

	List<String> inputs = new ArrayList<String>();

	// assigning the values
	protected void setUp() {
		setupInputRoutes();
	}

	private void setupInputRoutes() {
		inputs.add("A->B:240");
		inputs.add("A->C:70");
		inputs.add("A->D:120");
		inputs.add("C->B:60");
		inputs.add("D->E:480");
		inputs.add("C->E:240");
		inputs.add("B->E:210");
		inputs.add("E->A:300");
	}

	public void testRouteABQuery() {
		try {
			NetworkMap baseNetworkMap = new NetworkMap();
			routingHelper.initNetworkMap(inputs, baseNetworkMap);
			double travelTimeAE = queryHelper.findRoute("A", "B",
					baseNetworkMap);
			assertEquals(130.0, travelTimeAE);
		} catch (Exception e) {
		}
	}

	public void testRouteAEQuery() {
		try {
			NetworkMap baseNetworkMap = new NetworkMap();
			routingHelper.initNetworkMap(inputs, baseNetworkMap);
			double travelTimeAE = queryHelper.findRoute("A", "E",
					baseNetworkMap);
			assertEquals(310.0, travelTimeAE);
		} catch (Exception e) {

		}
	}

	public void testNearByAQuery() {
		try {
			NetworkMap baseNetworkMap = new NetworkMap();
			routingHelper.initNetworkMap(inputs, baseNetworkMap);
			List<Station> nearByStations = queryHelper.findNearBy("A", 130,
					baseNetworkMap);
			assertEquals(3, nearByStations.size());
		} catch (Exception e) {

		}
	}

}
