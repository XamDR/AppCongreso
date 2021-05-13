package org.grupotres.appcongreso.ui.speakers

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import org.grupotres.appcongreso.core.Speaker
import org.grupotres.appcongreso.databinding.ItemSpeakerDetailBinding
import org.grupotres.appcongreso.ui.helpers.INavigator
import org.grupotres.appcongreso.util.setOnClickListener

class SpeakerAdapter(private val viewModel: SpeakerViewModel, private val navigator: INavigator) :
	ListAdapter<Speaker, SpeakerAdapter.SpeakerViewHolder>(SpeakerCallback()) {

	inner class SpeakerViewHolder(private val binding: ItemSpeakerDetailBinding) :
		RecyclerView.ViewHolder(binding.root) {

		@SuppressLint("SetTextI18n")
		fun bind(speaker: Speaker) {
			binding.speakerName.text = speaker.toString()
			binding.speakerDesc.text = speaker.info
			binding.speakerPhoto.load(speaker.uriPhoto)
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpeakerViewHolder {
		val binding = ItemSpeakerDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		val holder = SpeakerViewHolder(binding)
		holder.setOnClickListener { position, _ -> goToSpeakerDetail(position) }
		return holder
	}

	private fun goToSpeakerDetail(position: Int) {
		val speaker = getItem(position)
		val navDirections = SpeakerListFragmentDirections.actionNavSpeakerListToSpeakerDetail(speaker)
		navigator.navigate(navDirections)
	}


	override fun onBindViewHolder(holder: SpeakerViewHolder, position: Int) {
		val speaker = viewModel.speakers.value?.get(position)
		speaker?.let { holder.bind(it) }
	}

	class SpeakerCallback : DiffUtil.ItemCallback<Speaker>() {

		override fun areItemsTheSame(oldSpeaker: Speaker, newSpeaker: Speaker) = oldSpeaker.id == newSpeaker.id

		override fun areContentsTheSame(oldSpeaker: Speaker, newSpeaker: Speaker) = oldSpeaker == newSpeaker
	}
}