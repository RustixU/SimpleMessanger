package rut.miit.simplemessanger.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import rut.miit.simplemessanger.databinding.FragmentSettingsBinding

class SettingsFragment : Fragment() {

    private var _binding: FragmentSettingsBinding? = null
    private val binding: FragmentSettingsBinding
        get() = _binding ?: throw RuntimeException("FragmentSettingsBinding == null")

    private val sharedPreferences by lazy {
        requireContext().getSharedPreferences("1", Context.MODE_PRIVATE)
    }

    private val Context.dataStore by preferencesDataStore("1")

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

        setupLanguagesSpinner()

        loadFromSharedPreferences()
        lifecycleScope.launch {
            loadFromDataStore()
        }

        binding.saveBtn.setOnClickListener {
            saveToSharedPreferences()
            lifecycleScope.launch {
                saveToDataStore()
            }
            Toast.makeText(requireContext(), "Settings saved", Toast.LENGTH_SHORT).show()
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
        requireContext().dataStore.edit { preferences ->
            preferences[NOTIFICATIONS_KEY] = binding.notificationsEnabledBtn.isChecked
            preferences[LANGUAGE_KEY] = binding.languages.selectedItem.toString()
        }
    }

    private suspend fun loadFromDataStore() {
        requireContext().dataStore.data.collect { preferences ->
            val notificationsEnabled = preferences[NOTIFICATIONS_KEY] ?: false
            val language = preferences[LANGUAGE_KEY] ?: "English"

            binding.notificationsEnabledBtn.isChecked = notificationsEnabled
            val languageIndex =
                (binding.languages.adapter as ArrayAdapter<String>).getPosition(language)
            binding.languages.setSelection(languageIndex)
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