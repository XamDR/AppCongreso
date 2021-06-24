package org.grupotres.appcongreso.ui.phoneauth

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.firebase.auth.FirebaseAuth
import org.grupotres.appcongreso.R

class Home : AppCompatActivity() {
	lateinit var auth: FirebaseAuth

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(R.layout.activity_home)
		auth= FirebaseAuth.getInstance()
		var currentUser=auth.currentUser

//        Reference
		val logout=findViewById<Button>(R.id.idLogout)

		if(currentUser==null){
			startActivity(Intent(this,phone::class.java))
			finish()
		}

		logout.setOnClickListener{
			auth.signOut()
			startActivity(Intent(this,phone::class.java))
			finish()
		}
	}

}