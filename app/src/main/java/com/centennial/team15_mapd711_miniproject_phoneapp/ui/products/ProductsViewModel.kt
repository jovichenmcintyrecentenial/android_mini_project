import ProductModel
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ProductsViewModel : ViewModel() {

    //live data for list of products
    val listOfProductLiveData: MutableLiveData<List<ProductModel>?> by lazy {
        MutableLiveData<List<ProductModel>?>()
    }

    //get list of products and update live data valuse
    fun getProducts(context: Context) {
        CoroutineScope(Dispatchers.IO).launch {
//            listOfProductLiveData.postValue(ProductRepository.getAllProducts(context))
        }
    }


}