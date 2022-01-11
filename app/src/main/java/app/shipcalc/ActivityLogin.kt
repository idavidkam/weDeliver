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
    private val repository: Repository = Repository()
    var mAuto: FirebaseAuth = Firebase.auth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()
        emailET = findViewById(R.id.login_email)
        passwordET = findViewById(R.id.login_password)

        mySharedPreferences = getSharedPreferences("registeredUsers", MODE_PRIVATE)
        val email: String = mySharedPreferences.getString("LastUser", "").toString()
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
            } else if (passwordET.text.toString() != (mySharedPreferences.getString(
                    emailET.text.toString(),
                    ""
                ).toString())
                || (mySharedPreferences.getString(emailET.text.toString(), "")
                    .toString()).isEmpty()
            ) {
                Toast.makeText(
                    this,
                    R.string.login_failed,
                    Toast.LENGTH_SHORT
                )
                    .show()
            } else {
                try {
                    // signIn the user to the firebase.authentication
                    mAuto.signInWithEmailAndPassword(
                        emailET.text.toString(),
                        passwordET.text.toString()
                    )
                        .addOnCompleteListener(this) {
                            // validate the user is sign in successfully to the firebase.authentication
                            if (it.isSuccessful) {
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