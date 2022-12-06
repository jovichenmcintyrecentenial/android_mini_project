
import CustomerModel
import ProductOrder
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OrdersViewModel : ViewModel() {
    //live data for customer
    val liveCustomerData: MutableLiveData<CustomerModel?> by lazy {
        MutableLiveData<CustomerModel?>()
    }
    //live data for list of orders
    val listOfOrdersLiveData: MutableLiveData<List<OrderModel>?> by lazy {
        MutableLiveData<List<OrderModel>?>()
    }
    //get customer information and update live data
    fun getCustomer( context: Context) {
        if(CustomerRepository.loginCustomer.value != null) {
            liveCustomerData.value = CustomerRepository.loginCustomer.value
        }

    }
    //get orders and update live data
    fun getOrders( context: Context) {
        CoroutineScope(Dispatchers.IO).launch {
            listOfOrdersLiveData.postValue(OrderRepository.getMyProductsOrders(context))
        }
    }
}