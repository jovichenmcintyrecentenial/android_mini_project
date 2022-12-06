
import android.content.Context
import com.centennial.team15_mapd711_miniproject_phoneapp.models.Database
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await

class OrderRepository {

    //defining database and live data object as companion objects
    companion object {
        val collection = "orders"
        //initialize database
        private fun getDB(context: Context) : FirebaseFirestore {
            FirebaseApp.initializeApp(context)
            return Firebase.firestore
        }
        //function used to update and insert order data
        suspend fun insertUpdateData(context: Context, orderModel: OrderModel) {

            Database.getDB()!!
                .collection(collection)
                .document(orderModel.idCreate())
                .set(orderModel)
                .await()
        }
        //get all order from customer with specified id
        suspend fun getMyProductsOrders(context: Context): List<OrderModel>? {

            var quertSnapshot = Database.getDB()!!.collection(collection)
                .whereEqualTo("custId", CustomerRepository.loginCustomer.value!!.id)
                .get().await()
            if(!quertSnapshot.isEmpty){
                val customers = quertSnapshot.toObjects(OrderModel::class.java)
                return customers
            }
            return null
        }


    }
}