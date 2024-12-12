package rut.miit.simplemessanger.repositories

import android.database.SQLException
import android.util.Log
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import rut.miit.simplemessanger.dao.CharacterDao
import rut.miit.simplemessanger.entity.Character

class CharacterRepository(private val dao: CharacterDao) {

    fun getCharacters(): Flow<List<Character>> {
        try {
            return dao.getAll()
        } catch (ex: NoSuchElementException) {
            Log.d("Error SQL", "No such elements")
            return emptyFlow()
        }
    }

    suspend fun insertCharacters(characters: List<Character>) {
        try {
            dao.insert(characters)
        } catch (ex: SQLException) {
            Log.d("Error SQL", "vThe SQL query could not be executed")
        }
    }

    fun deleteAll() {
        try {
            dao.deleteAll()
        } catch (ex: SQLException) {
            Log.d("Error SQL", "The SQL query could not be executed")
        }
    }
}
