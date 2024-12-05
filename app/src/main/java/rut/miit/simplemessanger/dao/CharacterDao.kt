package rut.miit.simplemessanger.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import rut.miit.simplemessanger.entity.Character

@Dao
interface CharacterDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCharacters(characters: List<Character>)

    @Query("SELECT * FROM characters")
    fun getAllCharacters(): List<Character>

    @Query("DELETE FROM characters")
    suspend fun clearCharacters()
}