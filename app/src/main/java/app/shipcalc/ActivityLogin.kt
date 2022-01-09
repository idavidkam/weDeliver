package app.shipcalc

import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth

class ActivityLogin : AppCompatActivity() {
    lateinit var auth : FirebaseAuth
    lateinit var createAccountButton: Button
    lateinit var loginButton: Button
    lateinit var mySharedPreferences: SharedPreferences
    lateinit var emailET: TextInputEditText
    lateinit var passwordET: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()
        emailET = findViewById(R.id.login_email)
        passwordET = findViewById(R.id.login_password)

        var mySharedPreferences = getSharedPreferences("registeredUsers", MODE_PRIVATE)
        var email: String = mySharedPreferences.getString("LastUser", "").toString()
        if (email != "") {
            emailET.setText(email)
            passwordET.setText(mySharedPreferences.getString(email, ""))
        }

        //move to next page - homepage
        loginButton = findViewById(R.id.login_button)

        loginButton.setOnClickListener {
            if (passwordET.text.toString().isEmpty() || emailET.text.toString().isEmpty()) {
                Toast.makeText(
                    this,
                    "The password or email fields can't be empty",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
            else if (passwordET.text.toString() != (mySharedPreferences.getString(
                    emailET.text.toString(),
                    ""
                ).toString())
                || (mySharedPreferences.getString(emailET.text.toString(), "")
                    .toString()).isEmpty()
            ) {
                Toast.makeText(
                    this,
                    "The password or email fields is not correct",
                    Toast.LENGTH_SHORT
                )
                    .show()
            }
            else {
                var i :  Intent = Intent(this@ActivityLogin, ActivityHome::class.java)
                i.putExtra("currentUser",emailET.text.toString())
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