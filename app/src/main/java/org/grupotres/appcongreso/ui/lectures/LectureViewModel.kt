package org.grupotres.appcongreso.ui.lectures

import androidx.lifecycle.ViewModel
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.ktx.toObject
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.tasks.await
import org.grupotres.appcongreso.core.Lecture

class LectureViewModel : ViewModel() {

	suspend fun fetchLecturesByRoomFromFirestore(vararg rooms: String): Flow<List<Lecture>> {
		val db = Firebase.firestore
		val lectures = mutableListOf<Lecture>()
//		val query = db.collection("lectures").whereIn("room", listOf(*rooms))
		val query = db.collection("lectures").whereEqualTo("id", "3")
		val result = query.get().await()

		for (document in result.documents) {
			if (document.toObject<Lecture>() != null) {
				val lecture = document.toObject<Lecture>()!!
				lectures.add(lecture)
			}
		}
		return MutableStateFlow(lectures)
	}
}