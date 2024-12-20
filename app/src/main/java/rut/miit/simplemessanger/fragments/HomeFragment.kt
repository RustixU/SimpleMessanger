package rut.miit.simplemessanger.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import rut.miit.simplemessanger.R
import rut.miit.simplemessanger.adapters.CharactersAdapter
import rut.miit.simplemessanger.database.AppDatabase
import rut.miit.simplemessanger.databinding.FragmentHomeBinding
import rut.miit.simplemessanger.entity.Character
import rut.miit.simplemessanger.network.ApiService
import rut.miit.simplemessanger.network.RetrofitNetwork
import rut.miit.simplemessanger.repositories.CharacterRepository

class HomeFragment : Fragment(R.layout.fragment_home) {

    private var _binding: FragmentHomeBinding? = null
    private val binding: FragmentHomeBinding
        get() = _binding ?: throw RuntimeException("FragmentHomeBinding == null")

    private var currentPage = 1
    private val pageSize = 50

    private lateinit var repository: CharacterRepository
    private lateinit var adapter: CharactersAdapter

    private var _retrofitApi: ApiService? = null
    private val retrofitApi get() = _retrofitApi ?: throw RuntimeException("ApiService == null")

//    private val fileName = "1_Avezov.txt"
//    private lateinit var savingData: List<Character>

    var isLoading = false

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        _retrofitApi = RetrofitNetwork()

        val recyclerView = binding.charactersList
        recyclerView.layoutManager = LinearLayoutManager(requireContext())

        adapter = CharactersAdapter(emptyList())
        recyclerView.adapter = adapter

        val database = AppDatabase.getDatabase(requireContext())
        repository = CharacterRepository(database.characterDao())

        loadCharactersFromDatabase()

//        val nextPageBtn = binding.nextPageBtn
//        val previewPageBtn = binding.previewPageBtn
//        val saveBtn = binding.saveImageBtn
//        val deleteBtn = binding.deleteImageBtn
        val loadFromApiBtn = binding.loadFromApiImageButton

        loadFromApiBtn.setOnClickListener{
            updateCharactersFromApi()
        }

//        binding.pageTextView.addTextChangedListener {
//            previewPageBtn.isEnabled = currentPage > 1
//        }

        binding.charactersList.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)

                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()

                if (!isLoading && totalItemCount <= lastVisibleItem + 5) {
                    isLoading = true
                    currentPage++
                    loadNextPage()
                }
            }
        })

