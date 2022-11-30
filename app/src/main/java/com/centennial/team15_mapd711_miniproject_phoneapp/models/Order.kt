data class OrderModel(
    //defining a column custId
    var custId: Int,
    //defining a column productId
    var productId: Int,
    //defining a column status
    var status: String,
    //defining a column orderDate
    var orderDate: Long

)
{
    //defining a primary key field Id
    var id: Int? = null
}