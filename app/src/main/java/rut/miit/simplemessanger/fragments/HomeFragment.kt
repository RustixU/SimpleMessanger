package rut.miit.simplemessanger.fragments

import android.annotation.SuppressLint
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.coroutines.launch
import rut.miit.simplemessanger.R
import rut.miit.simplemessanger.adapters.CharactersAdapter
import rut.miit.simplemessanger.databinding.FragmentHomeBinding
import rut.miit.simplemessanger.models.Character
import rut.miit.simplemessanger.network.ApiService
import rut.miit.simplemessanger.network.RetrofitNetwork
import java.io.File

class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding
        get() = _binding ?: throw RuntimeException("FragmentHomeBinding == null")

    private var currentPage = 1
    private val pageSize = 50

    private var _retrofitApi: ApiService? = null
    private val retrofitApi get() = _retrofitApi ?: throw RuntimeException("ApiService == null")

    private val fileName = "1_Avezov.txt"
    private lateinit var savingData: List<Character>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
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

        val nextPageBtn = binding.nextPageBtn
        val previewPageBtn = binding.previewPageBtn
        val saveBtn = binding.saveImageBtn
        val deleteBtn = binding.deleteImageBtn

        binding.pageTextView.addTextChangedListener {
            previewPageBtn.isEnabled = currentPage > 1
        }

        nextPageBtn.setOnClickListener {
            currentPage++
            loadCharacters(adapter, currentPage)
        }

        previewPageBtn.setOnClickListener {
            if (currentPage > 1) {
                currentPage--
                loadCharacters(adapter, currentPage)
            } else {
                Toast.makeText(requireContext(), "Нельзя", Toast.LENGTH_SHORT).show()
            }
        }

        saveBtn.setOnClickListener {
            saveHeroesToFile(savingData)
        }

        deleteBtn.setOnClickListener {
            createBackupFile(savingData)
            deleteFile()
        }
    }

    private fun loadCharacters(adapter: CharactersAdapter, page: Int) {
        lifecycleScope.launch {
            try {
                binding.saveImageBtn.isEnabled = false
                binding.nextPageBtn.isEnabled = false
                binding.previewPageBtn.isEnabled = false

                val characters = retrofitApi.getCharacters(page = page, pageSize = pageSize)
                savingData = characters
                Log.d("API", "Loaded characters: ${characters.size}")

                adapter.setData(characters)
                updatePageText()

                binding.saveImageBtn.isEnabled = true
                binding.nextPageBtn.isEnabled = true
                binding.previewPageBtn.isEnabled = true
            } catch (e: Exception) {
                Toast.makeText(
                    requireContext(), "Ошибка: ${e.localizedMessage}", Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun updatePageText() {
        binding.pageTextView.text = "Page: $currentPage"
    }

    private fun saveHeroesToFile(characters: List<Character>) {
        val fileContent = characters.joinToString("\n") { character ->
            "Name: ${character.name}, Culture: ${character.culture}"
        }

        val externalStorageDir =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
        val file = File(externalStorageDir, fileName)

        try {
            if (isFileExists()) {
                Toast.makeText(requireContext(), "Файл уже существует", Toast.LENGTH_SHORT).show()
            } else {
                file.writeText(fileContent)
                Toast.makeText(
                    requireContext(), "Файл сохранён: ${file.absolutePath}", Toast.LENGTH_SHORT
                ).show()
            }
        } catch (e: Exception) {
            Toast.makeText(
                requireContext(),
                "Ошибка сохранения файла: ${e.localizedMessage}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun deleteFile() {
        val externalStorageDir =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
        val file = File(externalStorageDir, fileName)

        if (isFileExists()) {
            file.delete()
            Toast.makeText(requireContext(), "Файл удалён", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), "Файл не найден", Toast.LENGTH_SHORT).show()
        }
    }

    private fun isFileExists(): Boolean {
        val externalStorageDir =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
        val file = File(externalStorageDir, fileName)
        return file.exists()
    }

    private fun createBackupFile(characters: List<Character>) {
        val fileContent = characters.joinToString("\n") { character ->
            "Name: ${character.name}, Culture: ${character.culture}"
        }

        val backupFile = File(requireContext().filesDir, fileName)

        try {
            backupFile.writeText(fileContent)
            Log.d("Backup", "Резервная копия создана: ${backupFile.absolutePath}")
        } catch (e: Exception) {
            Log.d("Backup", "Ошибка создания резервной копии: ${e.localizedMessage}")
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        Log.d("HomeFragment", "Fragment Destroyed")
    }
}