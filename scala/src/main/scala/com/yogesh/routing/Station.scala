package com.yogesh.routing

import scala.collection.mutable.MutableList

case class Station(name: String) extends Ordered[Station] {
  var minDistance: Double = Double.MaxValue
  var neighbours: MutableList[Route] = MutableList[Route]()
  var path: MutableList[Station] = MutableList[Station]()

  import scala.math.Ordered.orderingToOrdered

  def compare(that: Station): Int = (this.minDistance, this.minDistance) compare (that.minDistance, that.minDistance)

}