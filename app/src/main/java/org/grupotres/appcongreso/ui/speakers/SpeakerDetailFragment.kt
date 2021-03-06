package org.grupotres.appcongreso.ui.speakers

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.navArgs
import androidx.transition.TransitionInflater
import coil.load
import edu.icontinental.congresoi40.R
import edu.icontinental.congresoi40.databinding.FragmentSpeakerDetailBinding
import org.grupotres.appcongreso.ui.lectures.LectureDetailFragmentArgs

class SpeakerDetailFragment : Fragment() {

	private var binding: FragmentSpeakerDetailBinding? = null
	private val args by navArgs<LectureDetailFragmentArgs>()

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(R.transition.speaker_detail_enter)
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
		binding?.speakerName?.text = getString(R.string.speaker_name, args.speaker?.name, args.speaker?.surname, args.speaker?.maternalSurname)
		binding?.speakerAcademicInfo?.text = args.speaker?.academicInfo
		binding?.speakerPhoto?.load(args.speaker?.uriPhoto)
//		val drawable = flags[args.speaker.country]?.let { ResourcesCompat.getDrawable(resources, it, null) }
//		val lineHeight = binding?.speakerName?.lineHeight
//		val bitmap = (drawable as BitmapDrawable).bitmap
//		val flag = BitmapDrawable(resources, Bitmap.createScaledBitmap(bitmap, lineHeight!! + 10, lineHeight + 10, true))
//		binding?.speakerName?.setCompoundDrawablesRelativeWithIntrinsicBounds(null, null, flag, null)
	}
}