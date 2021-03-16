package br.com.tryyourfood.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import br.com.tryyourfood.data.database.entities.FavoriteEntity
import br.com.tryyourfood.databinding.ItemFavoriteRecipesRowBinding
import br.com.tryyourfood.utils.RecipesDiffUtil

class FavoriteRecipesAdapter: RecyclerView.Adapter<FavoriteRecipesAdapter.MyViewHolder>() {
    private var favoritesList = emptyList<FavoriteEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val selectedRecipe = favoritesList.get(position)
        holder.bind(selectedRecipe)
    }

    override fun getItemCount(): Int {
        return favoritesList.size
    }

    fun setData(newFavoriteRecipes:List<FavoriteEntity>){
        val favoriteDiffUtil = RecipesDiffUtil(favoritesList,newFavoriteRecipes)
        val diffUtilResult = DiffUtil.calculateDiff(favoriteDiffUtil)
        favoritesList = newFavoriteRecipes
        diffUtilResult.dispatchUpdatesTo(this)
    }

    class MyViewHolder(private val binding:ItemFavoriteRecipesRowBinding): RecyclerView.ViewHolder(binding.root) {
        fun bind(favoriteEntity: FavoriteEntity){
            binding.favoritesEntity = favoriteEntity
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup):MyViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemFavoriteRecipesRowBinding.inflate(layoutInflater,parent,false)
                return MyViewHolder(binding)
            }
        }
    }
}
