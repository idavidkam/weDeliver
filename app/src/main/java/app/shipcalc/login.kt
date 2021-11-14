package app.shipcalc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button

class login : AppCompatActivity() {
    lateinit var createAccountButton: Button
    lateinit var loginButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()

        //move to next page - homepage
        loginButton = findViewById(R.id.login_button)
        loginButton.setOnClickListener{
            val intent = Intent(this@login, HomeActivity::class.java)
            startActivity(intent)
        }


        //move to next page - create account
        createAccountButton = findViewById<Button>(R.id.create_account_Button)
        createAccountButton.setOnClickListener{
            val intent = Intent(this@login, signin::class.java)
            startActivity(intent)
        }


    }
}