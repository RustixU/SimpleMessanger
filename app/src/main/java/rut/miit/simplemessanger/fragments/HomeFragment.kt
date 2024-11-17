package rut.miit.simplemessanger.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import rut.miit.simplemessanger.R
import rut.miit.simplemessanger.adapters.CharactersAdapter
import rut.miit.simplemessanger.databinding.FragmentHomeBinding
import rut.miit.simplemessanger.network.ApiService
import rut.miit.simplemessanger.network.RetrofitNetwork

class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding
        get() = _binding ?: throw RuntimeException("FragmentHomeBinding == null")

    private var currentPage = 1
    private val pageSize = 50

    private var _retrofitApi: ApiService? = null
    private val retrofitApi get() = _retrofitApi ?: throw RuntimeException("ApiService == null")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

//        val chats = arrayListOf(
//            Chat("John", "Привет, сегодня идём гулять?"),
//            Chat("Alice", "Что делаешь?"),
//            Chat("Alexey", "Бро, я такой клатч потащил")
//        )
//
//        val list: RecyclerView = view.findViewById(R.id.chatsList)
//
//        list.layoutManager = GridLayoutManager(requireContext(), 1)
//        list.adapter = ChatAdapter(chats, requireContext())

        _retrofitApi = RetrofitNetwork()

        val recyclerView = binding.charactersList
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        val adapter = CharactersAdapter(emptyList())
        recyclerView.adapter = adapter

        loadCharacters(adapter, currentPage)

        binding.next.setOnClickListener {
            currentPage++
            loadCharacters(adapter, currentPage)
        }

        binding.preview.setOnClickListener {
            currentPage--
            loadCharacters(adapter, currentPage)
        }

        binding.archive.setOnClickListener {
            currentPage = 1
            loadCharacters(adapter, currentPage)
        }
    }

    private fun loadCharacters(adapter: CharactersAdapter, page: Int) {
        lifecycleScope.launch {
            try {
                val characters = retrofitApi.getCharacters(page = page, pageSize = pageSize)
                Log.d("API", "Loaded characters: ${characters.size}")

                adapter.setData(characters)
                updatePageText()
            } catch (e: Exception) {
                Toast.makeText(
                    requireContext(),
                    "Ошибка: ${e.localizedMessage}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updatePageText() {
        binding.pageTextView.text = "Page: $currentPage"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        Log.d("HomeFragment", "Fragment Destroyed")
    }
}