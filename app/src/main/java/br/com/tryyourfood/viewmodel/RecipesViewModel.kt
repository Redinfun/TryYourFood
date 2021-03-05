package br.com.tryyourfood.viewmodel

import android.app.Application
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import br.com.tryyourfood.data.repository.DataStoreRepository
import br.com.tryyourfood.utils.Constants.Companion.API_KEY
import br.com.tryyourfood.utils.Constants.Companion.DEFAULT_DIET_TYPE
import br.com.tryyourfood.utils.Constants.Companion.DEFAULT_MEAL_TYPE
import br.com.tryyourfood.utils.Constants.Companion.DEFAULT_RECIPES_NUMBER
import br.com.tryyourfood.utils.Constants.Companion.QUERY_ADD_RECIPE_INFORMATION
import br.com.tryyourfood.utils.Constants.Companion.QUERY_API_KEY
import br.com.tryyourfood.utils.Constants.Companion.QUERY_DIET
import br.com.tryyourfood.utils.Constants.Companion.QUERY_FILL_INGREDIENTS
import br.com.tryyourfood.utils.Constants.Companion.QUERY_NUMBER
import br.com.tryyourfood.utils.Constants.Companion.QUERY_TYPE
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

class RecipesViewModel @ViewModelInject constructor(
    application: Application,
    private val dataStoreRepository: DataStoreRepository
) :
    AndroidViewModel(application) {
    private var mealType = DEFAULT_MEAL_TYPE
    private var dietType = DEFAULT_DIET_TYPE

    val readMealAndDietType = dataStoreRepository.readMealAndDietType

    fun saveMealAndDietType(mealType: String, mealTypeId: Int, dietType: String, dietTypeId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveMealAndDietType(mealType, mealTypeId, dietType, dietTypeId)
        }
    }

    fun applyQueries(): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()

        viewModelScope.launch {
            readMealAndDietType.collect() { value ->
                mealType = value.selectedMealType
                dietType = value.selectedDietType
            }
        }

        queries[QUERY_NUMBER] = DEFAULT_RECIPES_NUMBER
        queries[QUERY_API_KEY] = API_KEY
        queries[QUERY_TYPE] = mealType
        queries[QUERY_DIET] = dietType
        queries[QUERY_ADD_RECIPE_INFORMATION] = "true"
        queries[QUERY_FILL_INGREDIENTS] = "true"

        return queries

    }
}