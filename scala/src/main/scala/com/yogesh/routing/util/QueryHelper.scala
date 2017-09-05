package com.yogesh.routing.util

import com.yogesh.routing.NetworkMap
import com.yogesh.routing.Station
import scala.collection.immutable.List
import scala.collection.mutable.MutableList

class QueryHelper {

  val ROUTE = "route"
  val NEARBY = "nearby"

  /**
   * method to build and execute list of query
   * @param queries - List of query
   * @param baseNetworkMap - base network map
   */
  def executeQuery(queries: List[String], baseNetworkMap: NetworkMap): Unit = {

    queries foreach { qry =>
      {
        if (!isValidQuery(qry)) {
          throw new Exception(
            "invalid query... query must contain route or nearby clauce")
        }
        if (qry.contains(ROUTE)) {
          val source = qry.substring(5, qry.indexOf("->"))
          val destination = qry.substring(qry.indexOf("->") + 2, qry.length())
          findRoute(source, destination, baseNetworkMap)
        }

        if (qry.contains(NEARBY)) {
          val source = qry.substring(6, qry.indexOf(","))
          val travelTime = qry.substring(qry.indexOf(",") + 1, qry.length())
          findNearBy(source, travelTime.toDouble, baseNetworkMap)
        }

      }
    }

  }

  def isValidQuery(qry: String): Boolean = {
    var ret = true
    if (qry == null)
      ret = false
    if (!(qry.contains("route") || qry.contains("nearby"))) {
      ret = false
    }
    if (qry.contains("route") && !qry.contains("->")) {
      ret = false
    }
    ret
  }

  /**
   *
   * Method to find shortest routes from source and print result for given destination
   *
   * @param source - given source station
   * @param destination - given destination
   * @param baseNetworkMap - base network map
   * @return - distance OR -1 if path not found
   */
  def findRoute(source: String, destination: String, baseNetworkMap: NetworkMap): Double = {
    if (!baseNetworkMap.stations.contains(source)) {
      print("Error: Not found source " + source)
      return -1
    }
    RoutingHelper.createRoutingHelper().findAllBestRouteForSource(baseNetworkMap.stations.get(source).get)
    val minTime = RoutingHelper.createRoutingHelper().printRoute(baseNetworkMap, destination)
    if (minTime == -1) {
      print("Error: No route from " + source + " -> " + destination)
    }
    minTime
  }

  /**
   * Method to find near by stations from given source and given travel time
   *
   * @param source - source station
   * @param travelTime - travel time
   * @param baseNetworkMap  - base network map
   * @return list of station that can reach within given travel time OR empty list if no reachable stations
   */
  def findNearBy(source: String, travelTime: Double, baseNetworkMap: NetworkMap): MutableList[Station] = {
    if (!baseNetworkMap.stations.contains(source)) {
      print("Error: Not found source " + source)
      return MutableList.empty
    }
    RoutingHelper.createRoutingHelper().findAllBestRouteForSource(baseNetworkMap.stations.get(source).get)

    RoutingHelper.createRoutingHelper().printNearByStations(baseNetworkMap, travelTime)

  }

}

object QueryHelper {
  implicit def createQueryHelper(): QueryHelper = {
    new QueryHelper()
  }
}