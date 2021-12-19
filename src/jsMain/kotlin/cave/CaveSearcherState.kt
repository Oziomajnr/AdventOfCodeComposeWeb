package cave
import androidx.compose.runtime.State

data class CaveSearcherData(val caves: List<List<Cave>>, val unvisitedCaves: Set<Cave>, val doneSearching: Boolean = false)

data class CaveSearcherState(val state: State<CaveSearcherState>)