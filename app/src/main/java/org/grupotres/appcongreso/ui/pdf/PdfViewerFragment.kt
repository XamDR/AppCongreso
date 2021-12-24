package org.grupotres.appcongreso.ui.pdf

import android.graphics.pdf.PdfRenderer
import android.os.Bundle
import android.os.ParcelFileDescriptor
import android.view.*
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import edu.icontinental.congresoi40.R
import edu.icontinental.congresoi40.databinding.FragmentPdfViewerBinding
import kotlinx.coroutines.launch
import org.grupotres.appcongreso.ui.lectures.LectureDetailFragmentArgs
import org.grupotres.appcongreso.util.showSnackbar
import org.grupotres.appcongreso.util.writeFile

class PdfViewerFragment : Fragment() {

	private var binding: FragmentPdfViewerBinding? = null
	private val args by navArgs<LectureDetailFragmentArgs>()
	private val viewModel by viewModels<PdfViewerViewModel>()
	private lateinit var adapter: PdfViewerAdapter
	private lateinit var renderer: PdfRenderer

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setHasOptionsMenu(true)
	}

	override fun onCreateView(inflater: LayoutInflater,
	                          container: ViewGroup?,
	                          savedInstanceState: Bundle?): View? {
		binding = FragmentPdfViewerBinding.inflate(inflater, container, false)
		return binding?.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		view.post { renderPdf() }
	}

	override fun onDestroyView() {
		super.onDestroyView()
		renderer.close()
	}

	override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
		super.onCreateOptionsMenu(menu, inflater)
		inflater.inflate(R.menu.pdf_viewer_menu, menu)
	}

	override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
		R.id.action_download_pdf -> {
			downloadCertificate(); true
		}
		else -> false
	}

	private fun renderPdf() {
		viewLifecycleOwner.lifecycleScope.launch {
			viewModel.fetchPdfFile(args.lectureId).observe(viewLifecycleOwner) { bytes ->
				val file = writeFile(requireContext(), bytes, args.lectureId.toString())
				val fileDescriptor = ParcelFileDescriptor.open(file, ParcelFileDescriptor.MODE_READ_ONLY)
				renderer = PdfRenderer(fileDescriptor)
				adapter = PdfViewerAdapter(renderer, requireContext())

				if (binding?.pdfView?.adapter == null) {
					binding?.pdfView?.adapter = adapter
				}
			}
		}
	}

	private fun downloadCertificate() {
		binding?.root?.showSnackbar(message = R.string.download_files_message)
		viewLifecycleOwner.lifecycleScope.launch {
			viewModel.downloadFile(requireContext(), args.lectureId)
		}
	}
}