package br.com.tryyourfood.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import br.com.tryyourfood.R
import br.com.tryyourfood.model.ExtendedIngredient
import br.com.tryyourfood.utils.Constants.Companion.BASE_IMAGE_URL
import br.com.tryyourfood.utils.RecipesDiffUtil
import coil.load
import kotlinx.android.synthetic.main.item_ingredients_row.view.*
import java.util.*

class IngredientsAdapter : RecyclerView.Adapter<IngredientsAdapter.MyViewHolder>() {

    private var ingredientsList = emptyList<ExtendedIngredient>()

    class MyViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val viewHolder = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_ingredients_row, parent, false)

        return MyViewHolder(viewHolder)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val ingredient = ingredientsList.get(position)
        holder.itemView.imageView_itemIngredients_id.load(BASE_IMAGE_URL + ingredient.image) {
            crossfade(600)
            error(R.drawable.error_placeholder)
        }
        holder.itemView.titleTextView_itemIngredients_id.text =
            ingredient.name?.capitalize(Locale.ROOT)
        holder.itemView.amountTextView_itemIngredients_id.text = ingredient.amount.toString()
        holder.itemView.unitTextView_itemIngredients_id.text =
            ingredient.unit?.capitalize(Locale.ROOT)
        holder.itemView.consistecyTextView_itemIngredients_id.text =
            ingredient.consistency?.capitalize(
                Locale.ROOT
            )
        holder.itemView.originalTextView_itemIngredients_id.text = ingredient.original?.capitalize(
            Locale.ROOT
        )

    }

    override fun getItemCount(): Int {
        return ingredientsList.size
    }

    fun setData(newIngredient: List<ExtendedIngredient>) {
        val ingredientDiffUtil = RecipesDiffUtil(ingredientsList, newIngredient)
        val diffUtilResult = DiffUtil.calculateDiff(ingredientDiffUtil)
        ingredientsList = newIngredient
        diffUtilResult.dispatchUpdatesTo(this)
    }
}