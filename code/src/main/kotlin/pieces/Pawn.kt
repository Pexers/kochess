package pieces

import CIRCLE_SIZE
import POSSIBLE_MOVE_COLOR
import Players
import PossibleMove
import SQUARE_SIZE
import Square
import context
import getSquareId
import squares
import kotlin.math.PI

class Pawn : Piece {

    override val playerType: Players
    override var possibleMoves: ArrayList<PossibleMove> = ArrayList()

    constructor(playerType: Players) {
        this.playerType = playerType
    }


    override fun drawPiece(x: Double, y: Double) {
        context?.beginPath()
        context?.fillStyle = playerType.color
        context?.fillRect(
            x = x + SQUARE_SIZE / 4,
            y = y + SQUARE_SIZE / 4,
            w = 30.0,
            h = 30.0
        )
    }

    override fun drawPossibleMoves(x: Double, y: Double) {
        val xIdx: Int = (x / SQUARE_SIZE).toInt()
        val yIdx: Int = (y / SQUARE_SIZE).toInt()
        val posOrNeg = if (playerType == Players.WHITES) 1 else -1
        val possibleMovesToEvaluate: Array<PossibleMove> = arrayOf(
            PossibleMove(squares[getSquareId(xIdx, yIdx - (1 * posOrNeg))], MoveTypes.NORMAL_MOVE),
            PossibleMove(
                if ((yIdx == 2 && !isThisPieceWhite()) || (yIdx == 7 && isThisPieceWhite()))
                    squares[getSquareId(xIdx, yIdx - (2 * posOrNeg))]
                else null
                , MoveTypes.NORMAL_MOVE
            ),
            PossibleMove(squares[getSquareId(xIdx + 1, yIdx - (1 * posOrNeg))], MoveTypes.EAT_MOVE),
            PossibleMove(squares[getSquareId(xIdx - 1, yIdx - (1 * posOrNeg))], MoveTypes.EAT_MOVE)
        )
        for (move: PossibleMove in possibleMovesToEvaluate) {
            if (move.moveType == MoveTypes.NORMAL_MOVE) {
                if (move.square != null && !move.square.hasPiece()) {
                    possibleMoves.add(move)
                }
            } else if (move.moveType == MoveTypes.EAT_MOVE
                && move.square != null
                && move.square.hasPiece()
                && (playerType != move.square.piece!!.playerType)
            ) {
                possibleMoves.add(move)
            }
        }
        for (possibleMove: PossibleMove in possibleMoves) {
            context?.beginPath()
            context?.fillStyle = POSSIBLE_MOVE_COLOR
            context?.arc(
                possibleMove.square!!.x + SQUARE_SIZE / 2,
                possibleMove.square.y + SQUARE_SIZE / 2,
                CIRCLE_SIZE,
                0.0,
                PI * 2
            )
            context?.fill()
        }
    }

    override fun clearPossibleMoves() {
        var possibleSquare: Square
        for (possibleMove: PossibleMove in possibleMoves) {
            context?.beginPath()
            possibleSquare = possibleMove.square!!
            context?.fillStyle = possibleSquare.color
            context?.fillRect(
                x = possibleSquare.x,
                y = possibleSquare.y,
                w = SQUARE_SIZE,
                h = SQUARE_SIZE
            )
            if (possibleSquare.hasPiece())
                possibleSquare.piece!!.drawPiece(possibleSquare.x, possibleSquare.y)
        }
        possibleMoves = arrayListOf<PossibleMove>()
    }

    override fun isThisMovePossible(squareClickedId: String): Boolean {
        for (possibleMove: PossibleMove in possibleMoves) {
            if (possibleMove.square?.id == squareClickedId)
                return true
        }
        return false
    }

    override fun isThisPieceWhite(): Boolean = playerType == Players.WHITES

}