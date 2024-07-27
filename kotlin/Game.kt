package de.dlyt.yanndroid.sudoku.game

class Game {
    private val grid: Array<IntArray> = Array(9) { IntArray(9) }
    private val editable: Array<BooleanArray> = Array(9) { BooleanArray(9) }

    fun setCellValue(row: Int, col: Int, value: Int) {
        grid[row][col] = value
    }

    fun getCellValue(row: Int, col: Int): Int {
        return grid[row][col]
    }

    fun setEditable(row: Int, col: Int, isEditable: Boolean) {
        editable[row][col] = isEditable
    }

    fun isEditable(row: Int, col: Int): Boolean {
        return editable[row][col]
    }
}
