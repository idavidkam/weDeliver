package app.shipcalc

import android.app.AlertDialog
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import java.lang.Exception

class ActivityLogin : AppCompatActivity() {
    private lateinit var createAccountButton: Button
    private lateinit var loginButton: Button
    private lateinit var mySharedPreferences: SharedPreferences
    private lateinit var emailET: TextInputEditText
    private lateinit var passwordET: TextInputEditText
    var mAuto: FirebaseAuth = Firebase.auth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()
        emailET = findViewById(R.id.login_email)
        passwordET = findViewById(R.id.login_password)

        mySharedPreferences = getSharedPreferences("registeredUsers", MODE_PRIVATE)
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
            } else {
                try {

                    // animationMode
                    val inflater = layoutInflater.inflate(R.layout.login_signin_wait, null)
                    val processing = AlertDialog.Builder(this).setView(inflater)
                    processing.show()

                    // signIn the user to the firebase.authentication
                    mAuto.signInWithEmailAndPassword(
                        emailET.text.toString(),
                        passwordET.text.toString()
                    )
                        .addOnCompleteListener(this) {
                            // validate the user is sign in successfully to the firebase.authentication
                            if (it.isSuccessful) {

                                // add the new user to share preference
                                var editor: SharedPreferences.Editor = mySharedPreferences.edit()
                                editor.putString("lastUser",  emailET.text.toString())
                                editor.putString( emailET.text.toString(),  passwordET.text.toString())
                                editor.apply()

                                // finish the activity and start the ActivityHome
                                val i = Intent(this@ActivityLogin, ActivityHome::class.java)
                                startActivity(i)
                                finish()
                            }
                        } // inform the user when the log in failed
                        .addOnFailureListener {
                            Toast.makeText(
                                this,
                                R.string.login_failed,
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                } catch (e: Exception) {
                    val alertDialogBuilder = AlertDialog.Builder(this)
                    alertDialogBuilder.setTitle("ERROR")
                        .setMessage("An error occurred in the registration\n\n$e")
                        .setIcon(R.drawable.ic_baseline_error_24)
                    val alertDialog = alertDialogBuilder.create()
                    alertDialog.show()
                }

            }
        }


        //move to next page - create account
        createAccountButton = findViewById(R.id.create_account_Button)
        createAccountButton.setOnClickListener {
            startActivity(Intent(this@ActivityLogin, ActivitySignin::class.java))
        }


    }
}