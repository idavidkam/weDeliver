package app.shipcalc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle

class ActivitySignin : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signin)
        supportActionBar?.hide()
    }
}