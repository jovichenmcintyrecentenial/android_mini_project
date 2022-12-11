package com.centennial.team15_mapd711_miniproject_phoneapp.ui.maps

import MapsViewModel
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.centennial.team15_mapd711_miniproject_phoneapp.R
import com.centennial.team15_mapd711_miniproject_phoneapp.models.Store
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
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
            //set custom info window adapter view

            var stores = mapsViewModel!!.liveListOfStores!!.value
            mMap!!.setInfoWindowAdapter(MyInfoWindowAdapter(requireContext(),stores!!))
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
            //adjust camera to first store in list of locations
            mMap!!.moveCamera(
                CameraUpdateFactory.newLatLngZoom(LatLng(stores[0].latitude!!, stores[0].longitude!!),
                8.0F
            ))
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

class MyInfoWindowAdapter(context: Context, private val stores: List<Store>) : GoogleMap.InfoWindowAdapter {
    val mWindow: View = LayoutInflater.from(context).inflate(R.layout.map_window_info, null)
    val contex = context

    override fun getInfoWindow(marker: Marker): View {

        //use the snippet infomation and cast it to an int so app know which marker is being pressed
        var index = marker.snippet.toString().toIntOrNull()
        if(index != null){
            //take index and retrieve storelocation object
            var storeLocation = stores[index];

            //find views
            val companyNameTextView = mWindow.findViewById<TextView>(R.id.company_name)
            val companyImageView = mWindow.findViewById<ImageView>(R.id.company_image)
            val addressTextView = mWindow.findViewById<TextView>(R.id.address)
            val phoneTextView = mWindow.findViewById<TextView>(R.id.phone)
            val openingTextView = mWindow.findViewById<TextView>(R.id.opening)
            val websiteTextView = mWindow.findViewById<TextView>(R.id.website)

            //update view data
            companyNameTextView.text = storeLocation.name
            addressTextView.text = storeLocation.address
            phoneTextView.text = storeLocation.phoneNumber
            openingTextView.text = storeLocation.openHours +  " - " + storeLocation.closeHours
            websiteTextView.text = storeLocation.website

            //load store image in view base on name
            val resourceImage: Int = contex.resources.getIdentifier(storeLocation.image, "drawable", contex.packageName)
            companyImageView?.setImageResource(resourceImage)

        }

        return mWindow
    }

    override fun getInfoContents(marker: Marker): View {
        return mWindow
    }
}