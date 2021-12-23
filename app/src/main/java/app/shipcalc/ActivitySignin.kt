package app.shipcalc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.android.material.textfield.TextInputEditText

class ActivitySignin : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)
        supportActionBar?.hide()

        var flagIsEmpty : Boolean = false
        var firstName = findViewById<TextInputEditText>(R.id.signin_fName)
        var lastName = findViewById<TextInputEditText>(R.id.signin_lName)
        var phoneNumber = findViewById<TextInputEditText>(R.id.signin_phone)
        var password = findViewById<TextInputEditText>(R.id.signin_password)
        var signinButton = findViewById<Button>(R.id.signin_button)

        signinButton.setOnClickListener {

            // Validate there is not empty fields
            if (firstName.text.toString() == ""){
                firstName.error = getString(R.string.enterValue)
                flagIsEmpty = true
            }
            if (lastName.text.toString() == ""){
                lastName.error = getString(R.string.enterValue)
                flagIsEmpty = true
            }
            if (phoneNumber.text.toString() == ""){
                phoneNumber.error = getString(R.string.enterValue)
                flagIsEmpty = true
            }
            if (password.text.toString() == ""){
                password.error = getString(R.string.enterValue)
                flagIsEmpty = true
            }
            if(flagIsEmpty)
                return@setOnClickListener


        }
    }
}