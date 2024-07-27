package de.dlyt.yanndroid.sudoku.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import de.dlyt.yanndroid.sudoku.R
import de.dlyt.yanndroid.sudoku.game.Game

class GamesListAdapter(private val gameList: List<Game>) : RecyclerView.Adapter<GamesListAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.game_list_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val game = gameList[position]
        holder.title.text = game.name
    }

    override fun getItemCount(): Int {
        return gameList.size
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val title: TextView = view.findViewById(R.id.game_title)
    }
}
