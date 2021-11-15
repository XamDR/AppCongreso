package org.grupotres.appcongreso.ui.info

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
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
}