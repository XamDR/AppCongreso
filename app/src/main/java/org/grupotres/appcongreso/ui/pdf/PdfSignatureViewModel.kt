package org.grupotres.appcongreso.ui.pdf

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.storage.FirebaseStorage
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class PdfSignatureViewModel(storage: FirebaseStorage): ViewModel() {

	init {
		fetchPdfFile(storage)
	}

	private val _pdfBytes = MutableLiveData<ByteArray>()
	val pdfBytes: LiveData<ByteArray> = _pdfBytes

	private fun fetchPdfFile(storage: FirebaseStorage) {
		viewModelScope.launch {
			val bytes = storage.reference.child("certificados").child("certificado.pdf")
				.getBytes(ONE_MEGABYTE).await()
			_pdfBytes.value = bytes
		}
	}

	companion object {
		const val ONE_MEGABYTE: Long = 1024 * 1024
	}
}