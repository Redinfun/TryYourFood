package br.com.tryyourfood.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import br.com.tryyourfood.R
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_main.*

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setTheme(R.style.Theme_TryYourFood)
        setContentView(R.layout.activity_main)

        navController = findNavController(R.id.nav_host_id)
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.recipesFragment_nav_id,
                R.id.favoriteRecipesFragment_nav_id,
                R.id.foodJokeFragment_nav_id
            )
        )

        bottomNavigationView_id.setupWithNavController(navController)
        setupActionBarWithNavController(navController, appBarConfiguration)

    }

    override fun onSupportNavigateUp(): Boolean {
        return navController.navigateUp() || super.onSupportNavigateUp()
    }
}