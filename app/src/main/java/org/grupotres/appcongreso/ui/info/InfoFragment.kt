package org.grupotres.appcongreso.ui.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import org.grupotres.appcongreso.databinding.InfoFragmentBinding

class InfoFragment : Fragment() {
	private var binding: InfoFragmentBinding? = null
	private val viewModel by viewModels<InfoViewModel>()

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		binding = InfoFragmentBinding.inflate(inflater, container, false)
		return binding?.root
	}

	override fun onDestroyView() {
		super.onDestroyView()
		binding = null
	}
}