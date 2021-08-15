package view.pieces

import controller.PIECE_WIDTH
import controller.SQUARE_SIZE
import controller.context
import controller.getSquareId
import org.w3c.dom.*
import controller.squares
import model.*
import view.Square
import kotlin.browser.window

class Horse : Piece {

    override val playerType: Players
    override var existingMoves: ArrayList<Move>
    override var possibleMoves: ArrayList<String> = arrayListOf()
    override var blockedDirections: ArrayList<Directions> = arrayListOf() // Not used in this piece
    override val image: HTMLImageElement = window.document.createElement("img") as HTMLImageElement

    constructor(playerType: Players) {
        this.playerType = playerType
        existingMoves = arrayListOf(
            Move(1, -2, Directions.OTHER, MoveTypes.EAT_MOVE, null),
            Move(-1, -2, Directions.OTHER, MoveTypes.EAT_MOVE, null),
            Move(1, 2, Directions.OTHER, MoveTypes.EAT_MOVE, null),
            Move(-1, 2, Directions.OTHER, MoveTypes.EAT_MOVE, null),
            Move(-2, -1, Directions.OTHER, MoveTypes.EAT_MOVE, null),
            Move(2, -1, Directions.OTHER, MoveTypes.EAT_MOVE, null),
            Move(-2, 1, Directions.OTHER, MoveTypes.EAT_MOVE, null),
            Move(2, 1, Directions.OTHER, MoveTypes.EAT_MOVE, null)
        )
    }

    override fun drawPiece(x: Double, y: Double) {
        context?.beginPath()
        image.src = "./pieces-2D-icons/horse_${playerType.color}.png"
        context?.drawImage(image, x + SQUARE_SIZE / 4, y + SQUARE_SIZE / 6, PIECE_WIDTH, 40.0)
    }

    override fun createPossibleMoves(x: Int, y: Int) {
        for (move: Move in existingMoves) {
            val possibleSquare: Square? =
                squares[getSquareId(x + move.xCord, y + move.yCord)]
            if (possibleSquare != null) {    // Checking if square exists
                if (possibleSquare.hasPiece()) {    // Blocking further moves
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

    override fun clearPossibleMoves() {
        for (squareId: String in possibleMoves) {
            squares[squareId]!!.clearPossibleMove()
        }
    }

    override fun isThisMovePossible(squareId: String): Boolean = possibleMoves.contains(squareId)

}