package br.com.tryyourfood.ui

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.navArgs
import br.com.tryyourfood.R
import br.com.tryyourfood.adapter.PageAdapter
import br.com.tryyourfood.data.database.entities.FavoriteEntity
import br.com.tryyourfood.fragments.ingredients.IngredientsFragment
import br.com.tryyourfood.fragments.instructions.InstructionsFragment
import br.com.tryyourfood.fragments.overview.OverviewFragment
import br.com.tryyourfood.utils.Constants.Companion.RECIPE_BUNDLE_KEY
import br.com.tryyourfood.viewmodel.MainViewModel
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_details.*

@AndroidEntryPoint
class DetailsActivity : AppCompatActivity() {
    private val args by navArgs<DetailsActivityArgs>()
    private val mainViewModel: MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        setSupportActionBar(toolbar_Details_id)
        toolbar_Details_id.setTitleTextColor(ContextCompat.getColor(this, R.color.white))
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val fragments = ArrayList<Fragment>()
        fragments.add(OverviewFragment())
        fragments.add(IngredientsFragment())
        fragments.add(InstructionsFragment())

        val titles = ArrayList<String>()
        titles.add("Overview")
        titles.add("Ingredients")
        titles.add("Instructions")

        val resultBundle = Bundle()
        resultBundle.putParcelable(RECIPE_BUNDLE_KEY, args.result)

        val adapter = PageAdapter(
            resultBundle,
            fragments,
            titles,
            supportFragmentManager
        )

        viewPager_Details_id.adapter = adapter
        tabLayout_Details_id.setupWithViewPager(viewPager_Details_id)

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_favorite_recipe, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            finish()
        } else if (item.itemId == R.id.save_favorite_menu_id) {
            saveToFavorites(item)
        }
        return super.onOptionsItemSelected(item)

    }

    private fun saveToFavorites(item: MenuItem) {
        val favoriteEntity = FavoriteEntity(0, args.result)
        mainViewModel.insertFavoriteRecipe(favoriteEntity)
        changeMenuItemColor(item, R.color.yellow)
        showSnackBar("Recipe Saved!")
    }

    private fun showSnackBar(message: String) {
        Snackbar.make(detailsLayout_id, message, Snackbar.LENGTH_SHORT)
            .setAnimationMode(Snackbar.ANIMATION_MODE_FADE)
            .setAction("Okay") {}
            .show()
    }

    private fun changeMenuItemColor(item: MenuItem, color: Int) {
        item.icon.setTint(ContextCompat.getColor(this, color))
    }
}