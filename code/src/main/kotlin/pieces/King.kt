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

class King : Piece {

    override val playerType: Players
    override var existingMoves: ArrayList<Move>
    override var possibleMoves: ArrayList<String> = arrayListOf()
    override var blockedDirections: ArrayList<Directions> = arrayListOf()
    override val image: HTMLImageElement = window.document.createElement("img") as HTMLImageElement

    constructor(playerType: Players) {
        this.playerType = playerType
        when (playerType) {
            Players.BLACKS -> existingMoves = arrayListOf(
                Move(0, -1, Directions.N, MoveTypes.EAT_MOVE, { true }),
                Move(0, 1, Directions.S, MoveTypes.EAT_MOVE, { true }),
                Move(1, 0, Directions.E, MoveTypes.EAT_MOVE, { true }),
                Move(-1, 0, Directions.W, MoveTypes.EAT_MOVE, { true }),
                Move(1, -1, Directions.NE, MoveTypes.EAT_MOVE, { true }),
                Move(-1, -1, Directions.NW, MoveTypes.EAT_MOVE, { true }),
                Move(1, 1, Directions.SE, MoveTypes.EAT_MOVE, { true }),
                Move(-1, 1, Directions.SW, MoveTypes.EAT_MOVE, { true })
            )
            Players.WHITES -> {
                existingMoves = arrayListOf(
                    Move(0, -1, Directions.N, MoveTypes.EAT_MOVE, { true }),
                    Move(0, 1, Directions.S, MoveTypes.EAT_MOVE, { true }),
                    Move(1, 0, Directions.E, MoveTypes.EAT_MOVE, { true }),
                    Move(-1, 0, Directions.W, MoveTypes.EAT_MOVE, { true }),
                    Move(1, -1, Directions.NE, MoveTypes.EAT_MOVE, { true }),
                    Move(-1, -1, Directions.NW, MoveTypes.EAT_MOVE, { true }),
                    Move(1, 1, Directions.SE, MoveTypes.EAT_MOVE, { true }),
                    Move(-1, 1, Directions.SW, MoveTypes.EAT_MOVE, { true })
                )
            }
        }
    }

    override fun drawPiece(x: Double, y: Double) {
        context?.beginPath()
        image.src = "./pieces-2D-icons/king_${playerType.color}.png"
        context?.drawImage(image, x + SQUARE_SIZE / 4, y + SQUARE_SIZE / 6, PIECE_WIDTH, 40.0)
    }

    override fun createPossibleMoves(x: Int, y: Int) {
        for (move: Move in existingMoves) {
            if (!blockedDirections.contains(move.direction)) {
                val possibleSquare: Square? =
                    squares[getSquareId(x + move.xCord, y + move.yCord)]
                if (possibleSquare != null) {    // Checking if square exists
                    if (possibleSquare.hasPiece()) {    // Blocking further moves
                        blockedDirections.add(move.direction)
                        if (possibleSquare.piece!!.playerType != playerType) {
                            possibleSquare.drawPossibleMove()
                            possibleMoves.add(possibleSquare.id)
                        }
                    } else {
                        possibleSquare.drawPossibleMove()
                        possibleMoves.add(possibleSquare.id)
                    }
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