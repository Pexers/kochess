import Pieces.Pawn
import org.w3c.dom.CanvasRenderingContext2D
import org.w3c.dom.HTMLCanvasElement
import kotlin.browser.document
import kotlin.browser.window

const val WHITE: String = "#d9d9d9"
const val BLACK: String = "#996633"
const val SQUARE_SIZE: Double = 60.0
val squares = hashMapOf<String, Square?>()
var context: CanvasRenderingContext2D? = null

fun main() {
    window.onload = {   // work after everything was loaded (DOM, media elements)
        context = initializeContext()
        var color: String = WHITE
        for (yIdx in 1..8) {
            for (xIdx in 1..8) {
                val sq = getSquare(xIdx, yIdx, color)
                sq.draw()
                squares[(96 + yIdx).toChar() + xIdx.toString()] = sq
                color = if (color === WHITE) BLACK; else WHITE
            }
            color = if (color === WHITE) BLACK; else WHITE
        }
        null
    }

    window.onmousedown = {
        if (squares.isNotEmpty()) {
            val xIdx: Int = (it.clientX / SQUARE_SIZE).toInt()
            val yIdx: Int = (it.clientY / SQUARE_SIZE).toInt()
            println("X: $xIdx   Y: $yIdx")
            val sq = squares[(96 + yIdx).toChar() + xIdx.toString()]
            sq?.draw()
        }
        true
    }

}

fun getSquare(xIdx: Int, yIdx: Int, color: String): Square {
    if (yIdx == 1 || yIdx == 8) {

    }
    if (yIdx == 2 || yIdx == 7) {
        return Square(SQUARE_SIZE * xIdx, SQUARE_SIZE * yIdx, color, Pawn())
    }
    return Square(SQUARE_SIZE * xIdx, SQUARE_SIZE * yIdx, color, null)
}

fun initializeContext(): CanvasRenderingContext2D {
    val canvas = document.createElement("canvas") as HTMLCanvasElement
    canvas.width = (SQUARE_SIZE * 10).toInt()
    canvas.height = (SQUARE_SIZE * 10).toInt()
    document.body?.appendChild(canvas)
    return canvas.getContext("2d") as CanvasRenderingContext2D
}

