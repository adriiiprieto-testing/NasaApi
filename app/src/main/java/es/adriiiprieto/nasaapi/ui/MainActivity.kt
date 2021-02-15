package es.adriiiprieto.nasaapi.ui

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.navigation.NavigationView
import es.adriiiprieto.nasaapi.R
import es.adriiiprieto.nasaapi.databinding.ActivityMainBinding
import es.adriiiprieto.nasaapi.ui.MainViewModel

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    val viewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Set view thanks to data binding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Take the navigation controller
        val navController = (supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment).navController

        // Support top toolbar
        val appBarConfiguration = AppBarConfiguration(setOf(R.id.listFragment, R.id.pagingFragment), binding.drawerLayout)

        // Custom toolbar
        binding.mainContent.myToolbar.setupWithNavController(navController, appBarConfiguration)

        // Set a navigation drawer
        binding.navView.setupWithNavController(navController)
    }
}