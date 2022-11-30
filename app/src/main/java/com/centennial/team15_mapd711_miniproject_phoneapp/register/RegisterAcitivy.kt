import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.centennial.team15_mapd711_miniproject_phoneapp.R

class RegisterAcitivy : AppCompatActivity() {

    lateinit var registerViewModel: RegisterViewModel

    //declare view varibles
    private lateinit var username: EditText
    private lateinit var  firstname: EditText
    private lateinit var  lastname: EditText
    private lateinit var  address: EditText
    private lateinit var  city: EditText
    private lateinit var  postalCode: EditText
    private lateinit var  password: EditText
    private lateinit var  rePassword: EditText


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_acitivy)

        //find views
        username = findViewById(R.id.uname)
        firstname = findViewById(R.id.firstname)
        lastname = findViewById(R.id.lastname)
        address = findViewById(R.id.address)
        city = findViewById(R.id.city)
        postalCode = findViewById(R.id.postal)
        password = findViewById(R.id.password1)
        rePassword = findViewById(R.id.password2)

        //connect to view model
        registerViewModel = ViewModelProvider(this).get(modelClass = RegisterViewModel::class.java)
    }

    //validate edit text information if there is an issue throw an exception
    private fun isDataValid(): Boolean {
        Utils.emptyValidation(username,"Please enter a username")
        Utils.emptyValidation(firstname,"Please enter a firstname")
        Utils.emptyValidation(lastname,"Please enter a lastname")
        Utils.emptyValidation(address,"Please enter a address")
        Utils.emptyValidation(city,"Please enter a city")
        Utils.emptyValidation(postalCode,"Please enter a postalCode")
        Utils.emptyValidation(password,"Please enter a password")
        Utils.emptyValidation(rePassword,"Please re-enter password")

        //password check if match
        if(rePassword.text.toString() != password.text.toString()){
            throw UserInputException("Password doesn't match, please try again.")
        }

        return true
    }



    fun onSubmit(view: View) {
        try{
            if(isDataValid()){

                //store edittext values in string varibles
                val username = this.username.text.toString()
                val firstname = this.firstname.text.toString()
                val lastname = this.lastname.text.toString()
                val address = this.address.text.toString()
                val city = this.city.text.toString()
                val postalCode = this.postalCode.text.toString()
                val password = this.password.text.toString()

                //create new customer model
                val customerModel = CustomerModel(username,firstname,lastname,address,city,postalCode,password)
                //use view model to insert data in database
                registerViewModel.insertCustomerData(this,customerModel)

                //display success and pop screen
                Toast.makeText(this,getString(R.string.success_reg),Toast.LENGTH_LONG).show()
                finish()

            }
        }
        //catch  and display user input exception
        catch (e: UserInputException) {
            //display exception message
            Utils.showMessage(this,e.message.toString())
        }
    }

}