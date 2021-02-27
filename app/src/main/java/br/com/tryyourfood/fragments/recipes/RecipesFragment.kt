package br.com.tryyourfood.fragments.recipes

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.tryyourfood.viewmodel.MainViewModel
import br.com.tryyourfood.R
import br.com.tryyourfood.adapter.RecipesAdapter
import br.com.tryyourfood.utils.Constants.Companion.API_KEY
import br.com.tryyourfood.utils.NetworkResult
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_recipes.view.*

@RequiresApi(Build.VERSION_CODES.M)
class RecipesFragment : Fragment() {

    private lateinit var mView: View
    private lateinit var mainViewModel: MainViewModel
    private val mAdapter by lazy { RecipesAdapter() }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
         mView = inflater.inflate(R.layout.fragment_recipes, container, false)

        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)

        setupRecyclerView()
        requestApiData()
        return mView
    }


    private fun requestApiData() {
        mainViewModel.getRecipes(applyQueries())
        mainViewModel.recipesResponse.observe(viewLifecycleOwner, { response ->

            when (response) {
                is NetworkResult.Success -> {
                    hideShimmer()
                    response.data?.let { mAdapter.setData(it) }
                }
                is NetworkResult.Error -> {
                  hideShimmer()
                  //Toast.makeText(requireContext(),response.message,Toast.LENGTH_SHORT).show()
                    Snackbar.make(mView,response.message.toString(),Snackbar.LENGTH_LONG).show()
                }

                is NetworkResult.Loading -> {
                    showShimmerEffect()
                }
            }
        })
    }

    private fun applyQueries(): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()
        queries["number"] = "100"
        queries["apiKey"] = API_KEY
        queries["type"] = "snack"
        //queries["diet"] = "vegan"
        queries["addRecipeInformation"] = "true"
        queries["fillIngredients"] = "true"

        return queries
    }

    private fun showShimmerEffect() {
        mView.shimmer_recyclerView_recipesFragment_id.showShimmer()
    }

    private fun hideShimmer() {
        mView.shimmer_recyclerView_recipesFragment_id.hideShimmer()
    }

    private fun setupRecyclerView() {
        mView.shimmer_recyclerView_recipesFragment_id.adapter = mAdapter
        mView.shimmer_recyclerView_recipesFragment_id.layoutManager =
            LinearLayoutManager(requireContext())
        showShimmerEffect()
    }

}