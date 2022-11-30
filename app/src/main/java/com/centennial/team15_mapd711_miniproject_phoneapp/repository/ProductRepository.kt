import ProductModel
import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.firestore.FirebaseFirestore

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.ArrayList

class ProductRepository {


    //defining database and live data object as companion objects
    companion object {
        val listOfPhones: MutableLiveData<List<ProductModel>> by lazy {
            MutableLiveData<List<ProductModel>>()
        }

        //initialize database
        private fun getDB(context: Context) : FirebaseFirestore {
            return CustomerRepository.getDB(context)
        }
        //get all product from database
        fun getAllProducts(context: Context):List<ProductModel>? {

            var db = getDB(context)

//            var listOfProducts = phoneStoreDatabase!!.phonestoreDao().getAllProducts()
//
//            listOfPhones.postValue(listOfProducts)
//
//            return listOfProducts
            return null

        }

    }
}