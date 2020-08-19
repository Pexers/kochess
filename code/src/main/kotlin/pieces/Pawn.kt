package pieces

import Directions
import PIECE_WIDTH
import Players
import Move
import SQUARE_SIZE
import Square
import context
import getSquareId
import org.w3c.dom.*
import squares
import kotlin.browser.window

class Pawn : Piece {

    override val playerType: Players
    override var existingMoves: ArrayList<Move>
    override var possibleMoves: ArrayList<String> = arrayListOf()
    override var blockedDirections: ArrayList<Directions> = arrayListOf()
    override val image: HTMLImageElement = window.document.createElement("img") as HTMLImageElement

    constructor(playerType: Players) {
        this.playerType = playerType
        when (playerType) {
            Players.BLACKS -> existingMoves = arrayListOf(
                Move(0, 1, Directions.S, MoveTypes.NORMAL_MOVE, { true }),
                Move(0, 2, Directions.S, MoveTypes.NORMAL_MOVE,
                    { yIdx -> yIdx == 2 }
                ),
                Move(1, 1, Directions.SE, MoveTypes.EAT_MOVE, { true }),
                Move(-1, 1, Directions.SW, MoveTypes.EAT_MOVE, { true })
            )
            Players.WHITES -> {
                existingMoves = arrayListOf(
                    Move(0, -1, Directions.N, MoveTypes.NORMAL_MOVE, { true }),
                    Move(0, -2, Directions.N, MoveTypes.NORMAL_MOVE,
                        { yIdx -> yIdx == 7 }
                    ),
                    Move(-1, -1, Directions.NW, MoveTypes.EAT_MOVE, { true }),
                    Move(1, -1, Directions.NE, MoveTypes.EAT_MOVE, { true })
                )
            }
        }
    }

    override fun drawPiece(x: Double, y: Double) {
        context?.beginPath()
        image.src = "./pieces-2D-icons/pawn_${playerType.color}.png"
        context?.drawImage(image, x + SQUARE_SIZE / 4, y + SQUARE_SIZE / 4, PIECE_WIDTH, 35.0)
    }

    override fun createPossibleMoves(x: Int, y: Int) {
        for (move: Move in existingMoves) {
            if (move.type == MoveTypes.NORMAL_MOVE) {
                if (!blockedDirections.contains(move.direction)) {
                    val possibleSquare: Square? =
                        squares[getSquareId(x + move.xCord, y + move.yCord)]
                    if (possibleSquare != null) {    // Checking if square exists
                        if (possibleSquare.hasPiece()) {    // Blocking further moves
                            blockedDirections.add(move.direction)
                        } else if (move.condition(y)) {
                            possibleSquare.drawPossibleMove()
                            possibleMoves.add(possibleSquare.id)
                        }
                    }
                }
            } else if (move.type == MoveTypes.EAT_MOVE) {
                val possibleSquare = squares[getSquareId(x + move.xCord, y + move.yCord)]
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