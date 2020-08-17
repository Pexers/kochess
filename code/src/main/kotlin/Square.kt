import pieces.Piece

class Square {
    val id: String
    val x: Double
    val y: Double
    val color: String
    var piece: Piece?

    constructor(id: String, x: Double, y: Double, color: String, piece: Piece?) {
        this.id = id
        this.x = x
        this.y = y
        this.color = color
        this.piece = piece
    }

    fun draw() {
        context?.fillStyle = color
        context?.fillRect(
            x = x,
            y = y,
            w = SQUARE_SIZE,
            h = SQUARE_SIZE
        )
        piece?.drawPiece(x, y)
    }

    fun changeState(toSelect: Boolean, squareClicked: Square) {
        context?.beginPath()
        context?.fillStyle = if (toSelect) SELECTED_COLOR else color
        context?.fillRect(
            x = x,
            y = y,
            w = SQUARE_SIZE,
            h = SQUARE_SIZE
        )
        if (toSelect) {
            piece!!.drawPiece(x, y)
            piece!!.drawPossibleMoves(x, y)
        } else {
            if (piece!!.isThisMovePossible(squareClicked.id)) {
                piece!!.clearPossibleMoves()
                piece!!.drawPiece(squareClicked.x, squareClicked.y)
                squareClicked.piece = piece
                piece = null
            } else {
                piece!!.drawPiece(x, y)
                piece!!.clearPossibleMoves()
            }
        }
    }

    fun hasPiece(): Boolean = piece != null

}