//Name: Jovi Chen-Mcintyre
//ID: 301125059

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.centennial.team15_mapd711_miniproject_phoneapp.models.Database
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class CustomerRepository {

    //defining database and live data object as companion objects
    companion object {
        private const val collection = "customers"

        val loginCustomer: MutableLiveData<CustomerModel?> by lazy {
            MutableLiveData<CustomerModel?>()
        }

        //initialize database
        fun getDB(context: Context) : FirebaseFirestore {
            FirebaseApp.initializeApp(context)
            return Firebase.firestore
        }

        //insert customer data
        suspend fun insertData(context: Context, customer:CustomerModel)  {

                Database.getDB()!!
                    .collection(collection)
                    .add(customer)
                    .await()
        }

        //get customer data based on username
        fun getData(context: Context, username:String):CustomerModel? {
//            phoneStoreDatabase = getDB(context)
//
//            var customer = phoneStoreDatabase!!.phonestoreDao().getCustomer(username)
//            loginCustomer.postValue(customer)
//
//            return customer
            return null
        }

        //update customer data
        fun update(context: Context, customer:CustomerModel) {
//            phoneStoreDatabase = getDB(context)
//
//            CoroutineScope(Dispatchers.IO).launch {
//
//                phoneStoreDatabase!!.phonestoreDao().updateCustomer(customer)
//            }

        }
        //check customer login credentials
        fun passwordCheck(context: Context, username: String, password:String):CustomerModel? {
//            phoneStoreDatabase = getDB(context)
//
//            var customer = phoneStoreDatabase!!.phonestoreDao().passwordCheck(username, password)
//            loginCustomer.postValue(customer)
//
//            return customer
            return null
        }

    }
}