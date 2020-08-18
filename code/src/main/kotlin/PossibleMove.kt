data class PossibleMove(
    val xCord: Int,
    val yCord: Int,
    val direction: Directions,
    val moveType: MoveTypes,
    val condition: (args: Any) -> Boolean
)