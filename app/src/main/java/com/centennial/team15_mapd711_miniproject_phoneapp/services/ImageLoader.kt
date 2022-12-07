package com.centennial.team15_mapd711_miniproject_phoneapp.services

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.nfc.Tag
import android.widget.ImageView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.net.URL

class ImageLoader {

    companion object{
        fun setImage(url:String,imageView: ImageView) {
            val url = URL(url)
            CoroutineScope(Dispatchers.IO).launch {
                try {
                    val bitmap =  BitmapFactory.decodeStream(url.openConnection().getInputStream())

                    withContext(Dispatchers.Main) {
                        imageView.setImageBitmap(bitmap)
                    }
                }
                catch (e:java.lang.Exception){
                    //error
                }
            }
        }

    }
}