package Pieces

import SQUARE_SIZE
import context

class Pawn : Piece {
    override fun draw(x: Double, y: Double) {
        context?.fillStyle = "#000000"
        context?.fillRect(
                x = x + SQUARE_SIZE / 4,
                y = y + SQUARE_SIZE / 4,
                w = 30.0,
                h = 30.0
        )
    }
}