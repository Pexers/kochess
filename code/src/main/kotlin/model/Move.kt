/*
 * Copyright (c) 21/8/2021, Pexers (https://github.com/Pexers)
 */

package model

data class Move(
    val xCord: Int,
    val yCord: Int,
    val direction: Directions,
    val type: MoveTypes,
    val condition: ((args: Any) -> Boolean)?
)