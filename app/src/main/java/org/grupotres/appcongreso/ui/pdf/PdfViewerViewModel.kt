package org.grupotres.appcongreso.ui.pdf

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.ktx.storage
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import org.grupotres.appcongreso.core.Lecture

class PdfViewerViewModel : ViewModel() {

	init {
		fetchPdfFile()
	}

	private val _pdfBytes = MutableLiveData<ByteArray>()
	val pdfBytes: LiveData<ByteArray> = _pdfBytes

	private fun fetchPdfFile() {
		viewModelScope.launch {
			val query = Firebase.firestore.collection("lectures").whereEqualTo("id", "3")
			val result = query.get().await()
			val lecture = result.documents[0].toObject<Lecture>()

			if (lecture != null) {
				val storageRef = Firebase.storage.reference
				val bytes = storageRef.child("lecture${lecture.id}").child(lecture.resources?.get(0)!!)
					.getBytes(ONE_MEGABYTE).await()
				_pdfBytes.value = bytes
			}
		}
	}

	companion object {
		const val ONE_MEGABYTE: Long = (1024 * 1024).toLong()
	}
}