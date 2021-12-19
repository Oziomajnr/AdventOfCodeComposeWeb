package cave;

import androidx.compose.runtime.*
import com.soywiz.kds.PriorityQueue
import kotlinx.coroutines.delay


class CaveSearcher(private val caves: List<List<Cave>>) {
    private val bestPathTable = mutableMapOf<Cave, Pair<Int, Cave>>()

    private val unVisitedCaves = PriorityQueue<Cave> { a, b ->
        (bestPathTable[a]?.first ?: Int.MAX_VALUE).compareTo(
            bestPathTable[b]?.first ?: Int.MAX_VALUE
        )
    }.also {
        it.addAll(caves.flatten())
    }


    init {
        val firstCave = caves[0][0]
        connectCaves()
        bestPathTable[firstCave] = Pair(0, firstCave)
        unVisitedCaves.remove(firstCave)
        unVisitedCaves.add(firstCave)
    }

    val lastCave = caves[caves.lastIndex][caves.first().lastIndex]
    val firstCave = caves[0][0]

    val searchState = mutableStateOf(CaveSearcherData(caves, unVisitedCaves.toMutableSet()))

    private fun connectCaves() {
        caves.forEachIndexed { y, list ->
            list.forEachIndexed { x, xValue ->
                if (x - 1 >= 0) {
                    xValue.siblings.add(caves[y][x - 1])
                }
                if (x + 1 <= caves.first().lastIndex) {
                    xValue.siblings.add(caves[y][x + 1])
                }
                if (y + 1 <= caves.lastIndex) {
                    xValue.siblings.add(caves[y + 1][x])
                }
                if (y - 1 >= 0) {
                    xValue.siblings.add(caves[y - 1][x])
                }
            }
        }
    }

    fun solvePart1(cave: Cave) {
        val currentCaveBestPath = bestPathTable[cave]?.first ?: 0

        cave.siblings.forEach {
            val sibilingBestPathToStart = bestPathTable[it]?.first ?: Int.MAX_VALUE
            if (currentCaveBestPath + it.value <= sibilingBestPathToStart) {
                bestPathTable[it] = Pair(currentCaveBestPath + it.value, cave)
                //just remove and add again so that queue would be sorted
                unVisitedCaves.remove(it)
                unVisitedCaves.add(it)
            }
        }

    }

    fun step() {
        if (unVisitedCaves.isNotEmpty()) {
            solvePart1(unVisitedCaves.removeHead())
        }
        if (bestPathTable[lastCave] == null) {
            searchState.value = CaveSearcherData(caves, unVisitedCaves.toMutableSet())
        } else {
            searchState.value = CaveSearcherData(caves, getCavesInPath(), true)

        }
    }

    fun getCavesInPath(): MutableSet<Cave> {
        val cavesInPath = mutableSetOf<Cave>()
        println("step in the name of love")
        cavesInPath.add(lastCave)
        bestPathTable.remove(firstCave)
        var x = bestPathTable[lastCave]
        var sum = 0
        while (x != null) {
            sum += x.second.value
            println(x.second.value)
            cavesInPath.add(x.second)
            x = bestPathTable[x.second]
        }
        return cavesInPath
    }
}
