import pieces.Pawn
import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.HTMLCanvasElement
import kotlin.browser.document
import kotlin.browser.window

val squares = hashMapOf<String, Square>()
var context: CanvasRenderingContext2D? = null
var squareSelectedId: String? = null
var currentPlayer: Players = Players.BLACKS

fun main() {
    window.onload = {   // work after everything was loaded (DOM, media elements)
        context = initializeContext()
        context?.beginPath()
        var color: String = LIGHT_SQUARE_COLOR
        for (yIdx in 1..8) {
            for (xIdx in 1..8) {
                val sq = getSquare(xIdx, yIdx, color)
                sq.draw()
                squares[sq.id] = sq
                color = if (color === LIGHT_SQUARE_COLOR) DARK_SQUARE_COLOR; else LIGHT_SQUARE_COLOR
            }
            color = if (color === LIGHT_SQUARE_COLOR) DARK_SQUARE_COLOR; else LIGHT_SQUARE_COLOR
        }
        null
    }

    window.onmousedown = {
        if (squares.isNotEmpty()) {
            val xIdx: Int = (it.clientX / SQUARE_SIZE).toInt()
            val yIdx: Int = (it.clientY / SQUARE_SIZE).toInt()
            println("X: $xIdx   Y: $yIdx")
            val squareClicked = squares[getSquareId(xIdx, yIdx)]
            if (squareClicked != null) {
                if (squareSelectedId == null) {
                    if (squareClicked.hasPiece()) {
                        squareClicked.changeState(true, squareClicked)
                        squareSelectedId = squareClicked.id
                    }
                } else {
                    val squareSelected = squares[squareSelectedId!!]
                    squareSelected?.changeState(false, squareClicked)
                    squareSelectedId = null
                }
            }
        }
        true
    }

}

fun getSquare(xIdx: Int, yIdx: Int, color: String): Square {
    if (yIdx == 1 || yIdx == 8) {

    }
    if (yIdx == 2 || yIdx == 7) {
        if (yIdx == 7)
            currentPlayer = Players.WHITES
        return Square(getSquareId(xIdx, yIdx), SQUARE_SIZE * xIdx, SQUARE_SIZE * yIdx, color, Pawn(currentPlayer))
    }
    return Square(getSquareId(xIdx, yIdx), SQUARE_SIZE * xIdx, SQUARE_SIZE * yIdx, color, null)
}

fun initializeContext(): CanvasRenderingContext2D {
    val canvas = document.createElement("canvas") as HTMLCanvasElement
    canvas.width = (SQUARE_SIZE * 10).toInt()
    canvas.height = (SQUARE_SIZE * 10).toInt()
    document.body?.appendChild(canvas)
    return canvas.getContext("2d") as CanvasRenderingContext2D
}

fun getSquareId(x: Int, y: Int): String {
    return (96 + y).toChar() + x.toString()
}

