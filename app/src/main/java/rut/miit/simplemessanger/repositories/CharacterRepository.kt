package rut.miit.simplemessanger.repositories

import rut.miit.simplemessanger.dao.CharacterDao
import rut.miit.simplemessanger.entity.Character

class CharacterRepository(private val dao: CharacterDao) {

    fun getCharacters(): List<Character> = dao.getAllCharacters()

    suspend fun insertCharacters(characters: List<Character>) {
        dao.insertCharacters(characters)
    }

    suspend fun clearCharacters() {
        dao.clearCharacters()
    }
}