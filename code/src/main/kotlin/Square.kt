import pieces.Piece
import kotlin.math.PI

class Square {
    val id: String
    val x: Double
    val y: Double
    var piece: Piece?
    val squareColor: String

    constructor(id: String, x: Double, y: Double, color: String, piece: Piece?) {
        this.id = id
        this.x = x
        this.y = y
        this.squareColor = color
        this.piece = piece
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

    fun drawSelection(){
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

    fun clearPossibleMove() {
        drawSquare()
        piece?.drawPiece(x, y)
    }

    fun changeState(toSelect: Boolean, squareClicked: Square) {
        if (toSelect) {
            drawSelection()
            piece!!.createPossibleMoves(x, y)
        } else {
            if (piece!!.isThisMovePossible(squareClicked.id)) {
                piece!!.clearPossibleMoves(x, y)
                squareClicked.piece = piece
                squareClicked.piece?.allowedMoves = arrayListOf()
                piece = null
                drawSquare()
                squareClicked.drawSquare()
            } else {
                drawSquare()
                piece!!.clearPossibleMoves(x, y)
            }
        }
    }

    fun hasPiece(): Boolean = piece != null

}