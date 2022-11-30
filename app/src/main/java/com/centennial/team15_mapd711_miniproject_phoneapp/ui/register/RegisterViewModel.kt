package com.centennial.team15_mapd711_miniproject_phoneapp.ui.register

import CustomerModel
import CustomerRepository
import android.content.Context
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class RegisterViewModel: ViewModel() {

    //insert user data to database
    fun insertCustomerData( context:Context,customerModel: CustomerModel) {
        CoroutineScope(Dispatchers.IO).launch {
            CustomerRepository.insertData(context, customerModel)
        }
    }
}