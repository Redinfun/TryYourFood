package br.com.tryyourfood.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import br.com.tryyourfood.databinding.ItemRecipesRowBinding
import br.com.tryyourfood.model.FoodRecipe
import br.com.tryyourfood.model.Result
import br.com.tryyourfood.utils.RecipesDiffUtil

class RecipesAdapter : RecyclerView.Adapter<RecipesAdapter.MyViewHolder>() {
    private var recipesList = emptyList<Result>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return  MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentRecipe = recipesList[position]
        holder.bind(currentRecipe)

    }

    override fun getItemCount(): Int {
        return recipesList.size
    }

    fun setData(newData:FoodRecipe){
        val recipeDiffUtil = RecipesDiffUtil(recipesList,newData.results)
        val diffUtilResult = DiffUtil.calculateDiff(recipeDiffUtil)
        recipesList = newData.results
        diffUtilResult.dispatchUpdatesTo(this)

    }

    class MyViewHolder(private val binding:ItemRecipesRowBinding):RecyclerView.ViewHolder(binding.root){

        fun bind(result: Result){
            binding.result = result
            binding.executePendingBindings()
        }

        companion object{
            fun from(parent: ViewGroup):MyViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemRecipesRowBinding.inflate(layoutInflater,parent,false)
                return MyViewHolder(binding)
            }
        }


    }
}
