package rut.miit.simplemessanger.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import rut.miit.simplemessanger.R
import rut.miit.simplemessanger.adapters.ChatAdapter
import rut.miit.simplemessanger.databinding.FragmentHomeBinding
import rut.miit.simplemessanger.models.Chat

class HomeFragment : Fragment(R.layout.fragment_home) {


    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding
        get() = _binding ?: throw RuntimeException("FragmentHomeBinding == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val chats = arrayListOf(
            Chat("John", "Привет, сегодня идём гулять?"),
            Chat("Alice", "Что делаешь?"),
            Chat("Alexey", "Бро, я такой клатч потащил")
        )

        val list: RecyclerView = view.findViewById(R.id.chatsList)

        list.layoutManager = GridLayoutManager(requireContext(), 1)
        list.adapter = ChatAdapter(chats, requireContext())
    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        Log.d("HomeFragment", "Fragment Destroyed")
    }
}