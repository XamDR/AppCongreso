package org.grupotres.appcongreso.ui.lectures

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class ResourceViewModel : ViewModel() {

	@Suppress("UNCHECKED_CAST")
	fun downloadFiles(context: Context, lectureId: String, dbRef: FirebaseFirestore, storage: FirebaseStorage) {
		viewModelScope.launch {
			val snapshot = dbRef.collection("lectures")
								.whereEqualTo("id", lectureId).get().await()

			if (snapshot.documents.isNotEmpty()) {
				val resources = snapshot.documents[0].get("resources") as List<String>

				for (resource in resources) {
					val uri = storage.reference.child("lecture${lectureId}").child(resource).downloadUrl.await()
					downloadFile(context, resource, Environment.DIRECTORY_DOWNLOADS, uri)
				}
				android.util.Log.d("RESOURCES", "Files downloaded")
			}
		}
	}

	private fun downloadFile(context: Context, fileName: String, destinationDirectory: String?, uri: Uri) {
		val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
		val request = DownloadManager.Request(uri)
		request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
		request.setDestinationInExternalFilesDir(context, destinationDirectory, fileName)
		downloadManager.enqueue(request)
	}
}