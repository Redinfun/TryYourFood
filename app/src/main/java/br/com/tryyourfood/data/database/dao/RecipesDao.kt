package br.com.tryyourfood.data.database.dao

import androidx.room.*
import br.com.tryyourfood.data.database.entities.FavoriteEntity
import br.com.tryyourfood.data.database.entities.RecipesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface RecipesDao {

    // Recipes
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipesEntity: RecipesEntity)

    @Query("SELECT * FROM recipes_table ORDER BY id DESC")
    fun readRecipes(): Flow<List<RecipesEntity>>

    // Favorite
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertFavoriteRecipe(favoriteEntity: FavoriteEntity)

    @Query("SELECT * FROM favorite_recipe_table ORDER BY id")
    fun readFavoriteRecipes():Flow<List<FavoriteEntity>>

    @Delete
    suspend fun deleteFavorite(favoriteEntity: FavoriteEntity)

    @Query("DELETE FROM FAVORITE_RECIPE_TABLE")
    suspend fun deleteAllFavoriteRecipes()



}