package br.com.tryyourfood.fragments.joke

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.observe
import br.com.tryyourfood.R
import br.com.tryyourfood.databinding.FragmentFoodJokeBinding
import br.com.tryyourfood.utils.Constants.Companion.API_KEY
import br.com.tryyourfood.utils.Constants.Companion.myLogTag
import br.com.tryyourfood.utils.NetworkResult
import br.com.tryyourfood.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@Suppress("NULLABILITY_MISMATCH_BASED_ON_JAVA_ANNOTATIONS")
@AndroidEntryPoint
class FoodJokeFragment : Fragment() {
    private val mainViewModel by viewModels<MainViewModel>()

    private var _binding: FragmentFoodJokeBinding? = null
    private val binding get() = _binding!!

    private var foodJoke = "No Food Joke"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFoodJokeBinding.inflate(inflater, container, false)
        binding.lifecycleOwner = viewLifecycleOwner
        binding.mainViewModel = mainViewModel

        setHasOptionsMenu(true)

        mainViewModel.getFoodJoke(API_KEY)
        mainViewModel.foodJokeResponse.observe(viewLifecycleOwner) { response ->
            when (response) {

                is NetworkResult.Success -> {
                    binding.foodJokeTextViewFragmentId.text = response.data?.text
                    foodJoke = response.data!!.text
                }
                is NetworkResult.Error -> {
                    Snackbar.make(binding.root, response.message.toString(), Snackbar.LENGTH_SHORT)
                        .show()

                    loadDataFromCache()
                }

                is NetworkResult.Loading -> {
                    Log.d(myLogTag(FoodJokeFragment::class.java.canonicalName), "Loading: ")
                }

            }
        }


        return binding.root
    }

    private fun loadDataFromCache() {
        lifecycleScope.launch {

            mainViewModel.readFoodJoke.observe(viewLifecycleOwner) { database ->
                if (database.isNotEmpty() && database != null) {
                    binding.foodJokeTextViewFragmentId.text = database[0].foodJoke.text
                    foodJoke = database[0].foodJoke.text
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.share_foodjoke_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if(item.itemId == R.id.share_foodJoke_menu_id){
            val shareIntent = Intent().apply {
                this.action = Intent.ACTION_SEND
                this.putExtra(Intent.EXTRA_TEXT,foodJoke)
                this.type = "text/plain"
            }
            startActivity(shareIntent)
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

}