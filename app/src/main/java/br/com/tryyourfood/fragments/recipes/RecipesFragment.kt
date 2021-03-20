package br.com.tryyourfood.fragments.recipes

import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import br.com.tryyourfood.R
import br.com.tryyourfood.adapter.RecipesAdapter
import br.com.tryyourfood.databinding.FragmentRecipesBinding
import br.com.tryyourfood.listeners.NetworkListener
import br.com.tryyourfood.utils.NetworkResult
import br.com.tryyourfood.utils.observeOnce
import br.com.tryyourfood.viewmodel.MainViewModel
import br.com.tryyourfood.viewmodel.RecipesViewModel
import com.google.android.material.snackbar.Snackbar
import kotlinx.android.synthetic.main.fragment_recipes.view.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch

@ExperimentalCoroutinesApi
@RequiresApi(Build.VERSION_CODES.M)
class RecipesFragment : Fragment(), androidx.appcompat.widget.SearchView.OnQueryTextListener {

    private val args by navArgs<RecipesFragmentArgs>()

    private var _binding: FragmentRecipesBinding? = null
    private val binding get() = _binding!!

    private lateinit var mainViewModel: MainViewModel
    private lateinit var recipesViewModel: RecipesViewModel
    private val mAdapter by lazy { RecipesAdapter() }

    private lateinit var networkListener: NetworkListener

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
        _binding = FragmentRecipesBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = this
        binding.mainViewModel = mainViewModel

        setHasOptionsMenu(true)
        setupRecyclerView()

        recipesViewModel.readBackOnline.observe(viewLifecycleOwner) {
            recipesViewModel.backOnline = it
        }

        lifecycleScope.launchWhenStarted {

            networkListener = NetworkListener()
            networkListener.checkNetWorkAvailability(requireContext())
                .collect { status ->
                    Log.i("TAG", "onCreateView: " + status.toString())
                    recipesViewModel.networkStatus = status
                    if (!status) {
                        binding.root.fab_recipesFragment_id.setColorFilter(
                            requireContext().getColor(
                                R.color.darkblue
                            )
                        )
                    } else {
                        binding.root.fab_recipesFragment_id.setColorFilter(
                            requireContext().getColor(
                                R.color.white
                            )
                        )
                    }
                    recipesViewModel.showNetworkStatus()
                    readDatabase()
                }
        }

        binding.fabRecipesFragmentId.setOnClickListener {
            if (recipesViewModel.networkStatus) {
                findNavController().navigate(R.id.action_recipesFragment_nav_id_to_recipeBottomSheet)
            } else {
                recipesViewModel.showNetworkStatus()

            }

        }
        return binding.root
    }

    private fun readDatabase() {

        lifecycleScope.launchWhenStarted {
            mainViewModel.readRecipes.observe(viewLifecycleOwner) { database ->
                if (database.isNotEmpty() && !args.backToRecipeFragment) {
                    mAdapter.setData(database[0].foodRecipe)
                    hideShimmer()
                    Log.i("TAG", "readDatabase: ")
                } else {
                    requestApiData()
                }
            }
        }
    }


    private fun requestApiData() {
        Log.i("TAG", "requestApiData: ")
        mainViewModel.getRecipes(recipesViewModel.applyQueries())
        mainViewModel.recipesResponse.observe(viewLifecycleOwner) { response ->

            when (response) {
                is NetworkResult.Success -> {
                    hideShimmer()
                    response.data?.let { mAdapter.setData(it) }
                }
                is NetworkResult.Error -> {
                    hideShimmer()
                    loadDataFromCache()
                    Snackbar.make(binding.root, response.message.toString(), Snackbar.LENGTH_LONG)
                        .show()
                }

                is NetworkResult.Loading -> {
                    showShimmerEffect()
                }
            }
        }
    }

    private fun searchApiData(searchQuery: String) {
        showShimmerEffect()
        mainViewModel.searchRecipes(recipesViewModel.applySearchQuery(searchQuery))
        mainViewModel.searchedRecipesResponse.observe((viewLifecycleOwner)) { response ->
            when (response) {
                is NetworkResult.Success -> {
                    hideShimmer()
                    val foodRecipe = response.data
                    foodRecipe?.let {
                        mAdapter.setData(it)
                    }
                }
                is NetworkResult.Error -> {
                    hideShimmer()
                    loadDataFromCache()
                    Snackbar.make(binding.root, response.message.toString(), Snackbar.LENGTH_SHORT)
                        .setAnimationMode(Snackbar.ANIMATION_MODE_FADE).show()
                }
                is NetworkResult.Loading ->{
                    showShimmerEffect()
                }
            }
        }
    }

    private fun loadDataFromCache() {
        lifecycleScope.launch {
            mainViewModel.readRecipes.observe(viewLifecycleOwner) { database ->
                if (database.isNotEmpty()) {
                    mAdapter.setData(database[0].foodRecipe)
                }
            }
        }
    }


    private fun showShimmerEffect() {
        binding.root.shimmer_recyclerView_recipesFragment_id.showShimmer()
    }

    private fun hideShimmer() {
        binding.root.shimmer_recyclerView_recipesFragment_id.hideShimmer()
    }

    private fun setupRecyclerView() {
        binding.root.shimmer_recyclerView_recipesFragment_id.adapter = mAdapter
        binding.root.shimmer_recyclerView_recipesFragment_id.layoutManager =
            LinearLayoutManager(requireContext())
        showShimmerEffect()
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_recipes_search, menu)
        val search = menu.findItem(R.id.recipes_search_menu_id)
        val searchView = search.actionView as? androidx.appcompat.widget.SearchView
        searchView?.isSubmitButtonEnabled = true
        searchView?.setOnQueryTextListener(this)
        super.onCreateOptionsMenu(menu, inflater)

    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        if(query != null){
            searchApiData(query)
        }
        return true
    }

    override fun onQueryTextChange(p0: String?): Boolean {
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}