import androidx.compose.runtime.*
import cave.Cave
import cave.CaveSearcher
import kotlinx.coroutines.delay
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.dom.*
import org.jetbrains.compose.web.renderComposable

private val caves: List<List<Cave>> =
    ("11637517422274862853338597396444961841755517295286\n" +
            "13813736722492484783351359589446246169155735727126\n" +
            "21365113283247622439435873354154698446526571955763\n" +
            "36949315694715142671582625378269373648937148475914\n" +
            "74634171118574528222968563933317967414442817852555\n" +
            "13191281372421239248353234135946434524615754563572\n" +
            "13599124212461123532357223464346833457545794456865\n" +
            "31254216394236532741534764385264587549637569865174\n" +
            "12931385212314249632342535174345364628545647573965\n" +
            "23119445813422155692453326671356443778246755488935\n" +
            "22748628533385973964449618417555172952866628316397\n" +
            "24924847833513595894462461691557357271266846838237\n" +
            "32476224394358733541546984465265719557637682166874\n" +
            "47151426715826253782693736489371484759148259586125\n" +
            "85745282229685639333179674144428178525553928963666\n" +
            "24212392483532341359464345246157545635726865674683\n" +
            "24611235323572234643468334575457944568656815567976").split(
        '\n'
    ).mapIndexed { y, value ->
        value.mapIndexed { x, charValue ->
            Cave(x, y, charValue.digitToInt())
        }
    }
var delay = 10L
fun main() {

    val caveSearcher: CaveSearcher = CaveSearcher(caves)
    renderComposable(rootElementId = "root") {
        LaunchedEffect(Unit) {
            while (true) {
                caveSearcher.step()
                delay(delay)
            }
        }
        Div(
            attrs = {
                style {
                    property("text-align", "center")
                }
            }
        ) {
            val graphState by remember { caveSearcher.searchState }
            graphState.caves.forEachIndexed { y, list ->
                Div {
                    list.forEachIndexed { x, cave ->
                        val visited = !caveSearcher.searchState.value.unvisitedCaves.contains(cave)
                        val state = remember { mutableStateOf(true) }

                        Span({
                            style {
                                height(10.px)
                                width(10.px)
                                if (!caveSearcher.searchState.value.doneSearch) {
                                    val backgroundColor = if (visited) Color.red else Color.black
                                    backgroundColor(backgroundColor)
                                } else {
                                    state.value = !state.value
                                    if (!visited) {
                                        if (state.value) {
                                            backgroundColor(Color.blue)
                                        } else {
                                            backgroundColor(Color.white)
                                        }
                                    } else {
                                        backgroundColor(Color.red)
                                    }
                                    delay = 2000
                                }
                                display(DisplayStyle.InlineBlock)
                                margin(4.px)
                                borderRadius(5.px)
                            }
                        })

                    }
                }
            }

        }
    }
}
