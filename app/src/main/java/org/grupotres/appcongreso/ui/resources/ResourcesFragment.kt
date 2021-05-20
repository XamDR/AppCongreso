package org.grupotres.appcongreso.ui.resources

import android.app.DownloadManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Environment.DIRECTORY_DOWNLOADS
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import org.grupotres.appcongreso.databinding.FragmentResourcesBinding
import java.io.File


class ResourcesFragment : Fragment() {

	private var binding: FragmentResourcesBinding? = null
	lateinit var reference: StorageReference
	lateinit var ref: StorageReference
	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		binding = FragmentResourcesBinding.inflate(inflater, container, false)

		binding?.download?.setOnClickListener {
			forDownloadFiles()
		}
		return binding?.root
	}


	override fun onDestroyView() {
		super.onDestroyView()
		binding = null
	}

	fun forDownloadFiles(){
		val storage = Firebase.storage

		reference=storage.getReference();
		ref=reference.child("Lean.pdf");

		ref.getDownloadUrl().addOnSuccessListener(OnSuccessListener<Uri> { uri ->
			val url = uri.toString()
			downloadFile(requireContext(), "Lean", ".pdf", DIRECTORY_DOWNLOADS, url)
			Toast.makeText(requireContext(), "Descarga Completa", Toast.LENGTH_SHORT).show()
		}).addOnFailureListener(OnFailureListener {
			Toast.makeText(requireContext(), "ERROR", Toast.LENGTH_SHORT).show()
		})
	}

	fun downloadFile(context: Context, fileName: String, fileExtension: String, destinationDirectory: String?, url: String?) {
		val downloadmanager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
		val uri = Uri.parse(url)
		val request = DownloadManager.Request(uri)
		request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
		request.setDestinationInExternalFilesDir(context, destinationDirectory, fileName + fileExtension)
		downloadmanager.enqueue(request)
	}
}