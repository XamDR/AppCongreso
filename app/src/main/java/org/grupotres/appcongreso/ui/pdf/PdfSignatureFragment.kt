package org.grupotres.appcongreso.ui.pdf

import android.graphics.pdf.PdfRenderer
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import org.grupotres.appcongreso.R
import org.grupotres.appcongreso.databinding.FragmentPdfSignatureBinding
import org.grupotres.appcongreso.util.createBitmap
import org.grupotres.appcongreso.util.debug
import org.grupotres.appcongreso.util.mainActivity
import org.grupotres.appcongreso.util.writeFile

class PdfSignatureFragment : Fragment() {

	private var binding: FragmentPdfSignatureBinding? = null
	private val viewModel by viewModels<PdfSignatureViewModel> {
		PdfSignatureViewModelFactory(mainActivity.storage)
	}

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setHasOptionsMenu(true)
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		binding = FragmentPdfSignatureBinding.inflate(inflater, container, false)
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
			binding?.pdfView?.setImageBitmap(bitmap)
			debug("URI", uri)
		})
	}

	override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
		super.onCreateOptionsMenu(menu, inflater)
		inflater.inflate(R.menu.menu_pdf_signature, menu)
	}

	override fun onDestroyView() {
		super.onDestroyView()
		binding = null
	}

	override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
		R.id.action_sign_pdf -> {
			findNavController().navigate(R.id.action_nav_pdf_signature_to_nav_signature_panel)
			true
		}
		else -> false
	}
}