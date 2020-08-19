package pieces

import Directions
import Players
import PossibleMove
import SQUARE_SIZE
import Square
import context
import convertCordToInt
import getSquareId
import org.w3c.dom.*
import squares
import kotlin.browser.window

class Pawn : Piece {

    override val playerType: Players
    override var existingMoves: ArrayList<PossibleMove>
    override var possibleMoves: ArrayList<String> = arrayListOf()
    override var blockedDirections: ArrayList<Directions> = arrayListOf()
    override val image: HTMLImageElement = window.document.createElement("img") as HTMLImageElement

    constructor(playerType: Players) {
        this.playerType = playerType
        when (playerType) {
            Players.BLACKS -> existingMoves = arrayListOf(
                PossibleMove(0, 1, Directions.N, MoveTypes.NORMAL_MOVE, { true }),
                PossibleMove(0, 2, Directions.N, MoveTypes.NORMAL_MOVE,
                    { yIdx -> yIdx == 2 }
                ),
                PossibleMove(1, 1, Directions.NE, MoveTypes.EAT_MOVE, { true }),
                PossibleMove(-1, 1, Directions.NW, MoveTypes.EAT_MOVE, { true })
            )
            Players.WHITES -> {
                existingMoves = arrayListOf(
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
        image.src = "./pieces-2D-icons/pawn_${playerType.color}.png"
        context?.drawImage(image, x + SQUARE_SIZE / 4, y + SQUARE_SIZE / 4, 30.0, 35.0)
    }

    override fun createPossibleMoves(x: Double, y: Double) {
        val xIdx: Int = convertCordToInt(x)
        val yIdx: Int = convertCordToInt(y)
        for (possibleMove: PossibleMove in existingMoves) {
            if (possibleMove.moveType == MoveTypes.NORMAL_MOVE) {
                if (!blockedDirections.contains(possibleMove.direction)) {
                    val possibleSquare: Square? =
                        squares[getSquareId(xIdx + possibleMove.xCord, yIdx + possibleMove.yCord)]
                    if (possibleSquare != null) {    // Checking if square exists
                        if (possibleSquare.hasPiece()) {    // Blocking further moves
                            blockedDirections.add(possibleMove.direction)
                        } else if (possibleMove.condition(yIdx)) {
                            possibleSquare.drawPossibleMove()
                            possibleMoves.add(possibleSquare.id)
                        }
                    }
                }
            } else if (possibleMove.moveType == MoveTypes.EAT_MOVE) {
                val possibleSquare = squares[getSquareId(xIdx + possibleMove.xCord, yIdx + possibleMove.yCord)]
                if (possibleSquare?.piece != null && possibleSquare.piece!!.playerType != playerType) {
                    possibleSquare.drawPossibleMove()
                    possibleMoves.add(possibleSquare.id)
                }
            }
        }
    }

    override fun clearPossibleMoves() {
        for (squareId: String in possibleMoves) {
            squares[squareId]!!.clearPossibleMove()
        }
        blockedDirections = arrayListOf()
    }

    override fun isThisMovePossible(squareId: String): Boolean = possibleMoves.contains(squareId)

}