package pieces

import Directions
import Players
import PossibleMove

interface Piece {
    val playerType: Players
    var possibleMoves: ArrayList<PossibleMove>
    var allowedMoves: ArrayList<String>    // Needs to be cleared after move
    var blockedDirections: ArrayList<Directions>    // Needs to be cleared after move
    fun drawPiece(x: Double, y: Double)
    fun createPossibleMoves(x: Double, y: Double)
    fun clearPossibleMoves(x: Double, y: Double)
    fun isThisMovePossible(squareId: String): Boolean
}