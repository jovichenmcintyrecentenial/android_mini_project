package com.centennial.team15_mapd711_miniproject_phoneapp.ui.udpate_customer

import UserInputException
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.centennial.team15_mapd711_miniproject_phoneapp.R

class UpdateCustomerActivity : AppCompatActivity() {

    lateinit var updateViewModel: UpdateCustumerViewModel

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
        val button = findViewById<Button>(R.id.register)

        //hide view unnecessary views for registration xml because xml is reuse
        // for update profile activity
        findViewById<TextView>(R.id.uname_label).visibility = View.GONE
        findViewById<TextView>(R.id.fname_label).visibility = View.GONE
        findViewById<TextView>(R.id.lname_label).visibility = View.GONE
        username.visibility = View.GONE
        firstname.visibility = View.GONE
        lastname.visibility = View.GONE

        //change button title
        button.text = getString(R.string.update)

        //connect to view model
        updateViewModel = ViewModelProvider(this).get(modelClass = UpdateCustumerViewModel::class.java)

        //object live data to pre fill edittext field with current customer data
        updateViewModel.liveCustomerData.observe(this, Observer {

            if(it != null) {
                username.setText(it.email)
                city.setText(it.city)
                address.setText(it.address)
                postalCode.setText(it.postal)
            }

        })

        //trigger get customer
        updateViewModel.getCustomer(this)

        button.setOnClickListener {
            onSubmit()
        }

    }

    private fun onSubmit() {
        try{
            if(isDataValid()){
                //save data in string varibles
                val address = this.address.text.toString()
                val city = this.city.text.toString()
                val postalCode = this.postalCode.text.toString()
                val password = this.password.text.toString()

//              update live data model information to data user entered
                updateViewModel.liveCustomerData.value!!.address = address
                updateViewModel.liveCustomerData.value!!.city = city
                updateViewModel.liveCustomerData.value!!.postal = postalCode
                updateViewModel.liveCustomerData.value!!.city = city

//                if password was edited update model data for password other wise ignore
                if(rePassword.text.toString().isNotEmpty() || this.password.text.toString().isNotEmpty() ) {
                    updateViewModel.liveCustomerData.value!!.password = password
                }

                //trigger customer data update
                updateViewModel.updateCustomer(this)


                //display success and pop
                Toast.makeText(this,getString(R.string.update_success), Toast.LENGTH_LONG).show()
                finish()


            }
        }
        //catch  and display user input exception
        catch (e: UserInputException) {
            //display exception message
            Utils.showMessage(this,e.message.toString())
        }
    }

    //validate edit text information if there is an issue throw an exception
    private fun isDataValid(): Boolean {
        Utils.emptyValidation(address,getString(R.string.enter_address))
        Utils.emptyValidation(city,getString(R.string.enter_city))
        Utils.emptyValidation(postalCode,getString(R.string.enter_postal))

        //if password was enter in either fields do password validation else if not just ignore password fields
        if(rePassword.text.toString().isNotEmpty() || password.text.toString().isNotEmpty() ) {

            Utils.emptyValidation(password,getString(R.string.enter_password))
            Utils.emptyValidation(rePassword,getString(R.string.enter_re_password))

            //check if password match
            if (rePassword.text.toString() != password.text.toString()) {
                throw UserInputException(getString(R.string.password_not_match))
            }
        }

        return true
    }
}