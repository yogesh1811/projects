package com.yogesh.routing

import scala.collection.mutable.Map

class NetworkMap {
  var stations: Map[String, Station] = collection.mutable.Map[String, Station]()

  def addRoute(source: String, destination: String, travelTime: Double): Unit = {
    val from = getElseCreate(source)
    val to = getElseCreate(destination)
    val route = Route(to, travelTime)
    from.neighbours.+=(route)
  }
  def getElseCreate(station: String): Station = {
    if (!stations.contains(station)) {
      stations.put(station, Station(station))
    }
    // print(stations)
    this.stations.get(station).get
  }

}