package com.centennial.team15_mapd711_miniproject_phoneapp.ui.maps

import MapsViewModel
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.centennial.team15_mapd711_miniproject_phoneapp.R
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class MapsFragment : Fragment(), OnMapReadyCallback {


    private var mMap: GoogleMap? = null
    var mapsViewModel:MapsViewModel? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        var view = inflater.inflate(R.layout.activity_maps, container, false)        // Initialize map fragment
        // Initialize map fragment
        val supportMapFragment =
            childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?

        mapsViewModel =
            ViewModelProvider(this).get(MapsViewModel::class.java)

        mapsViewModel!!.liveListOfStores.observe(viewLifecycleOwner, Observer { stores ->
            if(mMap!= null){
                addMarkers()
            }
        })

        mapsViewModel!!.getAllStores(requireContext())

        supportMapFragment!!.getMapAsync (this)

        return view

    }

    fun addMarkers(){

        if(mapsViewModel?.liveListOfStores != null && mMap != null) {
            var stores = mapsViewModel!!.liveListOfStores!!.value
            for ((index, store) in stores!!.withIndex()) {
                mMap!!.addMarker(
                    MarkerOptions()
                        .position(LatLng(store.latitude!!, store.longitude!!))
                        .title(store.name!!)
                        //save item position so item can be retrieve in MyInfoWindowAdapter to extract more data
                        .snippet(index.toString())
                        //resize image because image was too large
                        .icon(
                            BitmapDescriptorFactory.fromBitmap(
                                resizeMapIcons(
                                    store.icon!!,
                                    80,
                                    106
                                )
                            )
                        )
                )
            }
        }
    }

    private fun resizeMapIcons(iconName: String?, width: Int, height: Int): Bitmap {
        val imageBitmap = BitmapFactory.decodeResource(
            resources, resources.getIdentifier(
                iconName, "drawable",
                requireContext().packageName
            )
        )
        return Bitmap.createScaledBitmap(imageBitmap, width, height, false)
    }


    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
    }



}