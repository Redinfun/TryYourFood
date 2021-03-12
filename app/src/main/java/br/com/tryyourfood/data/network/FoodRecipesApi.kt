package br.com.tryyourfood.data.network

import br.com.tryyourfood.model.FoodRecipe
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.QueryMap

interface FoodRecipesApi {
    //usar o suspend para usar o Kotlin Coroutines

    @GET("/recipes/complexSearch")
    suspend fun getRecipes(@QueryMap queries: Map<String, String>): Response<FoodRecipe>

    @GET("/recipes/complexSearch")
    suspend fun searchRecipes(@QueryMap searchQuery:Map<String,String>):Response<FoodRecipe>
}