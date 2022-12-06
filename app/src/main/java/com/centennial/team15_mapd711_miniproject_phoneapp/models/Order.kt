data class OrderModel(
    //defining a column custId
    var custId: String,
    //defining a column productId
    var product: ProductModel,
    //defining a column status
    var status: String,
    //defining a column orderDate
    var orderDate: Long

)
{
    //defining a primary key field Id
    var id: Int? = null
}