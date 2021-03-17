package br.com.tryyourfood.fragments.instructions

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import br.com.tryyourfood.R
import br.com.tryyourfood.listeners.NetworkListener
import br.com.tryyourfood.model.Result
import br.com.tryyourfood.utils.Constants.Companion.RECIPE_BUNDLE_KEY
import br.com.tryyourfood.viewmodel.RecipesViewModel
import kotlinx.android.synthetic.main.fragment_instructions.view.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect

class InstructionsFragment : Fragment() {

    private lateinit var recipesViewModel: RecipesViewModel

    @ExperimentalCoroutinesApi
    private lateinit var networkListener: NetworkListener

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recipesViewModel = ViewModelProvider(requireActivity()).get(RecipesViewModel::class.java)
    }

    @ExperimentalCoroutinesApi
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_instructions, container, false)

        val args = arguments
        val myBundle: Result? = args?.getParcelable(RECIPE_BUNDLE_KEY)

        view.instructionsWebView_id.webViewClient = object : WebViewClient() {}
        val webSiteUrl: String = myBundle!!.sourceUrl
        view.instructionsWebView_id.loadUrl(webSiteUrl)

        lifecycleScope.launchWhenStarted {
            networkListener = NetworkListener()
            networkListener.checkNetWorkAvailability(requireContext())
                .collect { status ->
                    Log.i("TAG", "onCreateView: " + status.toString())
                    recipesViewModel.networkStatus = status
                    if (!status) {
                        view.instructionsWebView_id.visibility = View.GONE
                        view.imageView_noConnection_Ingredients_id.visibility = View.VISIBLE
                        view.textView_noConnection_Ingredients_id.visibility = View.VISIBLE
                    } else {
                        view.instructionsWebView_id.visibility = View.VISIBLE
                        view.imageView_noConnection_Ingredients_id.visibility = View.GONE
                        view.textView_noConnection_Ingredients_id.visibility = View.GONE
                    }
                    recipesViewModel.showNetworkStatus()

                }
        }

        return view
    }


}