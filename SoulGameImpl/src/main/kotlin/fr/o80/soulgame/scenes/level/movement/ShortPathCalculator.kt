package fr.o80.soulgame.scenes.level.movement

import fr.o80.soulgame.scenes.level.entity.Entity
import fr.o80.soulgame.scenes.level.level.Level
import fr.o80.soulgame.scenes.level.level.Point
import java.util.*

class ShortPathCalculator(
    private val level: Level
) {
    fun shortPath(entity: Entity, origin: Point, destination: Point): List<PathNode> {
        val start = PathNode(origin.x, origin.y, 0, 0, null)
        val done = mutableListOf<PathNode>()
        val todo = PriorityQueue<PathNode>(PathNodeComparator)

        todo.offer(start)
        while (todo.isNotEmpty()) {
            val currentNode = todo.poll()
            if (currentNode.x == destination.x && currentNode.y == destination.y) {
                return path(currentNode)
            }

            for (i in arrayOf(-1, 1)) {
                val nextX = currentNode.x + i
                val nextY = currentNode.y
                if (level.canGo(entity, nextX, nextY)) {
                    if (done.doesntContain(nextX, nextY)) {
                        val nextNodeFromTodo = todo.get(nextX, nextY)

                        if (nextNodeFromTodo == null) {
                            val newNode = buildNextPathNode(currentNode, destination, nextX, nextY)
                            todo.offer(newNode)
                        } else if (nextNodeFromTodo.cost > currentNode.cost + 1) {
                            val newNode = buildNextPathNode(currentNode, destination, nextX, nextY)
                            todo.remove(nextNodeFromTodo)
                            todo.offer(newNode)
                        }
                    }
                }
            }

            for (i in arrayOf(-1, 1)) {
                val nextX = currentNode.x
                val nextY = currentNode.y + i
                if (level.canGo(entity, nextX, nextY)) {
                    if (done.doesntContain(nextX, nextY)) {
                        val nextNodeFromTodo = todo.get(nextX, nextY)

                        if (nextNodeFromTodo == null) {
                            val newNode = buildNextPathNode(currentNode, destination, nextX, nextY)
                            todo.offer(newNode)
                        } else if (nextNodeFromTodo.cost > currentNode.cost + 1) {
                            val newNode = buildNextPathNode(currentNode, destination, nextX, nextY)
                            todo.remove(nextNodeFromTodo)
                            todo.offer(newNode)
                        }
                    }
                }
            }

            done.add(currentNode)
        }

        return emptyList()
    }

    private fun path(lastNode: PathNode): List<PathNode> {
        return LinkedList<PathNode>().apply {
            add(lastNode)

            var node: PathNode = lastNode
            while (node.previous != null) {
                add(0, node.previous!!)
                node = node.previous!!
            }
        }
    }

    private fun buildNextPathNode(
        currentNode: PathNode,
        destination: Point,
        nextX: Int,
        nextY: Int
    ): PathNode {
        val newCost = currentNode.cost + 1
        val interest = newCost + destination.distanceFrom(nextX, nextY)
        return PathNode(nextX, nextY, newCost, interest, currentNode)
    }
}

private fun PriorityQueue<PathNode>.get(x: Int, y: Int): PathNode? = find { it.x == x && it.y == y }

private fun MutableList<PathNode>.doesntContain(x: Int, y: Int): Boolean = none { it.x == x && it.y == y }

object PathNodeComparator : Comparator<PathNode> {
    override fun compare(a: PathNode, b: PathNode): Int {
        return a.estimatedTotalCost - b.estimatedTotalCost
    }
}

data class PathNode(
    val x: Int,
    val y: Int,
    val cost: Int,
    val estimatedTotalCost: Int,
    val previous: PathNode?
)
