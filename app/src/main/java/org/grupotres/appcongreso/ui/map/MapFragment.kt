package org.grupotres.appcongreso.ui.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import org.grupotres.appcongreso.R

class MapFragment : Fragment() {

	private val callback = OnMapReadyCallback { googleMap ->
		val uc = LatLng(latitude, longitud)
		initMapUIControls(googleMap)
		googleMap.addMarker(MarkerOptions().position(uc).title("Marker en la Universidad Continental"))
		googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(uc,16.0f))
		googleMap.setOnMarkerClickListener {
			showBottomSheetDialog()
			true
		}
	}

	override fun onCreateView(
		inflater: LayoutInflater,
		container: ViewGroup?,
		savedInstanceState: Bundle?
	): View? = inflater.inflate(R.layout.fragment_map, container, false)

	override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
		super.onViewCreated(view, savedInstanceState)
		val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
		mapFragment?.getMapAsync(callback)
	}

	private fun initMapUIControls(googleMap: GoogleMap) {
		googleMap.uiSettings.isMapToolbarEnabled = true
		googleMap.uiSettings.isZoomControlsEnabled = true
		googleMap.uiSettings.isMyLocationButtonEnabled = true
	}

	private fun showBottomSheetDialog() {
		val dialog = MapDialogFragment()
		dialog.show(parentFragmentManager, "MAP_FRAGMENT_MENU")
	}

	companion object {
		// Coordenadas de la Universidad Continental
		private const val latitude = -12.047555156621264
		private const val longitud = -75.19878650574097
	}
}