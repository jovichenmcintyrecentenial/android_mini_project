package com.centennial.team15_mapd711_miniproject_phoneapp.ui.product_review

import PhoneCheckOut
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.centennial.team15_mapd711_miniproject_phoneapp.MapsActivity
import com.centennial.team15_mapd711_miniproject_phoneapp.R
import com.centennial.team15_mapd711_miniproject_phoneapp.services.ImageLoader
import com.centennial.team15_mapd711_miniproject_phoneapp.ui.checkout.CheckOutActivity
import com.google.gson.Gson

class ProductReviewActivity : AppCompatActivity() {

    //declare views
    lateinit var  selectColorPhone:String
    lateinit var  checkoutObj: PhoneCheckOut
    lateinit var selectInternalStorage:String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_product_review)
        //set title
        supportActionBar?.title = getString(R.string.product_review)
        //set default internal size
        selectInternalStorage = resources.getString(R.string._64_gb)

        //deserialize PhoneCheckOut in varible checkoutObj
        checkoutObj = Gson().fromJson(intent.getStringExtra("checkout"),PhoneCheckOut::class.java)

        //find views
        var phoneImageView = findViewById<ImageView>(R.id.phone_image)
        var phoneNameTextView = findViewById<TextView>(R.id.phone_name)
        var radioButton = findViewById<TextView>(R.id.opt_64GB)
        val spinner: Spinner = findViewById(R.id.color_spinner)
        // data to populate the RecyclerView with
        // data to populate the RecyclerView with
        val colors: ArrayList<String> = ArrayList()
        colors.add(checkoutObj.phone.phoneColor!!)
        //load spinner with string array daata
        ArrayAdapter.createFromResource(
            this,
            R.array.string_of_phone_colors,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }

//        radioButton.text = checkoutObj.phone.storageCapacity

        //add click listener to save select value when user selects a color from spinner
        spinner.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                selectColorPhone = resources.getStringArray(R.array.string_of_phone_colors)[position]
                checkoutObj.color = selectColorPhone
            }

        }

        //display phone name in view
        phoneNameTextView.text = checkoutObj.phone.phoneModel

        ImageLoader.setImage(getString(R.string.base_url)+checkoutObj.phone.imageUri+".jpg",phoneImageView)


    }

    //handle selecting of radio button options
    fun onSelectRadioButton(view: View) {

        if(view is RadioButton){

            when(view.id){
                R.id.opt_64GB -> selectInternalStorage = getString(R.string._64_gb)
                R.id.opt_128GB -> selectInternalStorage = getString(R.string._128_gb)
                R.id.opt_256GB -> selectInternalStorage = getString(R.string._256_gb)
            }
        }
        checkoutObj.internalStorageSize = selectInternalStorage

    }

    fun onSubmit(view: View) {
        //create new intent to CheckOutActivity
        var newIntent = Intent(this, CheckOutActivity::class.java )

        //serialize checkoutObj and save to intent
        newIntent.putExtra("checkout",Gson().toJson(checkoutObj))
        //start intent
        startActivity(newIntent)
    }

    fun onFindStores(view: View) {

        //create new intent to CheckOutActivity
        var newIntent = Intent(this, MapsActivity::class.java )

        //serialize checkoutObj and save to intent
        newIntent.putExtra("product",Gson().toJson(checkoutObj.phone))
        //start intent
        startActivity(newIntent)
    }
}