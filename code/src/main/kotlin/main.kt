import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.HTMLCanvasElement
import pieces.*
import kotlin.browser.document
import kotlin.browser.window

val squares = hashMapOf<String, Square>()
var context: CanvasRenderingContext2D? = null
var squareSelectedId: String? = null
var currentPlayer: Players = Players.BLACKS

fun main() {
    window.onload = {   // work after everything was loaded (DOM, media elements)
        context = initializeContext()
        var color: String = LIGHT_SQUARE_COLOR
        for (yIdx in 1..8) {
            for (xIdx in 1..8) {
                createSquare(xIdx, yIdx, color)
                color = if (color === LIGHT_SQUARE_COLOR) DARK_SQUARE_COLOR else LIGHT_SQUARE_COLOR
            }
            color = if (color === LIGHT_SQUARE_COLOR) DARK_SQUARE_COLOR else LIGHT_SQUARE_COLOR
        }
        null
    }

    window.onmousedown = {
        if (squares.isNotEmpty()) {
            val xIdx: Int = ((it.clientX / SQUARE_SIZE) - (POSITION_X / SQUARE_SIZE)).toInt()
            val yIdx: Int = (it.clientY / SQUARE_SIZE).toInt()
            val squareClicked = squares[getSquareId(xIdx, yIdx)]
            if (squareClicked != null) {    // Checking if square exists
                if (squareSelectedId == null) {
                    if (squareClicked.hasPiece() && squareClicked.piece!!.playerType == currentPlayer) {
                        squareClicked.selectSquare()
                        squareSelectedId = squareClicked.id
                    }
                } else {
                    val squareSelected = squares[squareSelectedId!!]
                    squareSelected?.tryToMovePiece(squareClicked)
                    squareSelectedId = null
                }
            }
        }
        true
    }
}

fun createSquare(xIdx: Int, yIdx: Int, color: String) {
    val square =
        Square(getSquareId(xIdx, yIdx), SQUARE_SIZE * xIdx + POSITION_X, SQUARE_SIZE * yIdx + POSITION_Y, color)
    when (yIdx) {
        1, 8 -> {   // First row
            when (xIdx) {
                1, 8 -> square.setPiece(Tower(currentPlayer))
                2, 7 -> square.setPiece(Horse(currentPlayer))
                3, 6 -> square.setPiece(Bishop(currentPlayer))
                4 -> square.setPiece(Queen(currentPlayer))
                5 -> square.setPiece(King(currentPlayer))
            }
        }
        2, 7 -> {   // Second/Pawns row
            if (yIdx == 7)
                currentPlayer = Players.WHITES
            square.setPiece(Pawn(currentPlayer))
        }
    }
    square.drawSquare()
    squares[square.id] = square
}

fun initializeContext(): CanvasRenderingContext2D {
    val canvas = document.createElement("canvas") as HTMLCanvasElement
    canvas.width = window.innerWidth - 50
    canvas.height = window.innerHeight - 50
    document.body?.appendChild(canvas)
    return canvas.getContext("2d") as CanvasRenderingContext2D
}

fun getSquareId(x: Int, y: Int): String {
    return (96 + y).toChar() + x.toString()
}

fun convertXCordToInt(cord: Double): Int = ((cord / SQUARE_SIZE) - (POSITION_X / SQUARE_SIZE)).toInt()

fun convertYCordToInt(cord: Double): Int = ((cord / SQUARE_SIZE) - (POSITION_Y / SQUARE_SIZE)).toInt()
