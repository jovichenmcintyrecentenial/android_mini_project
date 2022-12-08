package com.centennial.team15_mapd711_miniproject_phoneapp

import MapsViewModel
import ProductModel
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.centennial.team15_mapd711_miniproject_phoneapp.databinding.ActivityMapsBinding
import com.centennial.team15_mapd711_miniproject_phoneapp.models.Store
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.Marker
import com.google.gson.Gson

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding
    private lateinit var product: ProductModel;
    var mapsViewModel:MapsViewModel? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        //get data send from previous page and deserialize the data
        product = Gson().fromJson(intent.getStringExtra("product"),ProductModel::class.java)


        mapsViewModel =
            ViewModelProvider(this).get(MapsViewModel::class.java)

        mapsViewModel!!.liveListOfStores.observe(this, Observer { stores ->
            if(mMap != null){

                addMarkers()
            }
        })
        mapsViewModel!!.getAllStores(this,product)

    }

    fun addMarkers(){

        if(mapsViewModel?.liveListOfStores != null && mMap != null) {
            //set custom info window adapter view

            var stores = mapsViewModel!!.liveListOfStores!!.value
            mMap!!.setInfoWindowAdapter(
                com.centennial.team15_mapd711_miniproject_phoneapp.ui.maps.MyInfoWindowAdapter(
                    this,
                    stores!!
                )
            )
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
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LatLng(stores[0].latitude!!, stores[0].longitude!!),
                8.0F
            ))
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        //load menu
        menuInflater.inflate(R.menu.menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        //update map type base on selected option
        when(item.itemId) {
            R.id.hybrid -> mMap.mapType = GoogleMap.MAP_TYPE_HYBRID
            R.id.normal -> mMap.mapType = GoogleMap.MAP_TYPE_NORMAL
            R.id.satellite -> mMap.mapType = GoogleMap.MAP_TYPE_SATELLITE
            R.id.terrain -> mMap.mapType = GoogleMap.MAP_TYPE_TERRAIN
        }

        return super.onOptionsItemSelected(item)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
    }
    //use to resize image got this code from the below link
    //https://stackoverflow.com/questions/14851641/change-marker-size-in-google-maps-api-v2
    private fun resizeMapIcons(iconName: String?, width: Int, height: Int): Bitmap {
        val imageBitmap = BitmapFactory.decodeResource(
            resources, resources.getIdentifier(
                iconName, "drawable",
                packageName
            )
        )
        return Bitmap.createScaledBitmap(imageBitmap, width, height, false)
    }
}


