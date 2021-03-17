package br.com.tryyourfood.fragments.instructions

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebViewClient
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import br.com.tryyourfood.R
import br.com.tryyourfood.listeners.NetworkListener
import br.com.tryyourfood.model.Result
import br.com.tryyourfood.utils.Constants.Companion.RECIPE_BUNDLE_KEY
import br.com.tryyourfood.viewmodel.RecipesViewModel
import kotlinx.android.synthetic.main.fragment_instructions.view.*
import kotlinx.coroutines.ExperimentalCoroutinesApi

class InstructionsFragment : Fragment() {

    private val recipesViewModel: RecipesViewModel by viewModels()
    @ExperimentalCoroutinesApi
    private lateinit var networkListener: NetworkListener

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


            return view
    }


}