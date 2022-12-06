package com.centennial.team15_mapd711_miniproject_phoneapp.ui.order_summary


import OrderModel
import PhoneCheckOut
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView
import android.widget.TableRow
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.centennial.team15_mapd711_miniproject_phoneapp.BottomNavActivity
import com.centennial.team15_mapd711_miniproject_phoneapp.R
import com.centennial.team15_mapd711_miniproject_phoneapp.ui.udpate_customer.UpdateCustumerViewModel
import com.google.gson.Gson
import java.util.*

class OrderSummaryActivity : AppCompatActivity() {

    private lateinit var checkoutObj:PhoneCheckOut
    private lateinit var orderViewModel: OrderViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_summary)

        //connect to order view model
        orderViewModel = ViewModelProvider(this).get(modelClass = OrderViewModel::class.java)

        //update title
        supportActionBar?.title = getString(R.string.order_summary)

        //find and store views
        val phoneImage = findViewById<ImageView>(R.id.phone_image)
        val companyName = findViewById<TextView>(R.id.company)
        val modelName = findViewById<TextView>(R.id.model)
        val phoneColor = findViewById<TextView>(R.id.phone_color)
        val internalStorage = findViewById<TextView>(R.id.internal_storage)
        val cardType = findViewById<TextView>(R.id.card_type)
        val last4Digits = findViewById<TextView>(R.id.last_4_digits)
        val price = findViewById<TextView>(R.id.price)


        //find views
        val userName = findViewById<TextView>(R.id.user_name)
        val address = findViewById<TextView>(R.id.address)
        val city = findViewById<TextView>(R.id.city)
        val postalCode = findViewById<TextView>(R.id.postal_code)

        //deserialize data from intent
        checkoutObj = Gson().fromJson(intent.getStringExtra("checkout"), PhoneCheckOut::class.java)

        //display phone image
        val resourceImage: Int = resources.getIdentifier(checkoutObj.phone.imageUri, "drawable", packageName)
        phoneImage?.setImageResource(resourceImage)

        //populate views
        companyName.text = checkoutObj.phone.phoneMake
        modelName.text = checkoutObj.phone.phoneModel
        phoneColor.text = checkoutObj.phone.phoneColor
        internalStorage.text = checkoutObj.phone.storageCapacity
        price.text = checkoutObj.phone.formattedPrice()

        //populate views
        userName.text = checkoutObj.firstName+" "+checkoutObj.lastName
        address.text = checkoutObj.address
        city.text = checkoutObj.city
        postalCode.text = checkoutObj.postalCode

        //connect to customer update view model
        val updateViewModel = ViewModelProvider(this).get(modelClass = UpdateCustumerViewModel::class.java)

        //check state of view if is  rendering for order details or order summary
        if(checkoutObj.isOrderDetail){

            //hide credit card info
            findViewById<TableRow>(R.id.last_4_digits_row).visibility = View.GONE
            findViewById<TableRow>(R.id.card_type_row).visibility = View.GONE

            //find views
            val orderDate = findViewById<TextView>(R.id.order_date)
            val orderDateRow = findViewById<TableRow>(R.id.order_date_row)
            val titleTextView = findViewById<TextView>(R.id.title)
            val button = findViewById<Button>(R.id.login)

            //show date field on order details
            orderDateRow.visibility = View.VISIBLE
            orderDate.text = checkoutObj.orderModel!!.orderDate?.let { Date(it).toString() }

            //change titles to order details
            titleTextView.text = getString(R.string.order_details)
            supportActionBar?.title = getString(R.string.order_details)

            //update button to say cancel or on order details state
            button.text = "Cancel Order"

            //hide cancel button if order not in Ordered state
            if(checkoutObj.orderModel!!.status != "Ordered"){
                button.visibility = View.GONE
            }
        }
        else{

            //display card information
            cardType.text = checkoutObj.cardType.toString()
            last4Digits.text = checkoutObj.cardNumber?.substring(12)

            //obeserver to store order information on getting customer information
            updateViewModel.liveCustomerData.observe(this, androidx.lifecycle.Observer {
                if(it != null){
                    //date stamp
                    val unixTime = System.currentTimeMillis()
                    var order = OrderModel(it.id, checkoutObj.phone, "Ordered",unixTime)

                    //save orderModel data to database
                    orderViewModel.addOrder(this,order)
                }
            })
            //trigger fetch for customer to update live data
            updateViewModel.getCustomer(this)
        }

    }

    fun onComplete(view: View) {
        //if this is rendering as order details page
        //this would be a cancel button press
        if(checkoutObj.isOrderDetail) {
            if(checkoutObj.orderModel != null) {

                //update order model to cancelled
                //in antipaction to update order data in database
                checkoutObj.orderModel!!.status = "Cancelled"

                //save date in varible to calculator hours passed
                var orderDate = checkoutObj.orderModel!!.orderDate?.let { Date(it) }
                var nowDate = Date(System.currentTimeMillis())

                //use the below link as reference
                //https://stackoverflow.com/questions/2003521/find-total-hours-between-two-dates
                //calculate hours elapse
                var secs: Long = (nowDate.time - orderDate!!.time)/1000
                val hours = (secs / 3600).toInt()

                //if hours is less than 24 allow user to cancell order
                if(hours < 24) {
                    //update order model with cancelled status in database
                    orderViewModel.updateOrder(this, checkoutObj.orderModel!!)

                    Toast.makeText(this,getString(R.string.order_cancelled),Toast.LENGTH_SHORT).show()
                    finish()
                }
                else{
                    Toast.makeText(this,getString(R.string.error_cant_cancel),Toast.LENGTH_SHORT).show()
                }
            }
        }
        else{
            //on go to home button press clear stack and navigate to BottomNavigationActivity
            var intent = Intent(this, BottomNavActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK
            startActivity(intent)
        }
    }
}