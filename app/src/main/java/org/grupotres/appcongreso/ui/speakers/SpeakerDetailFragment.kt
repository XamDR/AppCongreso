package org.grupotres.appcongreso.ui.speakers

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.navigation.fragment.navArgs
import coil.load
import org.grupotres.appcongreso.R
import org.grupotres.appcongreso.databinding.FragmentSpeakerDetailBinding

class SpeakerDetailFragment : Fragment() {

	private var binding: FragmentSpeakerDetailBinding? = null
	private val args: SpeakerListFragmentArgs by navArgs()

	private val flags = mapOf(
		"Bélgica" to R.drawable.belgium,
		"Chile" to R.drawable.chile,
		"Colombia" to R.drawable.colombia,
		"México" to R.drawable.mexico,
		"Perú" to R.drawable.peru,
	)

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

	@SuppressLint("SetTextI18n")
	private fun initSpeakerDetails() {
		binding?.speakerName?.text = "${args.speaker.name} " +
				"${flags[args.speaker.surname]} ${flags[args.speaker.maternalSurname]}"
		binding?.speakerInfo?.text = args.speaker.info
		binding?.speakerPhoto?.load(args.speaker.uriPhoto)
		val flag = flags[args.speaker.country]?.let { ResourcesCompat.getDrawable(resources, it, null) }
		binding?.speakerPhoto?.setImageDrawable(flag)
	}
}