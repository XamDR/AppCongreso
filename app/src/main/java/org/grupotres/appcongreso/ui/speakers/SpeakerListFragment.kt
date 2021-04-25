package org.grupotres.appcongreso.ui.speakers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import org.grupotres.appcongreso.databinding.FragmentSpeakerListBinding

class SpeakerListFragment : Fragment() {

	private var binding: FragmentSpeakerListBinding? = null
	private val speakerViewModel by viewModels<SpeakerViewModel>()

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		binding = FragmentSpeakerListBinding.inflate(inflater, container, false)
		speakerViewModel.text.observe(viewLifecycleOwner, {
			binding?.textSpeakerList?.text = it
		})
		return binding?.root
	}

	override fun onDestroyView() {
		super.onDestroyView()
		binding = null
	}
}