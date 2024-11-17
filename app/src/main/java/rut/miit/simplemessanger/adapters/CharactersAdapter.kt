package rut.miit.simplemessanger.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import rut.miit.simplemessanger.R
import rut.miit.simplemessanger.models.Character

class CharactersAdapter(private var data: List<Character>) :
    RecyclerView.Adapter<CharactersAdapter.CharacterViewHolder>() {

    class CharacterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val name: TextView = view.findViewById(R.id.nameTextView)
        val culture: TextView = view.findViewById(R.id.cultureTextView)
        val born: TextView = view.findViewById(R.id.bornTextView)
        val titles: TextView = view.findViewById(R.id.titlesTextView)
        val aliases: TextView = view.findViewById(R.id.aliasesTextView)
        val playedBy: TextView = view.findViewById(R.id.playedByTextView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_character, parent, false)
        return CharacterViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        val character = data[position]
        holder.name.text = "Name: ${character.name}"
        holder.culture.text = "Culture: ${character.culture}"
        holder.born.text = "Born: ${character.born}"
        holder.titles.text = "Titles: ${character.titles.joinToString(", ")}"
        holder.aliases.text = "Aliases: ${character.aliases.joinToString(", ")}"
        holder.playedBy.text = "Played By: ${character.playedBy.joinToString(", ")}"
    }

    override fun getItemCount(): Int = data.size

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newData: List<Character>) {
        data = newData
        notifyDataSetChanged()
    }
}