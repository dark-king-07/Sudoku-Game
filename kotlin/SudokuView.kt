package de.dlyt.yanndroid.sudoku.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.view.MotionEvent
import android.view.View
import de.dlyt.yanndroid.sudoku.game.Cell
import de.dlyt.yanndroid.sudoku.game.Game

class SudokuView(context: Context, private val game: Game) : View(context) {
    private val paint: Paint = Paint()
    private var selectedCell: Cell? = null

    init {
        paint.color = Color.BLACK
        paint.strokeWidth = 3f
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        drawGrid(canvas)
        drawCells(canvas)
    }

    private fun drawGrid(canvas: Canvas) {
        for (i in 0..9) {
            val xy = i * width / 9f
            canvas.drawLine(xy, 0f, xy, height.toFloat(), paint)
            canvas.drawLine(0f, xy, width.toFloat(), xy, paint)
        }
    }

    private fun drawCells(canvas: Canvas) {
        val cellSize = width / 9f
        for (row in 0 until 9) {
            for (col in 0 until 9) {
                val value = game.getCellValue(row, col)
                if (value != 0) {
                    val x = col * cellSize + cellSize / 2
                    val y = row * cellSize + cellSize / 2
                    canvas.drawText(value.toString(), x, y, paint)
                }
            }
        }
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        if (event.action == MotionEvent.ACTION_DOWN) {
            val cellSize = width / 9f
            val row = (event.y / cellSize).toInt()
            val col = (event.x / cellSize).toInt()
            selectedCell = Cell(row, col)
            invalidate()
            return true
        }
        return super.onTouchEvent(event)
    }
}
