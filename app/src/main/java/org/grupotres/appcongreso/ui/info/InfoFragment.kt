package org.grupotres.appcongreso.ui.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.text.HtmlCompat
import androidx.fragment.app.Fragment
import edu.icontinental.congresoi40.R
import edu.icontinental.congresoi40.databinding.InfoFragmentBinding

class InfoFragment : Fragment() {

	private var binding: InfoFragmentBinding? = null

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		binding = InfoFragmentBinding.inflate(inflater, container, false)
		return binding?.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		val textOption1 = HtmlCompat.fromHtml(getText(R.string.info_opc1).toString(), HtmlCompat.FROM_HTML_MODE_COMPACT)
		val textOption2 = HtmlCompat.fromHtml(getText(R.string.info_opc2).toString(), HtmlCompat.FROM_HTML_MODE_COMPACT)
		binding?.infoOpc1?.text = textOption1
		binding?.infoOpc2?.text = textOption2
	}
}