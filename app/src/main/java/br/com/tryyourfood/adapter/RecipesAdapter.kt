package br.com.tryyourfood.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import br.com.tryyourfood.databinding.ItemRecipesRowBinding
import br.com.tryyourfood.model.FoodRecipe
import br.com.tryyourfood.model.Result

class RecipesAdapter : RecyclerView.Adapter<RecipesAdapter.MyViewHolder>() {
    private var recipe = emptyList<Result>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return  MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentResult = recipe[position]
        holder.bind(currentResult)

    }

    override fun getItemCount(): Int {
        return recipe.size
    }

    fun setData(newData:FoodRecipe){
        recipe = newData.results
        notifyDataSetChanged()
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
