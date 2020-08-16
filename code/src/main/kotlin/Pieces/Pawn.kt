package Pieces

import CIRCLE_SIZE
import POSSIBLE_MOVE_COLOR
import SQUARE_SIZE
import Square
import context
import getSquareId
import squares
import kotlin.math.PI

class Pawn : Piece {

    var possibleMove1: Square? = null
    var possibleMove2: Square? = null

    override fun drawPiece(x: Double, y: Double) {
        context?.beginPath()
        context?.fillStyle = "#000000"
        context?.fillRect(
            x = x + SQUARE_SIZE / 4,
            y = y + SQUARE_SIZE / 4,
            w = 30.0,
            h = 30.0
        )
    }

    override fun drawPossibleMoves(x: Double, y: Double) {
        possibleMove1 = squares[getSquareId((x / SQUARE_SIZE).toInt(), (y / SQUARE_SIZE).toInt() - 1)]
        if (possibleMove1 != null && possibleMove1!!.piece == null) {
            context?.beginPath()
            context?.fillStyle = POSSIBLE_MOVE_COLOR
            context?.arc(
                possibleMove1!!.x + SQUARE_SIZE / 2,
                possibleMove1!!.y + SQUARE_SIZE / 2,
                CIRCLE_SIZE,
                0.0,
                PI * 2
            )
            context?.fill()
            possibleMove2 = squares[getSquareId((x / SQUARE_SIZE).toInt(), (y / SQUARE_SIZE).toInt() - 2)]
            if (possibleMove2 != null && possibleMove2!!.piece == null) {
                context?.fillStyle = POSSIBLE_MOVE_COLOR
                context?.arc(
                    possibleMove2!!.x + SQUARE_SIZE / 2,
                    possibleMove2!!.y + SQUARE_SIZE / 2,
                    CIRCLE_SIZE,
                    0.0,
                    PI * 2
                )
                context?.fill()
            }
        }
    }

    override fun clearPossibleMoves() {
        if (possibleMove1 != null) {
            context?.beginPath()
            context?.fillStyle = possibleMove1!!.color
            context?.fillRect(
                x = possibleMove1!!.x,
                y = possibleMove1!!.y,
                w = SQUARE_SIZE,
                h = SQUARE_SIZE
            )
            possibleMove1 = null
        }
        if (possibleMove2 != null) {
            context?.fillStyle = possibleMove2!!.color
            context?.fillRect(
                x = possibleMove2!!.x,
                y = possibleMove2!!.y,
                w = SQUARE_SIZE,
                h = SQUARE_SIZE
            )
            possibleMove2 = null
        }
    }

    override fun isThisMovePossible(squareClickedId: String): Boolean =
        possibleMove1!!.id == squareClickedId || possibleMove2!!.id == squareClickedId

}