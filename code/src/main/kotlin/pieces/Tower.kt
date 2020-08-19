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

class Tower : Piece {

    override val playerType: Players
    override var existingMoves: ArrayList<Move>
    override var possibleMoves: ArrayList<String> = arrayListOf()
    override var blockedDirections: ArrayList<Directions> = arrayListOf()
    override val image: HTMLImageElement = window.document.createElement("img") as HTMLImageElement

    constructor(playerType: Players) {
        this.playerType = playerType
        when (playerType) {
            Players.BLACKS -> existingMoves = arrayListOf(
                Move(0, -99, Directions.N, MoveTypes.EAT_MOVE, { true }),
                Move(0, 99, Directions.S, MoveTypes.EAT_MOVE, { true }),
                Move(99, 0, Directions.E, MoveTypes.EAT_MOVE, { true }),
                Move(-99, 0, Directions.W, MoveTypes.EAT_MOVE, { true })
            )
            Players.WHITES -> {
                existingMoves = arrayListOf(
                    Move(0, -99, Directions.N, MoveTypes.EAT_MOVE, { true }),
                    Move(0, 99, Directions.S, MoveTypes.EAT_MOVE, { true }),
                    Move(99, 0, Directions.E, MoveTypes.EAT_MOVE, { true }),
                    Move(-99, 0, Directions.W, MoveTypes.EAT_MOVE, { true })
                )
            }
        }
    }

    override fun drawPiece(x: Double, y: Double) {
        context?.beginPath()
        image.src = "./pieces-2D-icons/tower_${playerType.color}.png"
        context?.drawImage(image, x + SQUARE_SIZE / 4, y + SQUARE_SIZE / 4, PIECE_WIDTH, 35.0)
    }

    override fun createPossibleMoves(x: Int, y: Int) {
        var xNum = 0
        var yNum = 0
        for (move: Move in existingMoves) {
            when (move.xCord) {
                -99 -> xNum = -1
                99 -> xNum = 1
            }
            when (move.yCord) {
                -99 -> yNum = -1
                99 -> yNum = 1
            }
            for (i: Int in 1..8) {
                if (!blockedDirections.contains(move.direction)) {
                    val possibleSquare: Square? =
                        squares[getSquareId(x + i * xNum, y + i * yNum)]
                    if (possibleSquare != null) {    // Checking if square exists
                        if (possibleSquare.hasPiece()) {    // Blocking further moves
                            blockedDirections.add(move.direction)
                            if (possibleSquare.piece!!.playerType != playerType) {
                                possibleSquare.drawPossibleMove()
                                possibleMoves.add(possibleSquare.id)
                            }
                            break
                        } else {
                            possibleSquare.drawPossibleMove()
                            possibleMoves.add(possibleSquare.id)
                        }
                    }
                }
            }
            xNum = 0
            yNum = 0
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