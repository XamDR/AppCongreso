package org.grupotres.appcongreso.ui.login

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.transition.Transition
import android.util.Log
import android.widget.Button
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.google.firebase.auth.FirebaseAuth
import org.grupotres.appcongreso.MainActivity
import org.grupotres.appcongreso.R


class HomeActivity : AppCompatActivity() {

	private lateinit var mAuth: FirebaseAuth

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_home)

		mAuth = FirebaseAuth.getInstance()
		val currentUser = mAuth.currentUser

		val signInButton = findViewById<Button>(R.id.btn_cerrar)

		val profile_image = findViewById<ImageView>(R.id.profile_image)

		Glide.with(this).load(currentUser?.photoUrl).into(profile_image)

		signInButton.setOnClickListener {
			Log.d("LOGOUT", "Se cerró")

			mAuth.signOut()
			val intent = Intent(this, MainActivity::class.java)
			startActivity(intent)
			finish()
		}
	}
}