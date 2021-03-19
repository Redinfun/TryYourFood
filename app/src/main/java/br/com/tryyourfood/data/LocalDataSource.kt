package br.com.tryyourfood.data

import br.com.tryyourfood.data.database.dao.RecipesDao
import br.com.tryyourfood.data.database.entities.FavoriteEntity
import br.com.tryyourfood.data.database.entities.FoodJokeEntity
import br.com.tryyourfood.data.database.entities.RecipesEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val recipesDao: RecipesDao) {

    //RecipesDao

    fun readRecipes(): Flow<List<RecipesEntity>> {
        return recipesDao.readRecipes()
    }

    suspend fun insertRecipes(recipesEntity: RecipesEntity) {
        recipesDao.insertRecipe(recipesEntity)
    }

    // FavoritesDao

    fun readFavoritesRecipes(): Flow<List<FavoriteEntity>> {
        return recipesDao.readFavoriteRecipes()
    }

    suspend fun insertFavoriteRecipe(favoriteEntity: FavoriteEntity) {
        recipesDao.insertFavoriteRecipe(favoriteEntity)
    }

    suspend fun deleteFavoriteRecipe(favoriteEntity: FavoriteEntity) {
        recipesDao.deleteFavorite(favoriteEntity)
    }

    suspend fun deleteAllFavoriteRecipe() {
        recipesDao.deleteAllFavoriteRecipes()
    }

    // FoodJokeDao
    suspend fun insertFoodJoke(foodJokeEntity: FoodJokeEntity) {
        recipesDao.insertFoodJoke(foodJokeEntity)
    }

     fun readDataFoodJoke(): Flow<List<FoodJokeEntity>> {
        return recipesDao.readFoodJoke()
    }


}