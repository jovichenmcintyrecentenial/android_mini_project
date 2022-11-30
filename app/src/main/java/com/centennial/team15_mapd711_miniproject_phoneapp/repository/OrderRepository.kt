
import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class OrderRepository {

    //defining database and live data object as companion objects
    companion object {
        //initialize database
        private fun getDB(context: Context) : FirebaseFirestore {
            FirebaseApp.initializeApp(context)
            return Firebase.firestore
        }
        //function used to update and insert order data
        fun insertUpdateData(context: Context, orderModel: OrderModel) {
//            phoneStoreDatabase = getDB(context)
//
//            CoroutineScope(Dispatchers.IO).launch {
//                phoneStoreDatabase!!.phonestoreDao().insertOrder(orderModel)
//            }

        }
        //get all order from customer with specified id
        fun getMyProductsOrders(context: Context, id: Int): List<ProductOrder>? {

//            phoneStoreDatabase = getDB(context)
//
//            return phoneStoreDatabase!!.phonestoreDao().getMyOrders(id)
            return null
        }


    }
}