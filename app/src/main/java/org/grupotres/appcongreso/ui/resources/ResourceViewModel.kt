package org.grupotres.appcongreso.ui.resources

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.toObjects
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import org.grupotres.appcongreso.core.Lecture

class ResourceViewModel(dbRef: FirebaseFirestore) : ViewModel() {

	private val _lectures = MutableLiveData(mutableListOf<Lecture>())
	val lectures: LiveData<MutableList<Lecture>> = _lectures

	init {
		fechResources(dbRef)
	}

	private fun fechResources(dbRef: FirebaseFirestore) {
		viewModelScope.launch {
			val snapshot = dbRef.collection("lectures").get().await()
			val lectures = snapshot.toObjects<Lecture>()
			_lectures.value = lectures.toMutableList()
			android.util.Log.d("RESOURCE", lectures.toString())
		}
	}

	@Suppress("UNCHECKED_CAST")
	fun downloadFiles(context: Context, position: Int, dbRef: FirebaseFirestore, storage: FirebaseStorage) {
		viewModelScope.launch {
			val lecture = lectures.value?.get(position)
			val snapshot = dbRef.collection("lectures")
								.whereEqualTo("id", lecture?.id).get().await()
			val resources = snapshot.documents[0].get("resources") as List<String>

			for (resource in resources) {
				val uri = storage.reference.child("lecture${lecture?.id}").child(resource).downloadUrl.await()
				downloadFile(context, resource, Environment.DIRECTORY_DOWNLOADS, uri)
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