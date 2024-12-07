package rut.miit.simplemessanger.repositories

import kotlinx.coroutines.flow.Flow
import rut.miit.simplemessanger.dao.CharacterDao
import rut.miit.simplemessanger.entity.Character

class CharacterRepository(private val dao: CharacterDao) {

    fun getCharacters(): Flow<List<Character>> = dao.getAll()

    suspend fun insertCharacters(characters: List<Character>) {
        dao.insert(characters)
    }

    fun deleteAll() {
        dao.deleteAll()
    }
}