package app.shipcalc

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*
import com.google.firebase.database.FirebaseDatabase

// List for dropdown menu of package types
val TypesList = listOf("Envelop", "Small Package", "Large Package")

// Variables for controls
lateinit var deliveryType: AutoCompleteTextView
lateinit var sendButten: Button
lateinit var packageType: EditText
lateinit var isFragileCB: CheckBox
lateinit var pkgWeight: EditText
lateinit var lang: EditText
lateinit var lat: EditText
lateinit var pkgName: EditText
lateinit var pkgAddress: EditText

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
         packageType = findViewById<EditText>(R.id.addPkgTextPackageTypes)
         isFragileCB = findViewById<CheckBox>(R.id.addPkgCBisFragile)
         pkgWeight = findViewById<EditText>(R.id.addPkgTextWeight)
         lang = findViewById<EditText>(R.id.addPkgTextLongitude)
         lat = findViewById<EditText>(R.id.addPkgTextLatitude)
         pkgName = findViewById<EditText>(R.id.addPkgTextAddresseeName)
         pkgAddress = findViewById<EditText>(R.id.addPkgTextAddresseeAddress)


        sendButten.setOnClickListener {
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