package rut.miit.simplemessanger.fragments

import android.content.Context
import android.os.Bundle
import android.os.Environment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import rut.miit.simplemessanger.databinding.FragmentSettingsBinding
import java.io.File

private val Context.dataStore by preferencesDataStore(name = "settings")

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding: FragmentSettingsBinding
        get() = _binding ?: throw RuntimeException("FragmentSettingsBinding == null")

    private val fileName = "1_Avezov.txt"

    private val sharedPreferences by lazy {
        requireContext().getSharedPreferences("settings", Context.MODE_PRIVATE)
    }

    private val dataStore by lazy { requireContext().dataStore }

    private val NOTIFICATIONS_KEY = booleanPreferencesKey("notifications_enabled")
    private val LANGUAGE_KEY = stringPreferencesKey("language")

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        _binding = FragmentSettingsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val saveSettingsBtn = binding.saveSettingsBtn
        val restoreBackupBtn = binding.restoreBackupBtn
        val deleteBackupImageBtn = binding.deleteBackupImageBtn

        setupLanguagesSpinner()

        loadFromSharedPreferences()
        lifecycleScope.launch {
            restoreBackupBtn.isVisible = isBackupExists()
            deleteBackupImageBtn.isVisible = isBackupExists()
            loadFromDataStore()
        }



        saveSettingsBtn.setOnClickListener {
            saveToSharedPreferences()
            lifecycleScope.launch {
                saveToDataStore()
            }
            Toast.makeText(requireContext(), "Настройки сохранён", Toast.LENGTH_SHORT).show()
        }

        restoreBackupBtn.setOnClickListener {
            restoreBackupFile()
        }

        deleteBackupImageBtn.setOnClickListener {
            deleteBackupFile()
            restoreBackupBtn.isVisible = isBackupExists()
            deleteBackupImageBtn.isVisible = isBackupExists()
        }
    }

    private fun saveToSharedPreferences() {
        val editor = sharedPreferences.edit()
        editor.putString("nickname", binding.nicknameEditText.text.toString())
        editor.putBoolean("theme", binding.themeSwitch.isChecked)
        editor.apply()
    }

    private fun loadFromSharedPreferences() {
        val nickname = sharedPreferences.getString("nickname", "")
        val theme = sharedPreferences.getBoolean("theme", false)

        binding.nicknameEditText.setText(nickname)
        binding.themeSwitch.isChecked = theme
    }

    private suspend fun saveToDataStore() {
        lifecycleScope.launch {
            dataStore.edit { preferences ->
                preferences[NOTIFICATIONS_KEY] = binding.notificationsEnabledBtn.isChecked
                preferences[LANGUAGE_KEY] = binding.languages.selectedItem.toString()
            }
        }
    }

    private suspend fun loadFromDataStore() {
        lifecycleScope.launch {
            dataStore.data.collect { preferences ->
                val notificationsEnabled = preferences[NOTIFICATIONS_KEY] ?: false
                val language = preferences[LANGUAGE_KEY] ?: "English"

                binding.notificationsEnabledBtn.isChecked = notificationsEnabled
                val languageIndex = (binding.languages.adapter as ArrayAdapter<String>)
                    .getPosition(language)
                binding.languages.setSelection(languageIndex)
            }
        }
    }

    private fun restoreBackupFile() {
        val backupFile = File(requireContext().filesDir, fileName)
        val externalStorageDir =
            Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
        val restoredFile = File(externalStorageDir, fileName)

        try {
            restoredFile.writeText(backupFile.readText())
            Toast.makeText(
                requireContext(),
                "Файл восстановлен: ${restoredFile.absolutePath}",
                Toast.LENGTH_SHORT
            ).show()
        } catch (e: Exception) {
            Toast.makeText(
                requireContext(),
                "Ошибка восстановления файла: ${e.localizedMessage}",
                Toast.LENGTH_SHORT
            ).show()
        }
    }

    private fun isBackupExists(): Boolean {
        val backupFile = File(requireContext().filesDir, fileName)
        return backupFile.exists()
    }

    private fun deleteBackupFile() {
        val backupFile = File(requireContext().filesDir, fileName)
        if (isBackupExists()) {
            backupFile.delete()
            Toast.makeText(requireContext(), "Backup удалён", Toast.LENGTH_SHORT).show()
        } else {
            Toast.makeText(requireContext(), "Backup не найден", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setupLanguagesSpinner() {
        val languages = listOf("English", "Russian", "Spanish")
        val adapter =
            ArrayAdapter(requireContext(), android.R.layout.simple_spinner_item, languages)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
        binding.languages.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}