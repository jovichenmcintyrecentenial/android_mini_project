import CustomerRepository.Companion.loginCustomer
import ProductModel
import android.content.Context
import android.provider.ContactsContract.Data
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.centennial.team15_mapd711_miniproject_phoneapp.R
import com.centennial.team15_mapd711_miniproject_phoneapp.models.Database
import com.google.firebase.firestore.FirebaseFirestore

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.ArrayList

class ProductRepository {


    //defining database and live data object as companion objects
    companion object {

        val collection:String = "products"
        val listOfPhones: MutableLiveData<List<ProductModel>> by lazy {
            MutableLiveData<List<ProductModel>>()
        }

        //the main purpose of this function is to add product data to database
        //if the function detect that there is product data in database already it won't
        //insert the data again
        fun initialProductData(context:Context) {
            //populate list of stuff if not populated already
            CoroutineScope(Dispatchers.IO).launch {
                var obj = getAllProducts(context)
                if(obj == null){
                    val productModels = ArrayList<ProductModel>()
                    productModels.add(ProductModel(context.getString(R.string.oppo_reno8),756.81,"oppo_reno8_pro","Oppo", "Blue","128 GB"))
                    productModels.add(ProductModel(context.getString(R.string.oppo_reno8),756.81,"oppo_reno8_pro","Oppo", "Green","64 GB"))
                    productModels.add(ProductModel(context.getString(R.string.oppo_A77s),303.38,"oppo_a77s","Oppo", "Red","64 GB"))
                    productModels.add(ProductModel(context.getString(R.string.oppo_reno7_5G),488.25,"oppo_reno7","Oppo", "Blue","128 GB"))
                    productModels.add(ProductModel(context.getString(R.string.google_pixel_7_pro),1179.99,"google_pixel7_pro_new","Google", "Red","256 GB"))
                    productModels.add(ProductModel(context.getString(R.string.google_pixel_6_pro),900.99,"google_pixel_6_pro","Google", "Red","128 GB"))
                    productModels.add(ProductModel(context.getString(R.string.google_pixel_5_pro),600.99,"google_pixel_5","Google", "Red","256 GB"))
                    productModels.add(ProductModel(context.getString(R.string.samung_s22_ultra_5G),1499.99,"samsung_galaxy_s22_ultra_5g","Samsung", "Red","64 GB"))
                    productModels.add(ProductModel(context.getString(R.string.samung_galaxy_a53_5g),449.99,"samsung_galaxy_a53_5g","Samsung", "Red","128 GB"))
                    productModels.add(ProductModel(context.getString(R.string.samung_galaxy_a13),329.99,"samsung_galaxy_a13","Samsung", "Red","64 GB"))
                    productModels.add(ProductModel(context.getString(R.string.iphone_14_pro_max),1549.99,"apple_iphone_14_pro_max","Apple", "Red","256 GB"))
                    productModels.add(ProductModel(context.getString(R.string.iphone_13_pro_max),1399.99,"apple_iphone_13_pro_max","Apple", "Red","64 GB"))
                    productModels.add(ProductModel(context.getString(R.string.iphone_12_pro_max),1029.99,"apple_iphone_12_pro_max","Apple", "Red","256 GB"))

                    withContext(Dispatchers.IO) {
                        for(productModel in productModels){
                            Database.getDB()!!.collection(collection).add(productModel).await()
                        }
                    }
                }
            }
        }

        //get all product from database
        suspend fun getAllProducts(context: Context):List<ProductModel>? {

            var quertSnapshot = Database.getDB()!!.collection(collection)
                .get().await()

            if(!quertSnapshot.isEmpty){

                val products = quertSnapshot.toObjects(ProductModel::class.java)
                return products
            }

            return null
        }

    }
}