package br.com.tryyourfood.adapter

import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentActivity
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import br.com.tryyourfood.R
import br.com.tryyourfood.data.database.entities.FavoriteEntity
import br.com.tryyourfood.databinding.ItemFavoriteRecipesRowBinding
import br.com.tryyourfood.fragments.favorite.FavoriteRecipesFragmentDirections
import br.com.tryyourfood.utils.RecipesDiffUtil
import kotlinx.android.synthetic.main.item_favorite_recipes_row.view.*

class FavoriteRecipesAdapter(private val requireActivity: FragmentActivity) :
    RecyclerView.Adapter<FavoriteRecipesAdapter.MyViewHolder>(),
    ActionMode.Callback {

    private var favoritesList = emptyList<FavoriteEntity>()

    private var multiSelection = false
    private var favoriteListSelected = arrayListOf<FavoriteEntity>()
    private var myViewHolders = arrayListOf<MyViewHolder>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        myViewHolders.add(holder)
        val selectedRecipe = favoritesList.get(position)
        holder.bind(selectedRecipe)

        /* OnClickListener */
        holder.itemView.item_favoritesRow_layout.setOnClickListener {
            if (multiSelection) {
                applySelection(holder, selectedRecipe)
            } else {
                val action =
                    FavoriteRecipesFragmentDirections.actionFavoriteRecipesFragmentNavIdToDetailsActivity(
                        selectedRecipe.result
                    )
                holder.itemView.findNavController().navigate(action)
            }

        }

        /* OnLongClickListener */
        holder.itemView.item_favoritesRow_layout.setOnLongClickListener {
            if (!multiSelection) {
                multiSelection = true
                requireActivity.startActionMode(this)
                applySelection(holder, selectedRecipe)
                true
            } else {
                multiSelection = false
                false
            }
        }

    }

    override fun getItemCount(): Int {
        return favoritesList.size
    }

    fun setData(newFavoriteRecipes: List<FavoriteEntity>) {
        val favoriteDiffUtil = RecipesDiffUtil(favoritesList, newFavoriteRecipes)
        val diffUtilResult = DiffUtil.calculateDiff(favoriteDiffUtil)
        favoritesList = newFavoriteRecipes
        diffUtilResult.dispatchUpdatesTo(this)
    }

    private fun applySelection(holder: MyViewHolder, currentRecipe: FavoriteEntity) {
        if (favoriteListSelected.contains(currentRecipe)) {
            favoriteListSelected.remove(currentRecipe)
            changeRecipeStyle(holder, R.color.white, R.color.lightMediumGray)
        } else {
            favoriteListSelected.add(currentRecipe)
            changeRecipeStyle(holder, R.color.backgroundCardColor, R.color.colorPrimaryDark)
        }
    }

    private fun changeRecipeStyle(holder: MyViewHolder, backgroundColor: Int, strokeColor: Int) {
        holder.itemView.item_favoritesRow_layout.setBackgroundColor(
            ContextCompat.getColor(requireActivity, backgroundColor)
        )

        holder.itemView.materialCard_favoriteRow_layout_id.strokeColor =
            ContextCompat.getColor(requireActivity, strokeColor)
    }

    class MyViewHolder(private val binding: ItemFavoriteRecipesRowBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(favoriteEntity: FavoriteEntity) {
            binding.favoritesEntity = favoriteEntity
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemFavoriteRecipesRowBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }
    }

    override fun onCreateActionMode(actionMode: ActionMode?, menu: Menu?): Boolean {
        actionMode?.menuInflater?.inflate(R.menu.menu_favorite_longclick, menu)
        applyStatusBarColor(R.color.contextualStatusBarColor)
        return true
    }

    override fun onPrepareActionMode(actionMode: ActionMode?, menu: Menu?): Boolean {
        return true
    }

    override fun onActionItemClicked(actionMode: ActionMode?, menu: MenuItem?): Boolean {
        return true
    }

    override fun onDestroyActionMode(actionMode: ActionMode?) {
        myViewHolders.forEach { holder ->
            changeRecipeStyle(holder, R.color.white, R.color.lightMediumGray)

        }
        multiSelection = false
        favoriteListSelected.clear()
        applyStatusBarColor(R.color.statusBarColor)
    }

    private fun applyStatusBarColor(color: Int) {
        requireActivity.window.statusBarColor =
            ContextCompat.getColor(requireActivity, color)
    }
}
