package  com.centennial.team15_mapd711_miniproject_phoneapp.ui.orders

import CustomerModel
import OrderModel
import OrdersViewModel
import ProductOrder
import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.centennial.team15_mapd711_miniproject_phoneapp.R
import com.google.gson.Gson

class OrdersFragment : Fragment() {

    private var ordersViewModel: OrdersViewModel? = null
    private var customerID: String? = null

    //list for adaptor so i can update it properly when livedata changes
    var list:MutableList<OrderModel>? = null

    //trigger get orders when back press to update status rendered on list view
    override fun onResume() {
        super.onResume()
        if(ordersViewModel!=null){
            context?.let { ordersViewModel!!.getOrders(it) }
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        //connect to view model
        ordersViewModel =
        ViewModelProvider(this).get(OrdersViewModel::class.java)

        var view = inflater.inflate(R.layout.fragment_orders, container, false)

        //find listview
        var listView = view.findViewById<ListView>(R.id.list)

        //nested live data observer to first get customer data to search order table for logined in used transaction
        ordersViewModel!!.liveCustomerData.observe(viewLifecycleOwner, Observer { customerModel ->
            customerID = customerModel!!.id
            if(customerModel != null){
                //observer to update listview data with my list of orders
                ordersViewModel!!.listOfOrdersLiveData.observe(viewLifecycleOwner,Observer{ listOfOrders ->
                    if(listOfOrders != null){
                        //create list adaptor
                        var listAdaptor = activity?.let { activity ->
                            OrderListAdaptor(activity, listOfOrders, customerModel)
                        }

                        //save list information in list and prevent it from overriding whenever this
                        //observer trigger again so i can properly maintain the state of list view
                        //so it can be refreshed when needed
                        if(list == null){
                            list = listOfOrders as MutableList<OrderModel>?
                        }
                        //attach adaptor to listview once
                        if(listView.adapter == null) {
                            listView.adapter = listAdaptor
                        }else{
                            //on list update clear list and update with new information and notify
                            //list view to invalidate data
                            list!!.clear()
                            list!!.addAll(listOfOrders)
                            listView.invalidate()
                            listView.invalidateViews()
                        }

                    }
                })
                //get logined in customer's orders from order database
                context?.let { ordersViewModel!!.getOrders(it) }
            }

        })

        //get customer data so observe can use id to pull order that are specific to this customer
        context?.let { ordersViewModel!!.getCustomer(it) }

        //create a listener for on click aciton on list view
        //this on click listen take the click item in the list and convert it to a PhoneCheckout object
        //this was done inorder to reuse the order summary page but it will now render as order details instead
        listView.setOnItemClickListener { parent, view, position, id ->

//            var newIntent = Intent(activity, OrderSummaryActivity::class.java)
//            //get productOrder from list position
//            var productOrder = ordersViewModel!!.listOfOrdersLiveData.value!![position]
//            //get customer data
//            var customerModel = ordersViewModel!!.liveCustomerData.value!!
//
//            //create PhoneCheckOut base on product model
//            var checkoutObj = PhoneCheckOut(productOrder.productModel!!)
//
//            //update remaining field with customer data so it render properly on order summary page
//            checkoutObj.address = customerModel.address
//            checkoutObj.city = customerModel.city
//            checkoutObj.postalCode = customerModel.postal
//
//            checkoutObj.firstName = customerModel.firstname
//            checkoutObj.lastName = customerModel.lastname
//
//            checkoutObj.orderModel = productOrder.orderModel
//
//            //boolean that is use to indicate what state the order summary page should render
//            // either as Order Details or Order Summary
//            checkoutObj.isOrderDetail = true
//
//            //update create PhoneCheckOut and serialize data and pass to intent
//            newIntent.putExtra("checkout", Gson().toJson(checkoutObj))
//            //load new Intent
//            startActivity(newIntent)
        }
        return view
    }

    class OrderListAdaptor(context: Activity, list:List<OrderModel>,customerModel: CustomerModel):  BaseAdapter(){

        var context = context
        var list = list
        //save customer model to show address on list item
        var customerModel = customerModel

        override fun getCount(): Int {
            return list.count()
        }

        override fun getItem(position: Int): OrderModel {
            return list[position]
        }

        override fun getItemId(position: Int): Long {
            return position.toLong()
        }

        override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {

            var inflatedView:View? = convertView
            //load data at position
            val productOrder = list[position]


            if(inflatedView == null){
                //inflate custom list item layout
                inflatedView = LayoutInflater.from(context).
                inflate(R.layout.order_list_item, parent, false)
            }

            //find views
            val priceTextView = inflatedView?.findViewById<TextView>(R.id.price)
            val phoneImage = inflatedView?.findViewById<ImageView>(R.id.image)
            val name = inflatedView?.findViewById<TextView>(R.id.name)
            val address1 = inflatedView?.findViewById<TextView>(R.id.address1)
            val address2 = inflatedView?.findViewById<TextView>(R.id.address2)
            val orderStatus = inflatedView?.findViewById<TextView>(R.id.order_status)

            //dynamically load phone images using phone uri
//            val resourceImage: Int = context.resources.getIdentifier(productOrder.productModel!!.imageUri, "drawable", context.packageName)
//            phoneImage?.setImageResource(resourceImage)

            //update phone name in list
            name?.text = productOrder.product!!.phoneModel
            orderStatus?.text = productOrder.status
            address1?.text = customerModel.address
            address2?.text = customerModel.postal

            //update price on list time
            priceTextView?.text = productOrder.product!!.formattedPrice()
            return inflatedView!!
        }

    }

}

