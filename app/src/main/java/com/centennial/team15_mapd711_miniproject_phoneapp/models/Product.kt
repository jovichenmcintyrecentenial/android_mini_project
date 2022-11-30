
import android.icu.text.NumberFormat
import java.util.*

data class ProductModel(

    //defining a column phoneModel
    var phoneModel: String,
    //defining a column price
    var price: Double,
    var imageUri: String,
    //defining a column phoneMake
    var phoneMake: String,
    //defining a column phoneColor
    var phoneColor: String,
    //defining a column storageCapacity
    var storageCapacity: String


)
{
    fun getFormatterPrice(): String {
        return "$"+ NumberFormat.getNumberInstance(Locale.US).format(price);
    }
}

