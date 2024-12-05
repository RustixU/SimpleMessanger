package rut.miit.simplemessanger.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.SerialName

@Entity(tableName = "characters")
data class Character(
    @PrimaryKey(autoGenerate = true) val id: Int,
    val name: String = "Unknown",
    val culture: String = "Unknown",
    val born: String = "Unknown",
    val titles: List<String> = emptyList(),
    val aliases: List<String> = emptyList(),
    @ColumnInfo(name = "played_by") val playedBy: List<String> = emptyList()
)
