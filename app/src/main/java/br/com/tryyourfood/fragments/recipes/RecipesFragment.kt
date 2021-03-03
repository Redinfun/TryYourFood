package br.com.tryyourfood.fragments.recipes

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.tryyourfood.R
import br.com.tryyourfood.adapter.RecipesAdapter
import br.com.tryyourfood.utils.NetworkResult
import br.com.tryyourfood.utils.observeOnce
import br.com.tryyourfood.viewmodel.MainViewModel
import br.com.tryyourfood.viewmodel.RecipesViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_recipes.view.*
import kotlinx.coroutines.launch

@RequiresApi(Build.VERSION_CODES.M)
class RecipesFragment : Fragment() {

    private lateinit var mView: View
    private lateinit var mainViewModel: MainViewModel
    private lateinit var recipesViewModel: RecipesViewModel
    private val mAdapter by lazy { RecipesAdapter() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainViewModel = ViewModelProvider(requireActivity()).get(MainViewModel::class.java)
        recipesViewModel = ViewModelProvider(requireActivity()).get(RecipesViewModel::class.java)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        mView = inflater.inflate(R.layout.fragment_recipes, container, false)

        setupRecyclerView()
        readDatabase()
        return mView
    }

    private fun readDatabase() {

        lifecycleScope.launch {
            mainViewModel.readRecipes.observeOnce(viewLifecycleOwner, { database ->
                if (database.isNotEmpty()) {
                    mAdapter.setData(database[0].foodRecipe)
                    hideShimmer()
                    Log.i("TAG", "readDatabase: ")
                } else {
                    requestApiData()
                }
            })
        }
    }


    private fun requestApiData() {
        Log.i("TAG", "requestApiData: ")
        mainViewModel.getRecipes(recipesViewModel.applyQueries())
        mainViewModel.recipesResponse.observe(viewLifecycleOwner, { response ->

            when (response) {
                is NetworkResult.Success -> {
                    hideShimmer()
                    response.data?.let { mAdapter.setData(it) }
                }
                is NetworkResult.Error -> {
                    hideShimmer()
                    loadDataFromCache()
                    Snackbar.make(mView, response.message.toString(), Snackbar.LENGTH_LONG).show()
                }

                is NetworkResult.Loading -> {
                    showShimmerEffect()
                }
            }
        })
    }

    private fun loadDataFromCache() {
        lifecycleScope.launch {
            mainViewModel.readRecipes.observe(viewLifecycleOwner, { database ->
                if (database.isNotEmpty()) {
                    mAdapter.setData(database[0].foodRecipe)
                }
            })
        }
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