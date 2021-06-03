package org.grupotres.appcongreso.ui.pdf

import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import org.grupotres.appcongreso.R
import org.grupotres.appcongreso.databinding.FragmentSignaturePanelBinding

class SignaturePanelFragment : Fragment() {

	private var binding: FragmentSignaturePanelBinding? = null

	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setHasOptionsMenu(true)
	}

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		binding = FragmentSignaturePanelBinding.inflate(inflater, container, false)
		return binding?.root
	}

	override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
		super.onCreateOptionsMenu(menu, inflater)
		inflater.inflate(R.menu.menu_signature_panel, menu)
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		binding?.btnClearSignature?.setOnClickListener { binding?.drawingView?.clear() }
		binding?.btnAddSignature?.setOnClickListener {  }
	}

	override fun onDestroyView() {
		super.onDestroyView()
		binding = null
	}

	override fun onOptionsItemSelected(item: MenuItem) = when (item.itemId) {
		R.id.action_show_pdf -> {
			findNavController().navigate(R.id.action_nav_signature_panel_to_nav_pdf_viewer)
			true
		}
		else -> false
	}
}