package org.grupotres.appcongreso.ui.speakers

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import coil.load
import org.grupotres.appcongreso.databinding.FragmentSpeakerDetailBinding

class SpeakerDetailFragment : Fragment() {

	private var binding: FragmentSpeakerDetailBinding? = null
	private val args: SpeakerListFragmentArgs by navArgs()

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		binding = FragmentSpeakerDetailBinding.inflate(inflater, container, false)
		return binding?.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		initSpeakerDetails()
	}

	override fun onDestroyView() {
		super.onDestroyView()
		binding = null
	}

	private fun initSpeakerDetails() {
		binding?.speakerName?.text = args.speaker.name
		binding?.speakerCountry?.text = args.speaker.country
		binding?.speakerInfo?.text = args.speaker.info
		binding?.speakerPhoto?.load(args.speaker.uriPhoto)
	}
}