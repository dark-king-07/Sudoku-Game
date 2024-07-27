package de.dlyt.yanndroid.sudoku

import android.app.Activity
import android.app.ProgressDialog
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.AsyncTask
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager

class MainActivity : AppCompatActivity() {

    private lateinit var context: Context
    private lateinit var sharedPrefGames: SharedPreferences
    private lateinit var sharedPrefSettings: SharedPreferences
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var toolbarMenu: Menu
    private lateinit var resumeButtonLayout: LinearLayout
    private lateinit var mLoadingDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        context = this
        sharedPrefGames = getSharedPreferences("Games", Activity.MODE_PRIVATE)
        sharedPrefSettings = PreferenceManager.getDefaultSharedPreferences(context)

        mLoadingDialog = ProgressDialog(this).apply {
            setProgressStyle(ProgressDialog.STYLE_SPINNER)
            setCancelable(false)
        }

        drawerLayout = findViewById(R.id.drawer_view)
        drawerLayout.setDrawerButtonIcon(getDrawable(R.drawable.ic_oui_settings_outline))
        drawerLayout.setDrawerButtonTooltip(getString(R.string.settings))
        drawerLayout.setDrawerButtonOnClickListener {
            startActivity(Intent().setClass(context, SettingsActivity::class.java))
        }

        resumeButtonLayout = findViewById(R.id.resume_button_layout)
        drawerLayout.getAppBarLayout().addOnOffsetChangedListener { layout, verticalOffset ->
            val totalScrollRange = layout.totalScrollRange
            val inputMethodWindowVisibleHeight = InputMethodManager::class.java
                .getMethod("getInputMethodWindowVisibleHeight")
                .invoke(getSystemService(INPUT_METHOD_SERVICE)) as Int

            resumeButtonLayout.translationY = if (totalScrollRange != 0) {
                ((Math.abs(verticalOffset) - totalScrollRange) / 2.0f)
            } else {
                ((Math.abs(verticalOffset) - inputMethodWindowVisibleHeight) / 2.0f)
            }
        }

        drawerLayout.getToolbar().inflateMenu(R.menu.main_menu)
        toolbarMenu = drawerLayout.getToolbar().menu
        setSupportActionBar(drawerLayout.getToolbar())
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_solver_solve -> {
                mLoadingDialog.show()
                object : AsyncTask<Void, Void, Any?>() {
                    override fun doInBackground(vararg params: Void): Any? {
                        return currentGame.makeSolutionFromEdit(true)
                    }

                    override fun onPostExecute(result: Any?) {
                        if (result is Int) {
                            Toast.makeText(context, R.string.no_solution, Toast.LENGTH_SHORT).show()
                        } else {
                            popupGame(result as Game)
                        }
                        mLoadingDialog.dismiss()
                    }
                }.execute()
                true
            }
            R.id.menu_save -> {
                mLoadingDialog.show()
                object : AsyncTask<Void, Void, Any?>() {
                    override fun doInBackground(vararg params: Void): Any? {
                        return currentGame.makeGameFromEdit()
                    }

                    override fun onPostExecute(result: Any?) {
                        if (result is Int) {
                            Toast.makeText(context, if (result == 0) R.string.no_solution else R.string.multiple_solutions, Toast.LENGTH_SHORT).show()
                        } else {
                            (result as Game).apply {
                                name = "Sudoku ${games.size + 1}"
                                addGameToList(this)
                                Toast.makeText(context, getString(R.string.game_added_to_list), Toast.LENGTH_SHORT).show()
                            }
                        }
                        mLoadingDialog.dismiss()
                    }
                }.execute()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}
