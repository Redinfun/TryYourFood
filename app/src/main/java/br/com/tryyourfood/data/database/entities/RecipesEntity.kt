package br.com.tryyourfood.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import br.com.tryyourfood.model.FoodRecipe
import br.com.tryyourfood.utils.Constants.Companion.RECIPES_TABLE_NAME

@Entity(tableName = RECIPES_TABLE_NAME)
class RecipesEntity(var foodRecipe: FoodRecipe) {
    @PrimaryKey(autoGenerate = false)
    var id: Int = 0

}