//        saveBtn.setOnClickListener {
//            saveHeroesToFile(savingData)
//        }
//
//        deleteBtn.setOnClickListener {
//            createBackupFile(savingData)
//            deleteFile()
//        }
    }

    private fun loadNextPage() {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val apiCharacters = retrofitApi.getCharacters(currentPage, pageSize)

                if (apiCharacters.isNotEmpty()) {
                    val entities = apiCharacters.map { response ->
                        Character(
                            name = response.name,
                            culture = response.culture,
                            born = response.born,
                            titles = response.titles,
                            aliases = response.aliases,
                            playedBy = response.playedBy
                        )
                    }

                    val layoutManager =
                        binding.charactersList.layoutManager as LinearLayoutManager//
                    val currentPosition = layoutManager.findFirstVisibleItemPosition()//

                    repository.insertCharacters(entities)

                    val characters = repository.getCharacters().first()
                    adapter.setData(characters)

                    layoutManager.scrollToPosition(currentPosition)

                } else {
                    Log.d("HomeFragment", "No more characters to load.")
                }

                isLoading = false

            } catch (e: Exception) {
                Log.e(
                    "HomeFragment",
                    "Error fetching characters for page $currentPage: ${e.message}"
                )
                isLoading = false
            }
        }
    }

    private fun loadCharactersFromDatabase() {
        lifecycleScope.launch {
            repository.getCharacters().collect { characters ->
                if (characters.isNotEmpty()) {
                    binding.charactersList.adapter = CharactersAdapter(characters)
                } else {
                    fetchAndSaveCharacters()
                }
            }
        }
    }

    private fun updateCharactersFromApi() {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val apiCharacters = retrofitApi.getCharacters(currentPage, pageSize)

                val entities = apiCharacters.map { response ->
                    Character(
                        name = response.name,
                        culture = response.culture,
                        born = response.born,
                        titles = response.titles,
                        aliases = response.aliases,
                        playedBy = response.playedBy
                    )
                }

                Log.d("HomeFragment", "ENTITIES = $entities")

                repository.deleteAll()
                repository.insertCharacters(entities)

                val layoutManager = binding.charactersList.layoutManager as LinearLayoutManager
                val currentPosition = layoutManager.findFirstVisibleItemPosition()

                val characters = repository.getCharacters().first()
                activity?.runOnUiThread {
                    adapter.setData(characters)

                    layoutManager.scrollToPosition(currentPosition)
                }
            } catch (e: Exception) {
                Log.e("HomeFragment", "Error updating characters: ${e.message}")
            }
        }
    }

    private fun fetchAndSaveCharacters() {
        lifecycleScope.launch(Dispatchers.IO) {
            try {
                val apiCharacters = retrofitApi.getCharacters(currentPage, pageSize)

                val entities = apiCharacters.map { response ->
                    Character(
                        name = response.name,
                        culture = response.culture,
                        born = response.born,
                        titles = response.titles,
                        aliases = response.aliases,
                        playedBy = response.playedBy
                    )
                }

                repository.insertCharacters(entities)

                Log.d("HomeFragment", "Characters saved to database")
            } catch (e: Exception) {
                Log.e("HomeFragment", "Error fetching characters: ${e.message}")
            }
        }
    }


//    @SuppressLint("SetTextI18n")
//    private fun updatePageText() {
//        binding.pageTextView.text = "Page: $currentPage"
//    }

//    private fun saveHeroesToFile(characters: List<Character>) {
//        val fileContent = characters.joinToString("\n") { character ->
//            "Name: ${character.name}, Culture: ${character.culture}"
//        }
//
//        val externalStorageDir =
//            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
//        val file = File(externalStorageDir, fileName)
//
//        try {
//            if (isFileExists()) {
//                Toast.makeText(requireContext(), "Файл уже существует", Toast.LENGTH_SHORT).show()
//            } else {
//                file.writeText(fileContent)
//                Toast.makeText(
//                    requireContext(), "Файл сохранён: ${file.absolutePath}", Toast.LENGTH_SHORT
//                ).show()
//            }
//        } catch (e: Exception) {
//            Toast.makeText(
//                requireContext(),
//                "Ошибка сохранения файла: ${e.localizedMessage}",
//                Toast.LENGTH_SHORT
//            ).show()
//        }
//    }
//
//    private fun deleteFile() {
//        val externalStorageDir =
//            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
//        val file = File(externalStorageDir, fileName)
//
//        if (isFileExists()) {
//            file.delete()
//            Toast.makeText(requireContext(), "Файл удалён", Toast.LENGTH_SHORT).show()
//        } else {
//            Toast.makeText(requireContext(), "Файл не найден", Toast.LENGTH_SHORT).show()
//        }
//    }
//
//    private fun isFileExists(): Boolean {
//        val externalStorageDir =
//            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
//        val file = File(externalStorageDir, fileName)
//        return file.exists()
//    }
//
//    private fun createBackupFile(characters: List<Character>) {
//        val fileContent = characters.joinToString("\n") { character ->
//            "Name: ${character.name}, Culture: ${character.culture}"
//        }
//
//        val backupFile = File(requireContext().filesDir, fileName)
//
//        try {
//            backupFile.writeText(fileContent)
//            Log.d("Backup", "Резервная копия создана: ${backupFile.absolutePath}")
//        } catch (e: Exception) {
//            Log.d("Backup", "Ошибка создания резервной копии: ${e.localizedMessage}")
//        }
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
        Log.d("HomeFragment", "Fragment Destroyed")
    }
}
