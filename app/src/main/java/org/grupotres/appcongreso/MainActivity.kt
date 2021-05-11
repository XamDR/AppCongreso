package org.grupotres.appcongreso

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import org.grupotres.appcongreso.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

	private lateinit var appBarConfiguration: AppBarConfiguration
	lateinit var binding: ActivityMainBinding

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityMainBinding.inflate(layoutInflater)
		setContentView(binding.root)
		setSupportActionBar(binding.contentMain.toolbar)
		setupNavigation()
	}

	override fun onCreateOptionsMenu(menu: Menu): Boolean {
		menuInflater.inflate(R.menu.main, menu)

		return true
	}

	override fun onSupportNavigateUp(): Boolean {
		val navController = findNavController(R.id.nav_host_fragment)
		return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
	}

	private fun setupNavigation() {
		val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
		val navController = navHostFragment.navController
		appBarConfiguration = AppBarConfiguration(
			setOf(R.id.nav_home, R.id.nav_lecture_list, R.id.nav_speaker_list, R.id.nav_resources),
			binding.drawerLayout
		)
		setupActionBarWithNavController(navController, appBarConfiguration)

		//OCULTAR ITEM
		//val navigationView: NavigationView = findViewById(R.id.nav_view) as NavigationView
		//val nav_Menu: Menu = navigationView.getMenu()
		//nav_Menu.findItem(R.id.nav_lecture_list).setVisible(false)

		binding.navView.setupWithNavController(navController)
	}
}