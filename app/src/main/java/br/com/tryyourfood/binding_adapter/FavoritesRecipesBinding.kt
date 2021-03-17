package br.com.tryyourfood.binding_adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import br.com.tryyourfood.adapter.FavoriteRecipesAdapter
import br.com.tryyourfood.data.database.entities.FavoriteEntity

class FavoritesRecipesBinding {
    companion object {

        @BindingAdapter("viewVisibility", "setData", requireAll = false)
        @JvmStatic
        fun setDAtaAndViewVisibility(
            view: View,
            favoriteEntity: List<FavoriteEntity>?,
            mAdapter: FavoriteRecipesAdapter?
        ) {
            if (favoriteEntity.isNullOrEmpty()) {
                when (view) {
                    is ImageView -> {
                        view.visibility = View.VISIBLE
                    }
                    is TextView -> {
                        view.visibility = View.VISIBLE
                    }
                    is RecyclerView -> {
                        view.visibility = View.GONE
                    }
                }
            } else {
                when (view) {
                    is ImageView -> {
                        view.visibility = View.GONE
                    }
                    is TextView -> {
                        view.visibility = View.GONE
                    }
                    is RecyclerView -> {
                        view.visibility = View.VISIBLE
                        mAdapter?.setData(favoriteEntity)
                    }
                }
            }
        }
    }
}
