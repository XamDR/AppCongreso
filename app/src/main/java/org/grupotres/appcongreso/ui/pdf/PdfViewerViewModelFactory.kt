package org.grupotres.appcongreso.ui.pdf

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.storage.FirebaseStorage

class PdfViewerViewModelFactory(private val storage: FirebaseStorage) : ViewModelProvider.Factory {

	@Suppress("UNCHECKED_CAST")
	override fun <T : ViewModel?> create(modelClass: Class<T>) = PdfViewerViewModel(storage) as T
}