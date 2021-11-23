package app.shipcalc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class ActivityLogin : AppCompatActivity() {
    lateinit var createAccountButton: Button
    lateinit var loginButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()

        //move to next page - homepage
        loginButton = findViewById(R.id.login_button)
        loginButton.setOnClickListener{
            startActivity(Intent(this@ActivityLogin, ActivityHome::class.java))
        }


        //move to next page - create account
        createAccountButton = findViewById<Button>(R.id.create_account_Button)
        createAccountButton.setOnClickListener{
            startActivity(Intent(this@ActivityLogin, ActivitySignin::class.java))
        }


    }
}