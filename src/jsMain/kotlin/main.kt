import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import cave.Cave
import cave.CaveSearcher
import cave.CaveSearcherData
import cave.CaveSearcherState
import core.ComposeBirdGame
import core.Game
import data.GameFrame
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.coroutines.delay
import org.jetbrains.compose.web.attributes.InputType
import org.jetbrains.compose.web.attributes.disabled
import org.jetbrains.compose.web.css.*
import org.jetbrains.compose.web.css.keywords.CSSAutoKeyword
import org.jetbrains.compose.web.dom.*
import org.jetbrains.compose.web.renderComposable
import org.w3c.dom.HTMLElement
import org.w3c.dom.events.KeyboardEvent
import org.w3c.dom.get

private val caves: List<List<Cave>> =
    ("1163751742\n" +
            "1381373672\n" +
            "2136511328\n" +
            "3694931569\n" +
            "7463417111\n" +
            "1319128137\n" +
            "1359912421\n" +
            "3125421639\n" +
            "1293138521\n" +
            "2311944581").split(
        '\n'
    ).mapIndexed { y, value ->
        value.mapIndexed { x, charValue ->
            Cave(x, y, charValue.digitToInt())
        }
    }

fun main() {

    val caveSearcher: CaveSearcher = CaveSearcher(caves)
    renderComposable(rootElementId = "root") {
        LaunchedEffect(Unit) {
            while (true) {
                caveSearcher.step()
                delay(100)
            }
        }
        Div(
            attrs = {
                style {
                    property("text-align", "center")
                }
            }
        ) {
            caveSearcher.searchState.value.caves.forEachIndexed { y, list ->
                Div {
                    list.forEachIndexed { x, cave ->
                        val visited = !caveSearcher.searchState.value.unvisitedCaves.contains(cave)
                            println("done searching ${caveSearcher.searchState.value.unvisitedCaves}")
                        val backgroundColor = if (visited) Color.red else Color.black
                        Span({
                            style {
                                height(10.px)
                                width(10.px)
                                backgroundColor(backgroundColor)
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


@Composable
private fun Header(gameFrame: GameFrame) {
    // Game title
    H1 {
        Text(value = "🐦 Compose Bird!")
    }

    // Game score
    Text(value = "Your Score: ${gameFrame.score} || Top Score: ${ComposeBirdGame.TOTAL_TUBES}")
}

@Composable
private fun CaveSearcherResult(caveSearcherState: CaveSearcherData) {

}

@Composable
private fun GameResult(gameFrame: GameFrame) {
    // Game Status
    H2 {
        if (gameFrame.isGameWon) {
            Text("🚀 Won the game! 🚀")
        } else {
            // core.Game over
            Text("💀 Game Over 💀")
        }
    }

    // Try Again
    Button(
        attrs = {
            onClick {
                window.location.reload()
            }
        }
    ) {
        Text("Try Again!")
    }
}
