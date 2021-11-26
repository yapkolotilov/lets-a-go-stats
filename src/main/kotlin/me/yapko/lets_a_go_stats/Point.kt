package me.yapko.lets_a_go_stats

import org.joda.time.DateTime
import org.joda.time.Duration
import kotlin.math.*

/**
 * Точка маршрута.
 *
 * @param latitude Широта.
 * @param longitude Долгота.
 * @param altitude Высота.
 * @param timestamp Время прохождения точки.
 * @param id ID точки.
 */
data class Point(
    val latitude: Double,
    val longitude: Double,
    val altitude: Double,
    val timestamp: DateTime,
    val id: Int
) {

    /**
     * Расстояние до другой точки.
     *
     * @param other Другая точка.
     */
    infix fun distance(other: Point): Double {
        return distance(latitude, other.latitude, longitude, other.longitude)
    }

    infix fun same(other: Point): Boolean {
        return latitude == other.latitude && longitude == other.longitude && altitude == other.altitude
    }
}

/**
 * Длина маршрута.
 */
fun List<Point>.distance(): Double {
    var result = 0.0
    for (i in 0 until lastIndex)
        result += this[i] distance this[i + 1]
    return result
}

/**
 * Продолжительность маршрута.
 */
fun List<Point>.duration(): Duration {
    return Duration(firstOrNull()?.timestamp ?: DateTime.now(), lastOrNull()?.timestamp ?: DateTime.now())
}

/**
 * Средняя скорость.
 */
fun List<Point>.speed(): Double {
    return (distance() / 1000) / (duration().millis.toDouble() / (60 * 60 * 1000)) / 10
}

/**
 * Перепад высот.
 */
fun List<Point>.altitudeDelta(): Double {
    return maxOf { it.altitude } - minOf { it.altitude }
}

// https://stackoverflow.com/a/16794680
private fun distance(lat1: Double, lat2: Double, lon1: Double, lon2: Double): Double {
    val el1 = 0.0
    val el2 = 0.0
    val R = 6371 // Radius of the earth
    val latDistance = Math.toRadians(lat2 - lat1)
    val lonDistance = Math.toRadians(lon2 - lon1)
    val a = (sin(latDistance / 2) * sin(latDistance / 2)
            + (cos(Math.toRadians(lat1)) * cos(Math.toRadians(lat2))
            * sin(lonDistance / 2) * sin(lonDistance / 2)))
    val c = 2 * atan2(sqrt(a), sqrt(1 - a))
    var distance = R * c * 1000 // convert to meters
    val height = el1 - el2
    distance = distance.pow(2.0) + height.pow(2.0)
    return sqrt(distance)
}