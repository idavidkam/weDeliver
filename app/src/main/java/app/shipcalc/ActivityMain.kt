package app.shipcalc

import android.app.AlertDialog
import android.content.Intent
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import java.lang.Exception

class ActivityMain : AppCompatActivity() {

    lateinit var mySharedPreferences: SharedPreferences
    lateinit var repository: Repository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        repository = Repository()
        mySharedPreferences = getSharedPreferences("registeredUsers", MODE_PRIVATE)


        Handler().postDelayed({
            var phoneNumber: String =
                mySharedPreferences.getString("lastPhoneNumber", "").toString()
            if (phoneNumber == "") {
                startActivity(Intent(this@ActivityMain, ActivityLogin::class.java))
                finish()
            } else {
                try {
                    var user: User = repository.getUser(phoneNumber)
                    startActivity(Intent(this@ActivityMain, ActivityHome::class.java))
                }catch (e: Exception)
                {
                    val alertDialogBuilder = AlertDialog.Builder(this)
                    alertDialogBuilder.setTitle("ERROR")
                        .setMessage("the user already existed\n\n$e")
                        .setIcon(R.drawable.ic_baseline_error_24)
                    val alertDialog = alertDialogBuilder.create()
                    alertDialog.show()
                }
            }
        }, 1000)

        // test
    }
}