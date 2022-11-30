import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.FirebaseApp
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch


class LoginViewModel: ViewModel() {

    val liveCustomerData: MutableLiveData<CustomerModel?> by lazy {
        MutableLiveData<CustomerModel?>()
    }

    fun getUser(context: Context, username:String, password:String){
        FirebaseApp.initializeApp(context)
        val db = Firebase.firestore
        val user = hashMapOf(
            "first" to "Ada",
            "last" to "Lovelace",
            "born" to 1815
        )


        CoroutineScope(Dispatchers.IO).launch {
//            liveCustomerData.postValue( CustomerRepository.passwordCheck(context, username, password))
            db.collection("users")
                .add(user)
                .addOnSuccessListener { documentReference ->
                    Log.d("TAG", "DocumentSnapshot added with ID: ${documentReference.id}")
                }
                .addOnFailureListener { e ->
                    Log.w("TAG", "Error adding document", e)
                }
        }
    }


}