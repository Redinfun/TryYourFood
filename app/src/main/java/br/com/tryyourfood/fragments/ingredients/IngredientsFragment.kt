package br.com.tryyourfood.fragments.ingredients

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.tryyourfood.R
import br.com.tryyourfood.adapter.IngredientsAdapter
import br.com.tryyourfood.model.Result
import br.com.tryyourfood.utils.Constants.Companion.RECIPE_BUNDLE_KEY
import kotlinx.android.synthetic.main.fragment_ingredients.view.*

class IngredientsFragment : Fragment() {
    private val mAdapter: IngredientsAdapter by lazy { IngredientsAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_ingredients, container, false)

        val args = arguments
        val myBundle: Result? = args?.getParcelable(RECIPE_BUNDLE_KEY)

        setupRecyclerView(view)
        myBundle?.extendedIngredients?.let {
            mAdapter.setData(it)
        }

        return view
    }

    private fun setupRecyclerView(view: View) {
        view.recyclerView_ingredients_id.adapter = mAdapter
        view.recyclerView_ingredients_id.layoutManager = LinearLayoutManager(requireContext())
    }

}