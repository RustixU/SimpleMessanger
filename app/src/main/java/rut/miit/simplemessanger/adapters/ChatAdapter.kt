package rut.miit.simplemessanger.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import rut.miit.simplemessanger.databinding.ItemChatBinding
import rut.miit.simplemessanger.models.Chat

class ChatAdapter(private val chats: List<Chat>, var context: Context):
    RecyclerView.Adapter<ChatAdapter.MyViewHandler>() {

    class MyViewHandler(private val binding: ItemChatBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(chat: Chat) {
            with(binding) {
                usernameChat.text = chat.textChat
                textChat.text = chat.textChat
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHandler {
        val binding = ItemChatBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyViewHandler(binding)
    }

    override fun getItemCount(): Int = chats.count()

    override fun onBindViewHolder(holder: MyViewHandler, position: Int) {
        holder.bind(chats[position])
    }
}