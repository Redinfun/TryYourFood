package br.com.tryyourfood.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import br.com.tryyourfood.data.database.dao.RecipesDao
import br.com.tryyourfood.data.database.entities.FavoriteEntity
import br.com.tryyourfood.data.database.entities.RecipesEntity
import br.com.tryyourfood.utils.RecipesTypeConverter

@Database(
    entities = [RecipesEntity::class, FavoriteEntity::class],
    version = 1,
    exportSchema = true
)

@TypeConverters(RecipesTypeConverter::class)
abstract class RecipesDatabase : RoomDatabase() {
    abstract fun recipesDao(): RecipesDao
}