import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class LoginViewModel: ViewModel() {

    val liveCustomerData: MutableLiveData<CustomerModel?> by lazy {
        MutableLiveData<CustomerModel?>()
    }

    fun getUser(context: Context, username:String, password:String){
//        CoroutineScope(Dispatchers.IO).launch {
//            liveCustomerData.postValue( CustomerRepository.passwordCheck(context, username, password))
//        }
    }


}