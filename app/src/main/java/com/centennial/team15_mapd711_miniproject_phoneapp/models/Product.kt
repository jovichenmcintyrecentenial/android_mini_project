
import android.icu.text.NumberFormat
import java.util.*

data class ProductModel(

    var phoneModel: String? = null,
    var price: Double? = null,
    var imageUri: String? = null,
    var phoneMake: String? = null,
    var phoneColor: String? = null,
    var storageCapacity: String? = null,


)
{
    fun formattedPrice(): String {
        return "$"+ NumberFormat.getNumberInstance(Locale.US).format(price);
    }
}

