package rut.miit.simplemessanger.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow
import rut.miit.simplemessanger.entity.Character

@Dao
interface CharacterDao {
    @Query("SELECT * FROM characters")
    fun getAll(): Flow<List<Character>>

    @Query("SELECT * FROM characters WHERE id = :id")
    suspend fun getCharacter(id: Int): Character?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(characters: List<Character>)

    @Update
    suspend fun update(character: Character)

    @Delete
    suspend fun delete(character: Character)

    @Query("DELETE FROM characters")
    fun deleteAll()
}