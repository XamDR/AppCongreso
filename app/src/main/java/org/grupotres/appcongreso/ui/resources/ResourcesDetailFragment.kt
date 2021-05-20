package org.grupotres.appcongreso.ui.resources

import android.annotation.SuppressLint
import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.fragment.navArgs
import coil.load
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import org.grupotres.appcongreso.databinding.FragmentLectureDetailBinding
import org.grupotres.appcongreso.databinding.FragmentResourcesDetailBinding
import org.grupotres.appcongreso.ui.lectures.LectureListFragmentArgs
import org.grupotres.appcongreso.ui.speakers.SpeakerListFragmentArgs

class ResourcesDetailFragment : Fragment() {

	private var binding: FragmentResourcesDetailBinding? = null
	private val args: LectureListFragmentArgs by navArgs()
	lateinit var reference: StorageReference
	lateinit var ref: StorageReference

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

		binding = FragmentResourcesDetailBinding.inflate(inflater, container, false)
		binding?.download?.setOnClickListener {

			forDownloadFiles()
		}
		return binding?.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		initLectureDetails()
	}

	override fun onDestroyView() {
		super.onDestroyView()
		binding = null
	}

	@SuppressLint("SetTextI18n")
	private fun initLectureDetails() {
		binding?.lectureTitle?.text = args.lectureSpeaker.lecture.title
	}

	private fun forDownloadFiles(){
		val storage = Firebase.storage

		reference=storage.getReference();
		ref=reference.child("1 - Pon 1.3.pdf");

		ref.downloadUrl.addOnSuccessListener(OnSuccessListener<Uri> { uri ->
			val url = uri.toString()
			downloadFile(requireContext(), "1 - Pon 1.3", ".pdf", Environment.DIRECTORY_DOWNLOADS, url)
			Toast.makeText(requireContext(), "Descarga Completa", Toast.LENGTH_SHORT).show()
		}).addOnFailureListener(OnFailureListener {
			Toast.makeText(requireContext(), "ERROR", Toast.LENGTH_SHORT).show()
		})
	}

	private fun downloadFile(context: Context, fileName: String, fileExtension: String, destinationDirectory: String?, url: String?) {
		val downloadmanager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
		val uri = Uri.parse(url)
		val request = DownloadManager.Request(uri)
		request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
		request.setDestinationInExternalFilesDir(context, destinationDirectory, fileName + fileExtension)
		downloadmanager.enqueue(request)
	}
}