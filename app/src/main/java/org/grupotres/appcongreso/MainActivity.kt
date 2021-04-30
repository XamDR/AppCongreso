package org.grupotres.appcongreso

import android.os.Bundle
import android.view.Menu
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import org.grupotres.appcongreso.databinding.ActivityMainBinding
import org.grupotres.appcongreso.ui.helpers.INavigator

class MainActivity : AppCompatActivity(), INavigator {

	private lateinit var appBarConfiguration: AppBarConfiguration
	private lateinit var binding: ActivityMainBinding

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
			setOf(R.id.nav_login, R.id.nav_lecture_list, R.id.nav_speaker_list),
			binding.drawerLayout)
		setupActionBarWithNavController(navController, appBarConfiguration)
		binding.navView.setupWithNavController(navController)
	}

	override fun navigate(resId: Int, bundle: Bundle?) {
		val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
		val navController = navHostFragment.navController
		navController.navigate(resId)
	}
}