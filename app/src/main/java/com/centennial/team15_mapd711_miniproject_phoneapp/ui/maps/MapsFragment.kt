package com.centennial.team15_mapd711_miniproject_phoneapp.ui.maps

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.centennial.team15_mapd711_miniproject_phoneapp.R
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.MarkerOptions


class MapsFragment : Fragment() {


    private lateinit var mMap: GoogleMap

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        var view = inflater.inflate(R.layout.activity_maps, container, false)        // Initialize map fragment
        // Initialize map fragment
        val supportMapFragment =
            childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?

        supportMapFragment!!.getMapAsync { //googleMap ->
            // When map is loaded
//            googleMap.setOnMapClickListener { latLng -> // When clicked on map
//                // Initialize marker options
//                val markerOptions = MarkerOptions()
//                // Set position of marker
//                markerOptions.position(latLng)
//                // Set title of marker
//                markerOptions.title(latLng.latitude.toString() + " : " + latLng.longitude.toString())
//                // Remove all marker
//                googleMap.clear()
//                // Animating to zoom the marker
//                googleMap.animateCamera(CameraUpdateFactory.newLatLngZoom(latLng, 10f))
//                // Add marker on map
//                googleMap.addMarker(markerOptions)
//            }
        }

        return view

    }

    fun onEdit(view: View) {}

}