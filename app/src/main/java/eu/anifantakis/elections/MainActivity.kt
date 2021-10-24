package eu.anifantakis.elections

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import eu.anifantakis.elections.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        navController = navHostFragment.navController
        setSupportActionBar(binding.toolbar)

        // we define as top level destinations the login, welcome, instructions, shoesList fragments
        // so they don't contain a back button, as the instructions state "the login and onboarding pages do not show again"
        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.mainFragment
            )
        )

        // create the ActionBar managed by the NavigationUI, and pass as a 3rd param the "appBarConfiguration"
        // so it hides the back button on the login and onbording (welcome) screens.
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration)
    }

    override fun onSupportNavigateUp(): Boolean {
        this.onBackPressed()
        return super.onSupportNavigateUp()
    }
}