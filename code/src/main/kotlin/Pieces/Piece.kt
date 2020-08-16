package Pieces

interface Piece {
    fun drawPiece(x: Double, y: Double)
    fun drawPossibleMoves(x: Double, y: Double)
    fun clearPossibleMoves()
    fun isThisMovePossible(squareClickedId: String): Boolean
}