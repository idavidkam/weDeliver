package app.shipcalc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class ActivityHome : AppCompatActivity() {
    lateinit var addPackageButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        addPackageButton = findViewById(R.id.home_button_addPackage)
        addPackageButton.setOnClickListener {
            startActivity(Intent(this@ActivityHome, ActivityAddPackage::class.java))

        }
    }
}