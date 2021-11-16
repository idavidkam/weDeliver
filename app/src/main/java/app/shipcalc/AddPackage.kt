package app.shipcalc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.EditText
import android.widget.TextView
import androidx.core.content.ContentProviderCompat.requireContext

val items = listOf("Envelop", "Small Package", "Large Package")
lateinit  var deliveryTypes: EditText

class AddPackage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_package)

        deliveryTypes = findViewById(R.id.addPkgTextPackageType)
        val adapter = ArrayAdapter(this, R.layout.delivery_types_list, items)
        (deliveryTypes as? AutoCompleteTextView)?.setAdapter(adapter)

    }
}