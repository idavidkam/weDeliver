package app.shipcalc

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.google.firebase.database.FirebaseDatabase
import java.lang.Exception

// List for dropdown menu of package types
val TypesList = listOf("Envelop", "Small Package", "Large Package")

// Variables for controls
lateinit var deliveryType: AutoCompleteTextView
lateinit var sendButten: Button
lateinit var packageType: EditText
lateinit var isFragileCB: CheckBox
lateinit var pkgWeight: EditText
//lateinit var lang: EditText
//lateinit var lat: EditText
//lateinit var pkgName: EditText
//lateinit var pkgAddress: EditText

// FireBase
var databade = FirebaseDatabase.getInstance()
var myRef = databade.getReference()

// Counter for new package
var counter: Int = 0

/**
 * This class is about to add a new package for delivery
 */
class AddPackage : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_package)

        deliveryType = findViewById<AutoCompleteTextView>(R.id.addPkgTextPackageTypes)
        val adapter: ArrayAdapter<*> =
            ArrayAdapter(applicationContext, R.layout.package_type_dropdown, TypesList)
        (deliveryType as? AutoCompleteTextView)?.setAdapter(adapter)

        //findView
         sendButten = findViewById<Button>(R.id.addPkgButtonSend)
         var packageType = findViewById<EditText>(R.id.addPkgTextPackageType)
         isFragileCB = findViewById<CheckBox>(R.id.addPkgCBisFragile)
         var pkgWeight = findViewById<EditText>(R.id.addPkgTextWeight)
         var lang = findViewById<EditText>(R.id.addPkgTextLongitude)
         var lat = findViewById<EditText>(R.id.addPkgTextLatitude)
         var pkgName = findViewById<EditText>(R.id.addPkgTextAddresseeName)
         var pkgAddress = findViewById<EditText>(R.id.addPkgTextAddresseeAddress)


        sendButten.setOnClickListener {
            try {
                addPackage(
                    PackageDeliver(
                        ConvertPackageTypeToEnum(packageType),
                        isFragileCB.isChecked,
                        pkgWeight.text.toString().toDouble(),
                        Coordinate(lang.text.toString().toDouble(), lat.text.toString().toDouble()),
                        pkgName.text.toString(),
                        pkgAddress.text.toString()
                    )
                )
            }
            catch (E: Exception){
                val alertDialogBuilder = AlertDialog.Builder(this)
                alertDialogBuilder.setTitle("ERROR")
                    .setMessage("We apologize but an error occurred while trying to send the information")
                    .setIcon(R.drawable.ic_baseline_error_24)
                val alertDialog = alertDialogBuilder.create()
                alertDialog.show()
            }
        }
    }


    fun ConvertPackageTypeToEnum(packageType: EditText): PackageTypesEnum {
        return when (packageType.text.toString()) {
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