package com.centennial.team15_mapd711_miniproject_phoneapp.ui.products

import PhoneCheckOut
import ProductModel
import ProductsViewModel
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.centennial.team15_mapd711_miniproject_phoneapp.R
import com.centennial.team15_mapd711_miniproject_phoneapp.services.ImageLoader
import com.centennial.team15_mapd711_miniproject_phoneapp.ui.product_review.ProductReviewActivity
import com.google.gson.Gson

class ProductsFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        //connect to view model
        val productsViewModel =
            ViewModelProvider(this).get(modelClass = ProductsViewModel::class.java)


        val view = inflater.inflate(R.layout.fragment_home, container, false)

        //get share prefence instants to display user name on screen
        val sharedPreference =  this.requireContext().getSharedPreferences("STORE", Context.MODE_PRIVATE)

        var username = sharedPreference.getString("username","")
        val welcome: TextView = view.findViewById(R.id.welcome)

        //display user name on screen
        welcome.text = getString(R.string.welcome)+" "+username

        //find list view
        var listView = view.findViewById<RecyclerView>(R.id.recyclerview)

        listView.layoutManager = LinearLayoutManager(requireContext())

        //observer used to update list view with products
        activity?.let { productsViewModel.listOfProductLiveData.observe(it, Observer { listOfPhones ->
            if(listOfPhones != null) {
                //create instance of a custom listAdpator called PhoneListAdaptor
                var listAdaptor = PhoneListAdaptor(requireActivity(), listOfPhones){ product ->
                    var newIntent = Intent(activity, ProductReviewActivity::class.java)
                    //update create PhoneCheckOut and serialize data and pass to intent
                    newIntent.putExtra("checkout", Gson().toJson(PhoneCheckOut(product)))
                    //load new Intent
                    startActivity(newIntent)
                }

                //attach adaptor to listview
                listView.adapter = listAdaptor
            }


        }) }

        activity?.let { productsViewModel.getProducts(it) }


        return view
    }

    class PhoneListAdaptor(context: Activity, private val mList: List<ProductModel>,private val onItemClicked: (ProductModel) -> Unit) : RecyclerView.Adapter<PhoneListAdaptor.ViewHolder>() {

        var context = context

        // create new views
        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            // inflates the card_view_design view
            // that is used to hold list item
            val view = LayoutInflater.from(parent.context)
                .inflate(R.layout.phone_list_item, parent, false)

            return ViewHolder(view)
        }

        // binds the list items to a view
        override fun onBindViewHolder(holder: ViewHolder, position: Int) {

            val phone = mList[position]

            holder.storage!!.text = context.getString(R.string.storage)+" "+phone.storageCapacity
            holder.phoneColor!!.text = context.getString(R.string.color_phone)+phone.phoneColor
            //dynamically load phone images using phone uri
            ImageLoader.setImage(context.getString(R.string.base_url)+phone.imageUri!!+".jpg",holder.phoneImage!!)

            //update phone name in list
            holder.phoneNameTextView?.text = phone.phoneModel
            //update price on list time
            holder.priceTextView?.text = phone.formattedPrice()

            holder.root.setOnClickListener {
                onItemClicked(phone)
            }

        }

        // return the number of the items in the list
        override fun getItemCount(): Int {
            return mList.count()
        }

        // Holds the views for adding it to image and text
        class ViewHolder(inflatedView: View) : RecyclerView.ViewHolder(inflatedView) {

            //find views
            val priceTextView = inflatedView.findViewById<TextView>(R.id.phone_price)
            val phoneImage = inflatedView.findViewById<ImageView>(R.id.phone_image)
            val phoneNameTextView = inflatedView.findViewById<TextView>(R.id.phone_name)
            val storage = inflatedView.findViewById<TextView>(R.id.storage)
            val phoneColor = inflatedView.findViewById<TextView>(R.id.phone_color)
            val root = inflatedView.findViewById<LinearLayout>(R.id.root_view)



        }
    }

}