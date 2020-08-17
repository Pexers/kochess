package pieces

import Players
import PossibleMove

interface Piece {
    val playerType: Players
    var possibleMoves: ArrayList<PossibleMove>
    fun drawPiece(x: Double, y: Double)
    fun drawPossibleMoves(x: Double, y: Double)
    fun clearPossibleMoves()
    fun isThisMovePossible(squareClickedId: String): Boolean
    fun isThisPieceWhite(): Boolean
}