package br.com.tryyourfood.model

import com.google.gson.annotations.SerializedName

data class FoodJoke (
    @SerializedName("text")
    val text: String
)