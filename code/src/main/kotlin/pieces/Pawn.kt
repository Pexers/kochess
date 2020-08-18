package pieces

import Directions
import Players
import PossibleMove
import SQUARE_SIZE
import Square
import context
import convertCordDoubleToInt
import getSquareId
import squares

class Pawn : Piece {

    override val playerType: Players
    override var possibleMoves: ArrayList<PossibleMove>
    override var allowedMoves: ArrayList<String> = arrayListOf()
    override var blockedDirections: ArrayList<Directions> = arrayListOf()

    constructor(playerType: Players) {
        this.playerType = playerType
        when (playerType) {
            Players.BLACKS -> possibleMoves = arrayListOf(
                PossibleMove(0, 1, Directions.N, MoveTypes.NORMAL_MOVE, { true }),
                PossibleMove(0, 2, Directions.N, MoveTypes.NORMAL_MOVE,
                    { yIdx -> yIdx == 2 }
                ),
                PossibleMove(1, 1, Directions.NE, MoveTypes.EAT_MOVE, { true }),
                PossibleMove(-1, 1, Directions.NW, MoveTypes.EAT_MOVE, { true })
            )
            Players.WHITES -> {
                possibleMoves = arrayListOf(
                    PossibleMove(0, -1, Directions.S, MoveTypes.NORMAL_MOVE, { true }),
                    PossibleMove(0, -2, Directions.S, MoveTypes.NORMAL_MOVE,
                        { yIdx -> yIdx == 7 }
                    ),
                    PossibleMove(-1, -1, Directions.SW, MoveTypes.EAT_MOVE, { true }),
                    PossibleMove(1, -1, Directions.SE, MoveTypes.EAT_MOVE, { true })
                )
            }
        }
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

    override fun createPossibleMoves(x: Double, y: Double) {
        val xIdx: Int = convertCordDoubleToInt(x)
        val yIdx: Int = convertCordDoubleToInt(y)
        for (possibleMove: PossibleMove in possibleMoves) {
            if (possibleMove.moveType == MoveTypes.NORMAL_MOVE) {
                if (!blockedDirections.contains(possibleMove.direction)) {
                    val possibleSquare: Square? =
                        squares[getSquareId(xIdx + possibleMove.xCord, yIdx + possibleMove.yCord)]
                    if (possibleSquare != null) {    // Checking if square exists
                        if (possibleSquare.hasPiece()) {    // Blocking further moves
                            blockedDirections.add(possibleMove.direction)
                        } else if (possibleMove.condition(yIdx)) {
                            possibleSquare.drawPossibleMove()
                            allowedMoves.add(possibleSquare.id)
                        }
                    }
                }
            } else if (possibleMove.moveType == MoveTypes.EAT_MOVE) {
                val possibleSquare = squares[getSquareId(xIdx + possibleMove.xCord, yIdx + possibleMove.yCord)]
                if (possibleSquare?.piece != null && possibleSquare.piece!!.playerType != playerType) {
                    possibleSquare.drawPossibleMove()
                    allowedMoves.add(possibleSquare.id)
                }
            }
        }
    }

    override fun clearPossibleMoves(x: Double, y: Double) {
        val xIdx: Int = convertCordDoubleToInt(x)
        val yIdx: Int = convertCordDoubleToInt(y)
        for (possibleMove: PossibleMove in possibleMoves) {
            val possibleSquare: Square? = squares[getSquareId(xIdx + possibleMove.xCord, yIdx + possibleMove.yCord)]
            possibleSquare?.clearPossibleMove()
        }
        blockedDirections = arrayListOf()
    }

    override fun isThisMovePossible(squareId: String): Boolean = allowedMoves.contains(squareId)

}