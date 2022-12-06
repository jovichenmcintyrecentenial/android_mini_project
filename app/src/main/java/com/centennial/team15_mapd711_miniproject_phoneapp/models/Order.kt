import java.util.UUID

data class OrderModel(
    //defining a column custId
    var custId: String? = null,
    //defining a column productId
    var product: ProductModel?  = null,
    //defining a column status
    var status: String?  = null,
    //defining a column orderDate
    var orderDate: Long?  = null,
    var id: String? = null
)
{
    //defining a primary key field Id
    fun idCreate():String{
        if(id == null){
            return UUID.randomUUID().toString()
        }
        return id!!
    }

}