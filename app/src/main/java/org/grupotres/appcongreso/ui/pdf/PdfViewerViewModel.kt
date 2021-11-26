package org.grupotres.appcongreso.ui.pdf

import android.app.DownloadManager
import android.content.Context
import android.os.Environment
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.tasks.await
import org.grupotres.appcongreso.core.Lecture

class PdfViewerViewModel : ViewModel() {

	suspend fun downloadFile(context: Context, lectureId: Long) {
		val query = Firebase.firestore.collection("lectures").whereEqualTo("id", lectureId)
		val result = query.get().await()
		val lecture = result.documents[0].toObject<Lecture>()

		if (lecture != null) {
			val storageRef = Firebase.storage.reference
			val uri = storageRef.child("lecture${lectureId}").child(lecture.resources?.get(0)!!).downloadUrl.await()
			val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
			val request = DownloadManager.Request(uri)
			request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
			request.setDestinationInExternalFilesDir(context, Environment.DIRECTORY_DOWNLOADS, lecture.resources[0])
			downloadManager.enqueue(request)
		}
	}

	suspend fun fetchPdfFile(lectureId: Long): LiveData<ByteArray> {
		val pdfBytes = MutableLiveData<ByteArray>()
		val query = Firebase.firestore.collection("lectures").whereEqualTo("id", lectureId)
		val result = query.get().await()
		val lecture = result.documents[0].toObject<Lecture>()

		if (lecture != null) {
			val storageRef = Firebase.storage.reference
			val bytes = storageRef.child("lecture${lectureId}").child(lecture.resources?.get(0)!!)
				.getBytes(ONE_MEGABYTE).await()
			pdfBytes.value = bytes
		}
		return pdfBytes
	}

	companion object {
		const val ONE_MEGABYTE: Long = (1024 * 1024).toLong()
	}
}