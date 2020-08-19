package pieces

import Directions
import Players
import PossibleMove
import org.w3c.dom.HTMLImageElement

interface Piece {
    val playerType: Players
    var existingMoves: ArrayList<PossibleMove>  // All the moves that a piece has
    var possibleMoves: ArrayList<String>    // Moves that a piece can do. Needs to be cleared after move
    var blockedDirections: ArrayList<Directions>    // Needs to be cleared after move
    val image: HTMLImageElement
    fun drawPiece(x: Double, y: Double)
    fun createPossibleMoves(x: Double, y: Double)
    fun clearPossibleMoves()
    fun isThisMovePossible(squareId: String): Boolean
}