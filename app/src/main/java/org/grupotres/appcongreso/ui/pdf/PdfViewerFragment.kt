package org.grupotres.appcongreso.ui.pdf

import android.app.DownloadManager
import android.content.Context
import android.graphics.pdf.PdfRenderer
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import org.grupotres.appcongreso.R
import org.grupotres.appcongreso.databinding.FragmentPdfViewerBinding
import org.grupotres.appcongreso.util.createBitmap
import org.grupotres.appcongreso.util.debug
import org.grupotres.appcongreso.util.mainActivity
import org.grupotres.appcongreso.util.writeFile

class PdfViewerFragment : Fragment() {

	private var binding: FragmentPdfViewerBinding? = null
	private val viewModel by viewModels<PdfViewerViewModel> {
		PdfViewerViewModelFactory(mainActivity.storage)
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setHasOptionsMenu(true)
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		binding = FragmentPdfViewerBinding.inflate(inflater, container, false)
		return binding?.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		view.post { renderPdf() }
	}

	override fun onDestroyView() {
		super.onDestroyView()
		binding = null
	}

	override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
		super.onCreateOptionsMenu(menu, inflater)
		inflater.inflate(R.menu.menu_digital_certificate, menu)
	}

	override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
		R.id.action_download -> {
			downloadCertificate()
			true
		}
		else -> false
	}

	private fun renderPdf() {
		viewModel.pdfBytes.observe(viewLifecycleOwner, { bytes ->
			val file = writeFile(requireContext(), bytes, "certificado.pdf")
			val uri = Uri.fromFile(file)
			val fileDescriptor = mainActivity.contentResolver.openFileDescriptor(uri, "r", null)
			val renderer = fileDescriptor?.let { PdfRenderer(it) }
			val bitmap = createBitmap(requireContext())
			renderer?.apply {
				openPage(0).apply {
					render(bitmap, null, null, PdfRenderer.Page.RENDER_MODE_FOR_DISPLAY)
					close()
				}
				close()
			}
			binding?.pdfView?.setImageBitmap(bitmap)
			debug("URI", uri)
		})
	}

	private fun downloadCertificate() {
		viewLifecycleOwner.lifecycleScope.launch {
			val storageRef = mainActivity.storage.reference.child("certificados").child("certificado.pdf")
			val uri = storageRef.downloadUrl.await()
			val downloadManager = requireContext().getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
			val request = DownloadManager.Request(uri)
			request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
			request.setDestinationInExternalFilesDir(context, Environment.DIRECTORY_DOWNLOADS, "certificado.pdf")
			downloadManager.enqueue(request)
		}
	}
}