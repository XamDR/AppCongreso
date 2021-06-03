package org.grupotres.appcongreso.util

import android.content.Context
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.graphics.Canvas
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import java.io.File
import java.io.FileOutputStream

fun setNightMode(preferences: SharedPreferences) {
	when (preferences.getString("app_theme", "-1")?.toInt()) {
		0 -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
		1 -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
		-1 -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
	}
}

fun writeFile(context: Context, fileBytes: ByteArray, name: String): File {
	val path = "${context.filesDir}/$name"
	val file = File(path)
	FileOutputStream(file).use { it.write(fileBytes) }
	return file
}

fun createBitmap(context: Context): Bitmap {
	val screenHeight = context.resources?.displayMetrics?.heightPixels
	val screenWidth = context.resources?.displayMetrics?.widthPixels
	val aspectRatio = screenWidth!!.toFloat() / screenHeight!!.toFloat()
	val modifiedScreenHeight = 1000
	val modifiedScreenWidth = (modifiedScreenHeight * aspectRatio).toInt()

	return Bitmap.createBitmap(modifiedScreenWidth, modifiedScreenHeight, Bitmap.Config.ARGB_8888)
}

fun saveToInternalStorage(context: Context, bitmapImage: Bitmap, imageFileName: String): String {
	context.openFileOutput(imageFileName, Context.MODE_PRIVATE).use { fos ->
		bitmapImage.compress(Bitmap.CompressFormat.PNG, 25, fos)
	}
	return context.filesDir.absolutePath
}

fun getBitmapFromView(view: View): Bitmap? {
	val bitmap = Bitmap.createBitmap(view.width, view.height, Bitmap.Config.ARGB_8888)
	val canvas = Canvas(bitmap)
	view.draw(canvas)
	return bitmap
}

fun getFileFromInternalStorage(context: Context, fileName: String): File {
	val directory = context.filesDir
	return File(directory, fileName)
}

/*
fun utils (preferences: SharedPreferences){
	val prefs: SharedPreferences = getSharedPreferences("MisPreferencias", this.MODE_PRIVATE)

	val editor = prefs.edit()
	editor.putString("email", "Dean86collis@email.com")
	editor.putString("password", "86142DCL")
	editor.commit()
}*/
