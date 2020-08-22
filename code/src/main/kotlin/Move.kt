data class Move(
    val xCord: Int,
    val yCord: Int,
    val direction: Directions,
    val type: MoveTypes,
    val condition: ((args: Any) -> Boolean)?
)