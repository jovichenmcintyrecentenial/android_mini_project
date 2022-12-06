package com.centennial.team15_mapd711_miniproject_phoneapp.ui.order_summary

import OrderModel
import android.content.Context
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OrderViewModel: ViewModel() {
    //function to add order model to database
    fun addOrder( context:Context,orderModel: OrderModel) {
        CoroutineScope(Dispatchers.IO).launch {
            OrderRepository.insertUpdateData(context, orderModel)
        }
    }
    //function to update order in database
    fun updateOrder( context:Context,orderModel: OrderModel) {
        CoroutineScope(Dispatchers.IO).launch {
            OrderRepository.insertUpdateData(context, orderModel)
        }
    }
}