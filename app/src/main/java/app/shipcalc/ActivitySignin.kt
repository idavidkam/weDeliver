package app.shipcalc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.google.android.material.textfield.TextInputEditText

class ActivitySignin : AppCompatActivity() {
    /*lateinit var firstName: TextInputEditText
    lateinit var lastName: TextInputEditText
    lateinit var phoneNumber: TextInputEditText
    lateinit var password: TextInputEditText*/
    lateinit var signinButton: Button


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)
        supportActionBar?.hide()
        var flagIsEmpty : Boolean = false
        var firstName = findViewById<TextInputEditText>(R.id.signin_fName).text.toString()
        var lastName = findViewById<TextInputEditText>(R.id.signin_lName).text.toString()
        var phoneNumber = findViewById<TextInputEditText>(R.id.signin_phone).text.toString()
        var password = findViewById<TextInputEditText>(R.id.signin_password).text.toString()

        if (firstName == ""){
            findViewById<TextInputEditText>(R.id.addPkgTextWeight).
            error = getString(R.string.enterValue)
            flagIsEmpty = true
        }

        if (lastName == ""){
            findViewById<TextInputEditText>(R.id.addPkgTextWeight).
            error = getString(R.string.enterValue)
            flagIsEmpty = true
        }

        if (phoneNumber == ""){
            findViewById<TextInputEditText>(R.id.addPkgTextWeight).
            error = getString(R.string.enterValue)
            flagIsEmpty = true
        }

        if (password == ""){
            findViewById<TextInputEditText>(R.id.addPkgTextWeight).
            error = getString(R.string.enterValue)
            flagIsEmpty = true
        }

    }
}