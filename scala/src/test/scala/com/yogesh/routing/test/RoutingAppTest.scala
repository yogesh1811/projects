package com.yogesh.routing.test

import org.scalatest.FlatSpec
import com.yogesh.routing.util.RoutingHelper
import com.yogesh.routing.util.QueryHelper
import com.yogesh.routing.NetworkMap

class RoutingAppTest extends FlatSpec {

  val rHelper = new RoutingHelper()
  val qhelper = new QueryHelper()

  "Route from A->B" must "Give shortest route that is 130" in {
    val routes = List[String]("A->B:240", "A->C:70", "C->B:60")
    var map = new NetworkMap()

    rHelper.initNetworkMap(routes.toList, map)
    assert(qhelper.findRoute("A", "B", map) == 130)

  }

  "Near by A " must " Give 3 station for 100 travel time" in {
    val rHelper = new RoutingHelper()
    val qhelper = new QueryHelper()
    val routes = List[String]("A->B:240", "A->C:70", "C->B:60", "C->D:10")

    var map = new NetworkMap()
    rHelper.initNetworkMap(routes.toList, map)
    assert(qhelper.findNearBy("A", 100, map).size == 2)

  }

  "Route from A->Z" must " return -1 as no route from A->Z" in {
    val rHelper = new RoutingHelper()
    val qhelper = new QueryHelper()
    val routes = List[String]()

    var map = new NetworkMap()
    rHelper.initNetworkMap(routes.toList, map)
    assert(qhelper.findRoute("A", "Z", map) == -1)

  }

  "Initialisation map test" should "throw Exception if station special charecter" in {
    val rHelper = new RoutingHelper()
    val qhelper = new QueryHelper()
    val routes = List[String]("A->B@#$:240")

    var map = new NetworkMap()
    intercept[Exception] {
      rHelper.initNetworkMap(routes.toList, map)
      // Set.empty.head
    }
  }

  "Travel time for route " should "throw Exception if travel time is -ve" in {
    val rHelper = new RoutingHelper()
    val qhelper = new QueryHelper()
    val routes = List[String]("A->B:-240")

    var map = new NetworkMap()
    intercept[Exception] {
      rHelper.initNetworkMap(routes.toList, map)
      // Set.empty.head
    }
  }

}