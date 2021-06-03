package org.grupotres.appcongreso.ui.pdf

import android.graphics.pdf.PdfRenderer
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import org.grupotres.appcongreso.databinding.FragmentPdfViewerBinding
import org.grupotres.appcongreso.util.createBitmap
import org.grupotres.appcongreso.util.debug
import org.grupotres.appcongreso.util.mainActivity
import org.grupotres.appcongreso.util.writeFile

class PdfViewerFragment : Fragment() {

	private var binding: FragmentPdfViewerBinding? = null
	private val viewModel by viewModels<PdfSignatureViewModel> {
		PdfSignatureViewModelFactory(mainActivity.storage)
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		binding = FragmentPdfViewerBinding.inflate(inflater, container, false)
		return binding?.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		view.post { renderPdf() }
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
			signPdf()
			binding?.pdfView?.setImageBitmap(bitmap)
			debug("URI", uri)
		})
	}

	private fun signPdf() {
		// TODO
	}

	override fun onDestroyView() {
		super.onDestroyView()
		binding = null
	}
}