package rut.miit.simplemessanger.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import rut.miit.simplemessanger.databinding.ItemCharacterBinding
import rut.miit.simplemessanger.models.Character

class CharactersAdapter(private var data: List<Character>) :
    RecyclerView.Adapter<CharactersAdapter.CharacterViewHolder>() {

    class CharacterViewHolder(private val binding: ItemCharacterBinding) : RecyclerView.ViewHolder(binding.root) {

        fun bind(character: Character) {
            with(binding) {
                nameTextView.text = character.name
                cultureTextView.text = character.culture
                bornTextView.text = character.born
                titlesTextView.text = character.titles.joinToString(", ")
                aliasesTextView.text = character.aliases.joinToString(", ")
                playedByTextView.text = character.playedBy.joinToString(", ")
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CharacterViewHolder {
        val binding = ItemCharacterBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CharacterViewHolder(binding)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CharacterViewHolder, position: Int) {
        holder.bind(data[position])
    }

    override fun getItemCount(): Int = data.size

    @SuppressLint("NotifyDataSetChanged")
    fun setData(newData: List<Character>) {
        data = newData
        notifyDataSetChanged()
    }
}