package org.grupotres.appcongreso

import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity

@SuppressLint("CustomSplashScreen")
@Suppress("deprecation")
class SplashActivity : AppCompatActivity() {

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_UNSPECIFIED
		window.setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
			WindowManager.LayoutParams.FLAG_FULLSCREEN
		)
		setContentView(R.layout.activity_splash)
		Handler(Looper.getMainLooper()).postDelayed({
			val intent = Intent(this@SplashActivity, MainActivity::class.java)
			startActivity(intent)
		}, DURACION_SPLASH)
	}

	companion object {
		private const val DURACION_SPLASH = 500L
	}
}