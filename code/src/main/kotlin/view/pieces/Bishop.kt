/*
 * Copyright (c) 21/8/2021, Pexers (https://github.com/Pexers)
 */

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

class Bishop : Piece {

    override val playerType: Players
    override var existingMoves: ArrayList<Move>
    override var possibleMoves: ArrayList<String> = arrayListOf()
    override var blockedDirections: ArrayList<Directions> = arrayListOf()
    override val image: HTMLImageElement = window.document.createElement("img") as HTMLImageElement

    constructor(playerType: Players) {
        this.playerType = playerType
        existingMoves = arrayListOf(
            Move(99, -99, Directions.NE, MoveTypes.EAT_MOVE, null),
            Move(-99, -99, Directions.NW, MoveTypes.EAT_MOVE, null),
            Move(99, 99, Directions.SE, MoveTypes.EAT_MOVE, null),
            Move(-99, 99, Directions.SW, MoveTypes.EAT_MOVE, null)
        )
    }

    override fun drawPiece(x: Double, y: Double) {
        context?.beginPath()
        image.src = "./pieces-2D-icons/bishop_${playerType.color}.png"
        context?.drawImage(image, x + SQUARE_SIZE / 4, y + SQUARE_SIZE / 6, PIECE_WIDTH, 50.0)
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