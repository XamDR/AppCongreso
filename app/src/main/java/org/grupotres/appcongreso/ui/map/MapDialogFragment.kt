package org.grupotres.appcongreso.ui.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import org.grupotres.appcongreso.R
import org.grupotres.appcongreso.databinding.FragmentMapDialogBinding

class MapDialogFragment : BottomSheetDialogFragment() {

	private var binding: FragmentMapDialogBinding? = null

	override fun onCreateView(
		inflater: LayoutInflater, container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? {
		binding = FragmentMapDialogBinding.inflate(inflater, container, false)
		return binding?.root
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		binding?.infoUc?.setOnClickListener { showInfoUC() }
	}

	override fun onDestroyView() {
		super.onDestroyView()
		binding = null
	}

	private fun showInfoUC() {
		MaterialAlertDialogBuilder(requireContext())
			.setTitle(getString(R.string.uc))
			.setMessage(getString(R.string.detail_info_uc))
			.setPositiveButton(R.string.ok_button) { dialog, _ -> dialog.dismiss() }
			.also { it.show() }
	}

//	companion object {
//		private const val ARG_ITEM_COUNT = "item_count"
//
//		fun newInstance(itemCount: Int): MapDialogFragment =
//			MapDialogFragment().apply {
//				arguments = Bundle().apply {
//					putInt(ARG_ITEM_COUNT, itemCount)
//				}
//			}
//
//	}
}