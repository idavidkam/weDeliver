package app.shipcalc

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.widget.Toolbar
import com.google.android.material.internal.ToolbarUtils

class ActivityHome : AppCompatActivity() {
    lateinit var addPackageButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        var toolbar: Toolbar = findViewById(R.id.toolBar)
        setSupportActionBar(toolbar)
        /*addPackageButton = findViewById(R.id.home_button_addPackage)
        addPackageButton.setOnClickListener {
            startActivity(Intent(this@ActivityHome, ActivityAddPackage::class.java))

        }*/
    }
}