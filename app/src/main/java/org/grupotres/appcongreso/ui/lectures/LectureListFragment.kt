package org.grupotres.appcongreso.ui.lectures

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.material.tabs.TabLayoutMediator
import org.grupotres.appcongreso.databinding.FragmentLecturesBinding

class LectureListFragment : Fragment() {

	private var binding: FragmentLecturesBinding? = null
	private lateinit var adapter: LectureStateAdapter

	override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
		binding = FragmentLecturesBinding.inflate(inflater, container, false)
		return binding?.root
	}

	override fun onDestroyView() {
		super.onDestroyView()
		binding = null
	}

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		setupViewPagerWithTabLayout()
	}

	private fun setupViewPagerWithTabLayout() {
		adapter = LectureStateAdapter(this, listOf(
			FragmentLectureFirstRoom(),
			FragmentLectureSecondRoom())
		)
		binding?.pager?.adapter = adapter
		TabLayoutMediator(binding?.tabLayout!!, binding?.pager!!) { tab, position ->
			tab.text = when (position) {
				0 -> "Sala 1"
				else -> "Sala 2"
			}
		}.attach()
	}
}