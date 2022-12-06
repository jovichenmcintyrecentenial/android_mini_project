package com.centennial.team15_mapd711_miniproject_phoneapp.ui.udpate_customer

import CustomerModel
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class UpdateCustumerViewModel: ViewModel() {

    //live data for customer
    val liveCustomerData: MutableLiveData<CustomerModel?> by lazy {
        MutableLiveData<CustomerModel?>()
    }

    //get and update live data for customer
    fun getCustomer( context:Context) {
//        if custome data avavible in CustomerRepository just take the value of that varible
        if(CustomerRepository.loginCustomer.value != null) {
            liveCustomerData.value = CustomerRepository.loginCustomer.value
        }
        else{
            //if fail to get use from repo then use user name to search for customer information
            val sharedPreference =  context.getSharedPreferences("STORE",Context.MODE_PRIVATE)
            var username = sharedPreference.getString("username","")
            if(username != null){
                CoroutineScope(Dispatchers.IO).launch {
                    liveCustomerData.postValue(CustomerRepository.getData(context, username))
                }
            }

        }
    }

    //update customer data
    fun updateCustomer( context:Context) {
        CoroutineScope(Dispatchers.IO).launch {
            CustomerRepository.update(context, liveCustomerData.value!!)
        }
    }
}