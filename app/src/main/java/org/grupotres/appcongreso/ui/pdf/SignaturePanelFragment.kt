package org.grupotres.appcongreso.ui.pdf

import android.graphics.PixelFormat
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import org.grupotres.appcongreso.databinding.FragmentSignaturePanelBinding

class SignaturePanelFragment : Fragment() {

	private var binding: FragmentSignaturePanelBinding? = null

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		binding = FragmentSignaturePanelBinding.inflate(inflater, container, false)
		return binding?.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		// Make the surfaceView transparent
		binding?.surfaceView?.setZOrderOnTop(true)
		val surfaceHolder = binding?.surfaceView?.holder
		surfaceHolder?.setFormat(PixelFormat.TRANSPARENT)
	}

	override fun onDestroyView() {
		super.onDestroyView()
		binding = null
	}
}