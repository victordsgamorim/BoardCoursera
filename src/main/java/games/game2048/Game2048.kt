package games.game2048

import board.Cell
import board.Direction
import board.Direction.*
import board.GameBoard
import board.createGameBoard
import games.game.Game
import java.lang.IllegalArgumentException

/*
 * Your task is to implement the game 2048 https://en.wikipedia.org/wiki/2048_(video_game).
 * Implement the utility methods below.
 *
 * After implementing it you can try to play the game running 'PlayGame2048'.
 */
fun newGame2048(initializer: Game2048Initializer<Int> = RandomGame2048Initializer): Game = Game2048(initializer)

class Game2048(private val initializer: Game2048Initializer<Int>) : Game {
    private val board = createGameBoard<Int?>(4)

    override fun initialize() {
        repeat(2) {
            board.addNewValue(initializer)
        }
    }

    override fun canMove() = board.any { it == null }

    override fun hasWon() = board.any { it == 2048 }

    override fun processMove(direction: Direction) {
        if (board.moveValues(direction)) {
            board.addNewValue(initializer)
        }
    }

    override fun get(i: Int, j: Int): Int? = board.run { get(getCell(i, j)) }
}

/*
 * Add a new value produced by 'initializer' to a specified cell in a board.
 */
fun GameBoard<Int?>.addNewValue(initializer: Game2048Initializer<Int>) {
    val (cell, value) = initializer.nextValue(this)
            ?: throw IllegalArgumentException("Empty Initializer")

    this[cell] = value
}

/*
 * Update the values stored in a board,
 * so that the values were "moved" in a specified rowOrColumn only.
 * Use the helper function 'moveAndMergeEqual' (in Game2048Helper.kt).
 * The values should be moved to the beginning of the row (or column),
 * in the same manner as in the function 'moveAndMergeEqual'.
 * Return 'true' if the values were moved and 'false' otherwise.
 */
fun GameBoard<Int?>.moveValuesInRowOrColumn(rowOrColumn: List<Cell>): Boolean {
    val values = rowOrColumn
            .map { cell -> this[cell] }
            .moveAndMergeEqual { value -> value.times(2) }


    return when (values.isNotEmpty()) {
        true -> addNewValueToCell(values, rowOrColumn)
        else -> false
    }
}

private fun GameBoard<Int?>.addNewValueToCell(values: List<Int?>, rowOrColumn: List<Cell>): Boolean {

    var move = false

    for (i in values.indices) {
        val cell = rowOrColumn[i]
        val addedValue = values[i]

        if (this[cell] != addedValue) {
            this[cell] = addedValue
            move = true
        }

    }
    for (i in values.size until width) this[rowOrColumn[i]] = null
    return move
}

private fun addNewValueToCell(values: List<Int?>, allCells: List<Cell>, board: GameBoard<Int?>): Boolean {

    for (i in values.indices) {
        val cell = allCells[i]
        val newValue = values[i]

        if (board[cell] != newValue) {
            board[cell] = newValue
            return true
        }

        for (i in values.size until board.width) {
            board[allCells[i]] = null
        }
    }
    return false
}

/*
 * Update the values stored in a board,
 * so that the values were "moved" to the specified direction
 * following the rules of the 2048 game .
 * Use the 'moveValuesInRowOrColumn' function above.
 * Return 'true' if the values were moved and 'false' otherwise.
 */
fun GameBoard<Int?>.moveValues(direction: Direction): Boolean =
        when (direction) {
            LEFT -> movingRow(1..width)
            RIGHT -> movingRow(width downTo 1)
            DOWN -> movingColumn(width downTo 1)
            UP -> movingColumn(1..width)

        }

private fun GameBoard<Int?>.movingColumn(rowRange: IntProgression): Boolean {
    var moving = false

    for (column in 1..width) {
        val column = this.getColumn(rowRange, column)
        moving = moveValuesInRowOrColumn(column)
    }
    return moving
}

private fun GameBoard<Int?>.movingRow(jRange: IntProgression): Boolean {
    var moving = false
    for (row in 1..width) {
        val rows = this.getRow(row, jRange)
        moving = moveValuesInRowOrColumn(rows)
    }
    return moving
}