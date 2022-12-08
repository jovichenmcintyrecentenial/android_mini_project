import android.content.Context
import androidx.lifecycle.MutableLiveData
import com.centennial.team15_mapd711_miniproject_phoneapp.R
import com.centennial.team15_mapd711_miniproject_phoneapp.models.Database
import com.centennial.team15_mapd711_miniproject_phoneapp.models.Store
import com.google.gson.Gson

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import java.util.ArrayList

class StoreRepository {


    //defining database and live data object as companion objects
    companion object {

        val listOfStores: MutableLiveData<List<Store>> by lazy {
            MutableLiveData<List<Store>>()
        }

        suspend fun getAllStores(context: Context):List<Store>? {

            try {
                val res = context.resources
                val inputStream = res.openRawResource(R.raw.maps_stores)
                val b = ByteArray(inputStream.available())
                inputStream.read(b)
                var json = String(b)
                var stores:ArrayList<Store> = Gson().fromJson(json, Array<Store>::class.java).toList() as ArrayList<Store>
                var x = ""
                listOfStores.postValue(stores)
                return stores
            } catch (e: Exception) {
                e.printStackTrace();
            }

            return null
        }

    }
}