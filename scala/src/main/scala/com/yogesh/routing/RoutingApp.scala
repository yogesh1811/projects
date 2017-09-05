package com.yogesh.routing

import scala.collection.mutable.MutableList
import scala.collection.mutable.ListBuffer
import com.yogesh.routing.util.RoutingHelper
import com.yogesh.routing.util.QueryHelper
import scala.util.control.Exception

object RoutingApp extends App {
  val in = scala.io.StdIn
  val n = in.readInt()
  val input = ListBuffer[String]()
  for (i <- 1 to n) {
    input += in.readLine()
  }
  val queries = ListBuffer[String]()
  for (i <- 1 to 2) {
    queries += in.readLine()
  }

  var map = new NetworkMap()

  val qhelper = new QueryHelper()
  val rHelper = new RoutingHelper()

  rHelper.initNetworkMap(input.toList, map)
  qhelper.executeQuery(queries.toList, map)

}