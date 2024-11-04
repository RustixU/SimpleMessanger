package rut.miit.simplemessanger.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import rut.miit.simplemessanger.R
import rut.miit.simplemessanger.models.Chat

class ChatAdapter(private val chats: List<Chat>, var context: Context):
    RecyclerView.Adapter<ChatAdapter.MyViewHandler>() {

    class MyViewHandler(view: View): RecyclerView.ViewHolder(view) {
        val usernameChat: TextView = view.findViewById(R.id.usernameChat)
        val textChat: TextView = view.findViewById(R.id.textChat)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHandler {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.chat, parent, false)
        return MyViewHandler(view)
    }

    override fun getItemCount(): Int = chats.count()

    override fun onBindViewHolder(holder: MyViewHandler, position: Int) {
        holder.usernameChat.text = chats[position].usernameChat
        holder.textChat.text = chats[position].textChat
    }
}