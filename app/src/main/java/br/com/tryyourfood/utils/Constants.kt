package br.com.tryyourfood.utils

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
class Constants {

    companion object {
        //Apontamento para API
        const val BASE_URL = "https://api.spoonacular.com"
        const val BASE_IMAGE_URL = "https://spoonacular.com/cdn/ingredients_100x100/"

        //Chave de api
        const val API_KEY = "45334d825076402ba6ff877e8ef65cd6"

        //Bundle Fragments Keys
        const val RECIPE_BUNDLE_KEY = "recipeBundle"

        //Api query keys
        const val QUERY_SEARCH = "query"
        const val QUERY_NUMBER = "number"
        const val QUERY_API_KEY = "apiKey"
        const val QUERY_TYPE = "type"
        const val QUERY_DIET = "diet"
        const val QUERY_ADD_RECIPE_INFORMATION = "addRecipeInformation"
        const val QUERY_FILL_INGREDIENTS = "fillIngredients"

        //Room
        const val RECIPES_TABLE_NAME = "recipes_table"
        const val RECIPES_DATABASE_NAME = "recipes_database"
        const val FAVORITES_TABLE_NAME = "favorite_recipe_table"
        const val FOOD_JOKE_TABLE = "food_joke_table"

        //Bottom Sheet and Preferences
        const val PREFERENCES_NAME = "foody_preferences"
        const val DEFAULT_MEAL_TYPE = "snack"
        const val DEFAULT_DIET_TYPE = "gluten free"
        const val DEFAULT_RECIPES_NUMBER = "50"
        const val PREFERENCES_MEAL_TYPE = "mealType"
        const val PREFERENCES_MEAL_TYPE_ID = "mealTypeId"
        const val PREFERENCES_DIET_TYPE = "dietType"
        const val PREFERENCES_DIET_TYPE_ID = "dietTypeId"
        const val PREFERENCES_BACK_ONLINE = "backOnline"

        //Tag to logs
        fun myLogTag(className: String): String {
            return className::class.java.canonicalName
        }
    }
}