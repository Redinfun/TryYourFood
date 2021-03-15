package br.com.tryyourfood.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.tryyourfood.model.Result
import br.com.tryyourfood.utils.Constants.Companion.FAVORITES_TABLE_NAME

@Entity(tableName = FAVORITES_TABLE_NAME)
class FavoriteEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var result: Result
)