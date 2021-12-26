package app.shipcalc

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText

class ActivityLogin : AppCompatActivity() {
    lateinit var createAccountButton: Button
    lateinit var loginButton: Button
    lateinit var mySharedPreferences: SharedPreferences
    lateinit var phoneNumberET: TextInputEditText
    lateinit var passwordET: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()
        phoneNumberET = findViewById(R.id.login_phone)
        passwordET = findViewById(R.id.login_password)

        var mySharedPreferences = getSharedPreferences("registeredUsers", MODE_PRIVATE)
        var phoneNumber: String = mySharedPreferences.getString("LastPhoneNumber", "").toString()
        if (phoneNumber != "") {
            phoneNumberET.setText(phoneNumber)
            passwordET.setText(mySharedPreferences.getString(phoneNumber, ""))
        }

        //move to next page - homepage
        loginButton = findViewById(R.id.login_button)

        loginButton.setOnClickListener {
            if (passwordET.text.toString().isEmpty() || phoneNumberET.text.toString().isEmpty()) {
                Toast.makeText(
                    this,
                    "The password or phone number can't be empty",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
            else if (passwordET.text.toString() != (mySharedPreferences.getString(
                    phoneNumberET.text.toString(),
                    ""
                ).toString())
                || (mySharedPreferences.getString(phoneNumberET.text.toString(), "")
                    .toString()).isEmpty()
            ) {
                Toast.makeText(
                    this,
                    "The password or phone number is not correct",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
            else {
                var i :  Intent = Intent(this@ActivityLogin, ActivityHome::class.java)
                i.putExtra("currentUser",phoneNumberET.text.toString())
                startActivity(i)
                finish()
            }
        }


        //move to next page - create account
        createAccountButton = findViewById<Button>(R.id.create_account_Button)
        createAccountButton.setOnClickListener {
            startActivity(Intent(this@ActivityLogin, ActivitySignin::class.java))
        }


    }
}