import pieces.Piece
import kotlin.math.PI

class Square {
    val id: String
    val x: Double
    val y: Double
    val squareColor: String
    var piece: Piece? = null

    constructor(id: String, x: Double, y: Double, color: String) {
        this.id = id
        this.x = x
        this.y = y
        this.squareColor = color
    }

    fun setPiece(piece: Piece) {
        this.piece = piece
    }

    fun selectSquare() {
        drawSelection()
        piece!!.createPossibleMoves(x, y)
    }

    fun clearPossibleMove() {
        drawSquare()
        piece?.drawPiece(x, y)
    }

    fun tryToMovePiece(squareClicked: Square) { // Only called if square is selected and has a piece
        if (piece!!.isThisMovePossible(squareClicked.id)) {
            piece!!.clearPossibleMoves()
            squareClicked.piece = piece
            squareClicked.piece?.possibleMoves = arrayListOf()
            piece = null
            drawSquare()
            squareClicked.drawSquare()
            currentPlayer = if (currentPlayer == Players.WHITES) Players.BLACKS else Players.WHITES
        } else {
            drawSquare()
            piece!!.clearPossibleMoves()
        }
    }

    fun drawSquare() {
        context?.beginPath()
        context?.fillStyle = squareColor
        context?.fillRect(
            x = x,
            y = y,
            w = SQUARE_SIZE,
            h = SQUARE_SIZE
        )
        piece?.drawPiece(x, y)
    }

    private fun drawSelection() {
        context?.beginPath()
        context?.fillStyle = SELECTED_COLOR
        context?.fillRect(
            x = x,
            y = y,
            w = SQUARE_SIZE,
            h = SQUARE_SIZE
        )
        piece?.drawPiece(x, y)
    }

    fun drawPossibleMove() {
        context?.beginPath()
        context?.fillStyle = POSSIBLE_MOVE_COLOR
        context?.arc(
            x + SQUARE_SIZE / 2,
            y + SQUARE_SIZE / 2,
            CIRCLE_SIZE,
            0.0,
            PI * 2
        )
        context?.fill()
    }

    fun hasPiece(): Boolean = piece != null

}