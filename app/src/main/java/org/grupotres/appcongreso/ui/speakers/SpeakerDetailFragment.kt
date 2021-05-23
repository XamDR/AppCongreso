package org.grupotres.appcongreso.ui.speakers

import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.res.ResourcesCompat
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import coil.load
import org.grupotres.appcongreso.R
import org.grupotres.appcongreso.databinding.FragmentSpeakerDetailBinding
import org.grupotres.appcongreso.ui.lectures.LectureDetailFragmentArgs

class SpeakerDetailFragment : Fragment() {

	private var binding: FragmentSpeakerDetailBinding? = null
	private val args: LectureDetailFragmentArgs by navArgs()

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

	private fun initSpeakerDetails() {
		binding?.speakerName?.text = getString(R.string.speaker_name, args.speaker.toString())
		binding?.speakerAcademicInfo?.text = args.speaker.academicInfo
		binding?.speakerPhoto?.load(args.speaker.uriPhoto)
		val drawable = flags[args.speaker.country]?.let { ResourcesCompat.getDrawable(resources, it, null) }
		val lineHeight = binding?.speakerName?.lineHeight
		val bitmap = (drawable as BitmapDrawable).bitmap
		val flag = BitmapDrawable(resources, Bitmap.createScaledBitmap(bitmap, lineHeight!!, lineHeight, true))
		binding?.speakerName?.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, flag, null)
	}
}