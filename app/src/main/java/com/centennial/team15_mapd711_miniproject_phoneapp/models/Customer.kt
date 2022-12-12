import java.util.UUID

data class CustomerModel(
    var email: String? = null,
    var firstname: String? = null,
    var lastname: String? = null,
    var address: String? = null,
    var city: String? = null,
    var postal: String? = null,
    var password: String? = null,
    var id:String? = null
){
    //defining a primary key field Id
    fun idCreate(){
        if(id == null){
            id = UUID.randomUUID().toString()
        }
    }
}

