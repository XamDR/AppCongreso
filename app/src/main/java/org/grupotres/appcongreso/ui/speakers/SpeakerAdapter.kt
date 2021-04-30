package org.grupotres.appcongreso.ui.speakers

import android.annotation.SuppressLint
import android.net.Uri
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import org.grupotres.appcongreso.core.Speaker
import org.grupotres.appcongreso.databinding.ItemLectureDetailBinding
import org.grupotres.appcongreso.databinding.ItemSpeakerDetailBinding
import org.grupotres.appcongreso.util.setOnClickListener

class SpeakerAdapter(private val viewModel: SpeakerViewModel) :
	ListAdapter<Speaker, SpeakerAdapter.SpeakerViewHolder>(SpeakerCallback()) {

	inner class SpeakerViewHolder(private val binding: ItemSpeakerDetailBinding) :
		RecyclerView.ViewHolder(binding.root) {

		@SuppressLint("SetTextI18n")
		fun bind(speaker: Speaker) {
			binding.speakerName.text = "${speaker.name} ${speaker.surname}"
			binding.speakerDesc.text = speaker.country

			if (speaker.uriPhoto != null) {
				binding.speakerPhoto.setImageURI(Uri.parse(speaker.uriPhoto))
			}
		}
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SpeakerViewHolder {
		val binding = ItemSpeakerDetailBinding.inflate(LayoutInflater.from(parent.context), parent, false)
		val holder = SpeakerViewHolder(binding)
		holder.setOnClickListener { position, _ -> goToSpeakerDetail(position) }
		return SpeakerViewHolder(binding)
	}

	private fun goToSpeakerDetail(position: Int) {
		TODO("Falta implementar")
	}


	override fun onBindViewHolder(holder: SpeakerViewHolder, position: Int) {
		val speaker = viewModel.speakers[position]
		holder.bind(speaker)
	}

	class SpeakerCallback : DiffUtil.ItemCallback<Speaker>() {

		override fun areItemsTheSame(oldSpeaker: Speaker, newSpeaker: Speaker) = oldSpeaker.id == newSpeaker.id

		override fun areContentsTheSame(oldSpeaker: Speaker, newSpeaker: Speaker) = oldSpeaker == newSpeaker
	}
}