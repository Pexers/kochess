import Pieces.Piece

class Square {
    var piece: Piece?
    var x: Double
    var y: Double
    private var color: String

    constructor(x: Double, y: Double, color: String, piece: Piece?) {
        this.x = x
        this.y = y
        this.color = color
        this.piece = piece
    }

    fun draw() {
        context?.fillStyle =
                if (color === WHITE) {
                    color = BLACK
                    BLACK
                } else {
                    color = WHITE
                    WHITE
                }
        context?.fillRect(
                x = x,
                y = y,
                w = SQUARE_SIZE,
                h = SQUARE_SIZE
        )
        piece?.draw(x, y)
    }
}