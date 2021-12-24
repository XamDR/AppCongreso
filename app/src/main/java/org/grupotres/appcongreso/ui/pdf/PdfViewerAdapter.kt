package org.grupotres.appcongreso.ui.pdf

import android.content.Context
import android.graphics.Bitmap
import android.graphics.pdf.PdfRenderer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import edu.icontinental.congresoi40.R
import org.grupotres.appcongreso.util.renderAndClose

class PdfViewerAdapter(
	private val renderer: PdfRenderer,
	private val context: Context) : RecyclerView.Adapter<PdfViewerAdapter.ViewHolder>() {

	class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
		fun bind(bitmap: Bitmap) = (itemView as ImageView).setImageBitmap(bitmap)
	}

	override fun onAttachedToRecyclerView(recyclerView: RecyclerView) {
		super.onAttachedToRecyclerView(recyclerView)
		recyclerView.addItemDecoration(DividerItemDecoration(context, DividerItemDecoration.VERTICAL))
	}

	override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
		ViewHolder(LayoutInflater.from(parent.context).inflate(R.layout.item_pdf_page, parent, false))

	override fun getItemCount() = renderer.pageCount

	override fun onBindViewHolder(holder: ViewHolder, position: Int) =
		holder.bind(renderer.openPage(position).renderAndClose(context))
}