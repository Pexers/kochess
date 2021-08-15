package model

import controller.BLACK_COLOR
import controller.WHITE_COLOR

enum class Players(val color: String) {
    WHITES(WHITE_COLOR),
    BLACKS(BLACK_COLOR)
}