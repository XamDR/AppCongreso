package org.grupotres.appcongreso

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import org.grupotres.appcongreso.databinding.ActivityWelcomeBinding
import org.grupotres.appcongreso.ui.settings.SettingsManager

class WelcomeActivity : AppCompatActivity() {

	private lateinit var binding: ActivityWelcomeBinding
	private lateinit var manager: SettingsManager

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		binding = ActivityWelcomeBinding.inflate(layoutInflater)
		setContentView(binding.root)
		manager = SettingsManager(this)
		showIntroOrMain()
	}

	private fun showIntroOrMain() {
		if (manager.isFirstRun) {
			manager.isFirstRun = false
		}
		else {
			goToMainActivity()
		}
	}

	fun goToMainActivity() = startActivity(Intent(this, MainActivity::class.java))
}