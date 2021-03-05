package br.com.tryyourfood.utils

class Constants {

    companion object {
        //apontamento para API
        const val BASE_URL = "https://api.spoonacular.com"

        //chave de api
        const val API_KEY = "45334d825076402ba6ff877e8ef65cd6"

        //Api query keys
        const val QUERY_NUMBER = "number"
        const val QUERY_API_KEY = "apiKey"
        const val QUERY_TYPE = "type"
        const val QUERY_DIET = "diet"
        const val QUERY_ADD_RECIPE_INFORMATION = "addRecipeInformation"
        const val QUERY_FILL_INGREDIENTS = "fillIngredients"

        //Room
        const val RECIPES_TABLE_NAME = "recipes_table"
        const val RECIPES_DATABASE_NAME = "recipes_database"

        //Bottom Sheet and Preferences
        const val PREFERENCES_NAME = "foody_preferences"
        const val DEFAULT_MEAL_TYPE = "main_course"
        const val DEFAULT_DIET_TYPE = "gluten free"
        const val DEFAULT_RECIPES_NUMBER = "50"
        const val PREFERENCES_MEAL_TYPE = "mealType"
        const val PREFERENCES_MEAL_TYPE_ID = "mealTypeId"
        const val PREFERENCES_DIET_TYPE = "dietType"
        const val PREFERENCES_DIET_TYPE_ID = "dietTypeId"
    }
}