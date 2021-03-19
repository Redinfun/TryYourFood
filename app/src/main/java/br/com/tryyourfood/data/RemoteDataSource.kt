package br.com.tryyourfood.data

import br.com.tryyourfood.data.network.FoodRecipesApi
import br.com.tryyourfood.model.FoodJoke
import br.com.tryyourfood.model.FoodRecipe
import retrofit2.Response
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val foodRecipesApi: FoodRecipesApi
) {

    suspend fun getRecipes(queries: Map<String, String>): Response<FoodRecipe> {
        return foodRecipesApi.getRecipes(queries)
    }

    suspend fun getSearchRecipes(queries: Map<String, String>): Response<FoodRecipe> {
        return foodRecipesApi.searchRecipes(queries)
    }

    suspend fun getFoodJoke(apiKey: String): Response<FoodJoke> {
        return foodRecipesApi.getFoodJoke(apiKey)
    }

}