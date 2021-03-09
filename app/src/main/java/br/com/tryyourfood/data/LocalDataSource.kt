package br.com.tryyourfood.data

import br.com.tryyourfood.data.database.dao.RecipesDao
import br.com.tryyourfood.data.database.entities.RecipesEntity
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class LocalDataSource @Inject constructor(private val recipesDao: RecipesDao) {
    fun readDatabase(): Flow<List<RecipesEntity>>{
        return recipesDao.readRecipes()
    }

    suspend fun insertRecipes(recipesEntity: RecipesEntity){
        recipesDao.insertRecipe(recipesEntity)
    }
}