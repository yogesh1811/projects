package com.yogesh.routing.util

import com.yogesh.routing.NetworkMap
import java.util.regex.Pattern
import com.yogesh.routing.Station

import java.util.PriorityQueue
import scala.collection.mutable.LinkedList
import scala.collection.immutable.ListMap
import scala.collection.mutable.MutableList

class RoutingHelper {

  // this can be implemented to cache results for searched results for given source
  var mapCache = collection.mutable.Map[String, NetworkMap]()

  /**
   * method is initialize networkmap with given input routes
   * @param input
   * @param networkMap
   */
  def initNetworkMap(input: List[String], networkMap: NetworkMap): Unit = {

    if (input.size < 1) {
      throw new Exception("input routes list can not be empty")
    }

    for (inputStr <- input) {
      try {
        val source = inputStr.substring(0, inputStr.indexOf("->"))
        isValidStaion(source)
        val destination = inputStr.substring(inputStr.indexOf("->") + 2, inputStr.indexOf(":"))
        isValidStaion(destination)
        val ttStr = inputStr.substring(inputStr.indexOf(":") + 1, inputStr.length())
        val travelTime = ttStr.toDouble
        if (travelTime < 0) {
          throw new Exception("travel time must be +ve number")
        }
        networkMap.addRoute(source, destination, travelTime)
      } catch {
        case t: Throwable =>
          t.printStackTrace()
          throw t;
      }

    }

  }

  /**
   * validation for not include special characters
   * @param station
   */
  def isValidStaion(station: String): Unit = {
    if (!Pattern.compile("[a-zA-Z0-9]").matcher(station).matches()) {
      throw new Exception(station + "is not valid")
    }
  }

  def printNetworkMap(networkMap: NetworkMap): Unit = {

    if (networkMap.stations == null || networkMap.stations.isEmpty) {
      print("Empty network map...")
    }
    print("\n")
    networkMap.stations foreach (x => {
      x._2.path foreach { p => println(" " + p.name + "->") }
      println(x._2.name + x._2.minDistance)
    })
  }

  /**
   * method to find best shortest path for given source
   *
   * @param source - Source station
   */
  def findAllBestRouteForSource(source: Station): Unit = {

    source.minDistance = 0

    var queue = new PriorityQueue[Station]()

    queue.add(source)

    while (!queue.isEmpty) {

      val u = queue.poll

      u.neighbours foreach { neighbour =>
        {
          val newDist = u.minDistance + neighbour.travelTime

          if (neighbour.target.minDistance > newDist) {
            queue remove neighbour.target
            neighbour.target.minDistance = newDist
            // Take the path visited till now and add the new node.s
            neighbour.target.path += u
            // Reenter the node with new distance.
            queue.add(neighbour.target)
          }

        }
      }
    }
  }

  /**
   * method to print shortest path for given destination station
   * @param networkMap - network map with calculated shortest path from source
   * @param destination - destination station where want to go
   * @return  - time travel if find path OR -1 if no path found
   */
  def printRoute(networkMap: NetworkMap, destination: String): Double = {
    var retVal = -1.0
    if (networkMap.stations == null || networkMap.stations.isEmpty) {
      print("Empty network map...")
    }
    // filter network map for given destination
    val filterMap = networkMap.stations.filterKeys { _ == destination }

    filterMap foreach (x => {
      x._2.path foreach { p => { print(" " + p.name + "->") } }
      print(x._2.name + ":" + x._2.minDistance)
      retVal = x._2.minDistance
    })

    retVal
  }

  /**
   * print nearby station for source and given time tavel limit
   *
   * @param networkMap - networkMap with shorted route calculated
   * @param travelTime - time travel limit
   * @return - list of stations where we can reach in given time travel
   */
  def printNearByStations(networkMap: NetworkMap, travelTime: Double): MutableList[Station] = {
    if (networkMap.stations == null || networkMap.stations.isEmpty) {
      print("Empty network map...")
    }

    val filterMap = networkMap.stations.filter { case (k, v) => v.minDistance <= travelTime && v.minDistance != 0 }
    var stationList = new MutableList[Station]()

    if (!filterMap.isEmpty) {
      val sortedMap = ListMap(filterMap.toSeq.sortBy(_._2): _*)

      var allNearby = new java.lang.StringBuilder

      sortedMap foreach { item =>
        {
          allNearby.append(item._2.name + ":" + item._2.minDistance + ",")
          stationList.+=(item._2)
        }
      }
      println("\n" + allNearby.toString().substring(0, allNearby.toString().length() - 1))
    }
    stationList
  }

}

object RoutingHelper {
  implicit def createRoutingHelper(): RoutingHelper = {
    new RoutingHelper()
  }
}