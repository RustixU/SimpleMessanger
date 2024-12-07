package rut.miit.simplemessanger.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "characters")
data class Character(
    @PrimaryKey(autoGenerate = true) val id: Int = 0,
    @ColumnInfo(name = "name") val name: String?,
    @ColumnInfo(name = "culture") val culture: String?,
    @ColumnInfo(name = "born") val born: String?,
    @ColumnInfo(name = "titles") val titles: List<String>?,
    @ColumnInfo(name = "aliases") val aliases: List<String>?,
    @ColumnInfo(name = "played_by") val playedBy: List<String>?
)
//{
//    companion object {
//        fun from(
//            name: String,
//            culture: String,
//            born: String,
//            titles: List<String>,
//            aliases: List<String>,
//            playedBy: List<String>
//        ): Character {
//            return Character(
//                name = name,
//                culture = culture,
//                born = born,
//                titles = titles,
//                aliases = aliases,
//                playedBy = playedBy
//            )
//        }
//    }
//}
