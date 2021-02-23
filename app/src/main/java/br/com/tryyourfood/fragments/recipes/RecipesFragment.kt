package br.com.tryyourfood.fragments.recipes

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import br.com.tryyourfood.R
import kotlinx.android.synthetic.main.fragment_recipes.*
import kotlinx.android.synthetic.main.fragment_recipes.view.*

class RecipesFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view =  inflater.inflate(R.layout.fragment_recipes, container, false)
        view.shimmer_recyclerView_recipesFragment_id.showShimmer()
// endpoint https://api.spoonacular.com/recipes/complexSearch?number=1&apiKey=45334d825076402ba6ff877e8ef65cd6&addRecipeInformation=true&fillIngredients=true
        return view
    }

}