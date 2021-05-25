package org.grupotres.appcongreso.ui.resources

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.firestore.FirebaseFirestore

class ResourceViewModelFactory(private val dbRef: FirebaseFirestore) : ViewModelProvider.Factory {

	@Suppress("UNCHECKED_CAST")
	override fun <T : ViewModel?> create(modelClass: Class<T>) = ResourceViewModel(dbRef) as T
}