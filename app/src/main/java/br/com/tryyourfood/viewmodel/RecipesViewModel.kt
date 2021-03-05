package br.com.tryyourfood.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import br.com.tryyourfood.utils.Constants
import br.com.tryyourfood.utils.Constants.Companion.DEFAULT_DIET_TYPE
import br.com.tryyourfood.utils.Constants.Companion.DEFAULT_MEAL_TYPE
import br.com.tryyourfood.utils.Constants.Companion.DEFAULT_RECIPES_NUMBER
import br.com.tryyourfood.utils.Constants.Companion.QUERY_ADD_RECIPE_INFORMATION
import br.com.tryyourfood.utils.Constants.Companion.QUERY_API_KEY
import br.com.tryyourfood.utils.Constants.Companion.QUERY_DIET
import br.com.tryyourfood.utils.Constants.Companion.QUERY_FILL_INGREDIENTS
import br.com.tryyourfood.utils.Constants.Companion.QUERY_NUMBER
import br.com.tryyourfood.utils.Constants.Companion.QUERY_TYPE

class RecipesViewModel(application: Application) : AndroidViewModel(application) {

    fun applyQueries(): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()
        queries[QUERY_NUMBER] = DEFAULT_RECIPES_NUMBER
        queries[QUERY_API_KEY] = Constants.API_KEY
        queries[QUERY_TYPE] = DEFAULT_MEAL_TYPE
        queries[QUERY_DIET] = DEFAULT_DIET_TYPE
        queries[QUERY_ADD_RECIPE_INFORMATION] = "true"
        queries[QUERY_FILL_INGREDIENTS] = "true"

        return queries

    }
}