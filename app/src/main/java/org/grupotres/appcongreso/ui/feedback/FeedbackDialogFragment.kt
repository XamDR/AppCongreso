package org.grupotres.appcongreso.ui.feedback

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import org.grupotres.appcongreso.databinding.FragmentFeedbackDialogBinding

class FeedbackDialogFragment : BottomSheetDialogFragment() {

	private var binding: FragmentFeedbackDialogBinding? = null

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		binding = FragmentFeedbackDialogBinding.inflate(inflater, container, false)
		return binding?.root
	}

	override fun onDestroyView() {
		super.onDestroyView()
		binding = null
	}
}