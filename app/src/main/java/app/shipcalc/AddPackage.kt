package app.shipcalc

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.FirebaseDatabase
import java.lang.Exception

/**
 * This class is about to add a new package for delivery
 */
class AddPackage : AppCompatActivity() {

    // List for dropdown menu of package types
    val TypesList = listOf("Envelop", "Small Package", "Large Package")

    // Variables for controls
    lateinit var deliveryType: AutoCompleteTextView
    lateinit var sendButten: Button

    /*lateinit var isFragileCB: CheckBox
    lateinit var packageType: EditText
    lateinit var pkgWeight: EditText
    lateinit var lang: EditText
    lateinit var lat: EditText
    lateinit var pkgName: EditText
    lateinit var pkgAddress: EditText*/

    // FireBase
    var databade = FirebaseDatabase.getInstance()
    var myRef = databade.getReference()

    // Counter
    var counter: Int = 0
    var errorCounter: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_package)

        deliveryType = findViewById<AutoCompleteTextView>(R.id.addPkgTextPackageTypes)
        val adapter: ArrayAdapter<*> =
            ArrayAdapter(applicationContext, R.layout.package_type_dropdown, TypesList)
        (deliveryType as? AutoCompleteTextView)?.setAdapter(adapter)

        //findView
        sendButten = findViewById<Button>(R.id.addPkgButtonSend)

        sendButten.setOnClickListener {
            try {

                // find view
                var isFragileCB = findViewById<CheckBox>(R.id.addPkgCBisFragile).isChecked
                var packageType = findViewById<AutoCompleteTextView>(R.id.addPkgTextPackageTypes).text.toString()
                var pkgWeight = findViewById<TextInputEditText>(R.id.addPkgTextWeight).text.toString()
                var lang = findViewById<TextInputEditText>(R.id.addPkgTextLongitude).text.toString()
                var lat = findViewById<TextInputEditText>(R.id.addPkgTextLatitude).text.toString()
                var pkgName = findViewById<TextInputEditText>(R.id.addPkgTextAddresseeName).text.toString()
                var pkgAddress = findViewById<TextInputEditText>(R.id.addPkgTextAddresseeAddress).text.toString()



                addPackage(
                    PackageDeliver(
                        convertPackageTypeToEnum(packageType),
                        isFragileCB,
                        pkgWeight.toDouble(),
                        Coordinate(lang.toDouble(), lat.toDouble()),
                        pkgName,
                        pkgAddress
                    ))
                Toast.makeText(this, "The package was sent successfully", Toast.LENGTH_SHORT).show()

                //clear view
                findViewById<TextInputEditText>(R.id.addPkgTextWeight).text?.clear()
                findViewById<TextInputEditText>(R.id.addPkgTextLongitude).text?.clear()
                findViewById<TextInputEditText>(R.id.addPkgTextLatitude).text?.clear()
                findViewById<TextInputEditText>(R.id.addPkgTextAddresseeName).text?.clear()
                findViewById<TextInputEditText>(R.id.addPkgTextAddresseeAddress).text?.clear()

            } catch (E: Exception) {
                val alertDialogBuilder = AlertDialog.Builder(this)
                alertDialogBuilder.setTitle("ERROR")
                    .setMessage("We apologize but an error occurred while trying to send the information\n\n" + E.toString())
                    .setIcon(R.drawable.ic_baseline_error_24)
                val alertDialog = alertDialogBuilder.create()
                alertDialog.show()
                myRef.child("ERRORS").child("ERROR ${++errorCounter}").setValue(E.toString())
            }
        }
    }


    fun convertPackageTypeToEnum(packageType: String): PackageTypesEnum {
        return when (packageType) {
            "Envelop" -> PackageTypesEnum.ENVELOPE
            "Small Package" -> PackageTypesEnum.SMALL_PACKAGE
            "Large Package" -> PackageTypesEnum.LARG_PACKAGE
            else -> PackageTypesEnum.ENVELOPE
        }
    }

    fun addPackage(packageDeliver: PackageDeliver) {
        var addRef = myRef.child("NewPackages").child("Package_${++counter}")
        addRef.child("addresseeName").setValue(packageDeliver.name.toString())
        addRef.child("addresseeAddress").setValue(packageDeliver.address.toString())
        addRef.child("type").setValue(packageDeliver.packageType.toString())
        addRef.child("weight").setValue(packageDeliver.weight.toString())
        addRef.child("isFragile").setValue(packageDeliver.isFragile.toString())
        addRef.child("coordinate").child("longitude")
            .setValue(packageDeliver.coor.longitude.toString())
        addRef.child("coordinate").child("latitude")
            .setValue(packageDeliver.coor.latitude.toString())
    }
}