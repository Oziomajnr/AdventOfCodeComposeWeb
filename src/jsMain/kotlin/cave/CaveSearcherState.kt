package cave

data class CaveSearcherData(val caves: List<List<Cave>>, val unvisitedCaves: MutableSet<Cave>, val doneSearch: Boolean = false)