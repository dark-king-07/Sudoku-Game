package de.dlyt.yanndroid.sudoku.dialog

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.view.View
import android.widget.Button
import de.dlyt.yanndroid.sudoku.R

class NewSudokuDialog(context: Context) : Dialog(context) {

    private lateinit var generateButton: Button
    private lateinit var importButton: Button
    private lateinit var makeOwnButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_new_sudoku_root)

        generateButton = findViewById(R.id.generate_button)
        importButton = findViewById(R.id.import_button)
        makeOwnButton = findViewById(R.id.make_own_button)

        generateButton.setOnClickListener {
            // handle generate button click
        }

        importButton.setOnClickListener {
            // handle import button click
        }

        makeOwnButton.setOnClickListener {
            // handle make own button click
        }
    }
}
