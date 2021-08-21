/*
 * Copyright (c) 21/8/2021, Pexers (https://github.com/Pexers)
 */

package controller

import kotlin.browser.window

// Sizes
const val SQUARE_SIZE: Double = 65.0
const val CIRCLE_SIZE: Double = 13.0
const val PIECE_WIDTH: Double = SQUARE_SIZE - ((SQUARE_SIZE / 4) * 2)
const val POSITION_Y: Double = 0.0
val POSITION_X: Double = (window.innerWidth / 4).toDouble()

// Colors
const val LIGHT_SQUARE_COLOR: String = "#E2E7BF"
const val DARK_SQUARE_COLOR: String = "#6EA257"
const val WHITE_COLOR: String = "white"
const val BLACK_COLOR: String = "black"
const val SELECTED_COLOR: String = "#E6F65D"
const val POSSIBLE_MOVE_COLOR: String = "#bebebe96"


