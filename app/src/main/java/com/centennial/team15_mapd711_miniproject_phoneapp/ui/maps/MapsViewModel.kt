import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.centennial.team15_mapd711_miniproject_phoneapp.models.Store
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class MapsViewModel: ViewModel() {

    val liveListOfStores: MutableLiveData<List<Store>> by lazy {
        MutableLiveData<List<Store>>()
    }

    fun getAllStores(context: Context){

        CoroutineScope(Dispatchers.IO).launch {
            liveListOfStores.postValue( StoreRepository.getAllStores(context))
        }
    }


}