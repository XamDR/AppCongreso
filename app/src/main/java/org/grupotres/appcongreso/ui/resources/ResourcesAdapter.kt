package org.grupotres.appcongreso.ui.resources

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.google.android.gms.tasks.OnFailureListener
import com.google.android.gms.tasks.OnSuccessListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.storage.StorageReference
import com.google.firebase.storage.ktx.storage
import org.grupotres.appcongreso.R
import org.grupotres.appcongreso.core.LectureResources
import org.grupotres.appcongreso.databinding.ItemResourceBinding

class ResourcesAdapter(private val context: Context, private val viewModel: ResourceViewModel) :
	ListAdapter<LectureResources, ResourcesAdapter.ResourceViewHolder>(ResourceCallback()) {

	lateinit var reference: StorageReference
	lateinit var ref: StorageReference

	inner class ResourceViewHolder(private val binding: ItemResourceBinding) :
		RecyclerView.ViewHolder(binding.root) {

		fun bind(resource: LectureResources) {
			binding.lectureTitle.text = resource.lecture.title
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ResourceViewHolder {
		val binding = ItemResourceBinding.inflate(LayoutInflater.from(parent.context), parent, false)

		binding.download.setOnClickListener {
			forDownloadFiles()
		}
		return ResourceViewHolder(binding)
	}

	override fun onBindViewHolder(holder: ResourceViewHolder, position: Int) {
		val resource = viewModel.resources.value?.get(position)
		resource?.let { holder.bind(resource) }
	}

	class ResourceCallback : DiffUtil.ItemCallback<LectureResources>() {

		override fun areItemsTheSame(oldResource: LectureResources, newResource: LectureResources)
			= oldResource.lecture.id == newResource.lecture.id

		override fun areContentsTheSame(oldResource: LectureResources, newResource: LectureResources)
			= oldResource == newResource
	}

	private fun forDownloadFiles(){
		val storage = Firebase.storage

		reference=storage.getReference();
		ref=reference.child("1 - Pon 1.3.pdf");

		ref.downloadUrl.addOnSuccessListener(OnSuccessListener<Uri> { uri ->
			val url = uri.toString()
			downloadFile(context, "1 - Pon 1.3", ".pdf", Environment.DIRECTORY_DOWNLOADS, url)
			Toast.makeText(context, "Descarga Completa", Toast.LENGTH_SHORT).show()
		}).addOnFailureListener(OnFailureListener {
			Toast.makeText(context, "ERROR", Toast.LENGTH_SHORT).show()
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
