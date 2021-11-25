package app.shipcalc

import android.app.AlertDialog
import android.app.Dialog
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import com.google.android.material.textfield.TextInputEditText
import com.google.firebase.database.FirebaseDatabase
import java.lang.Exception

/**
 * This class is about to add a new package for delivery
 */
class ActivityAddPackage : AppCompatActivity() {

    // List for dropdown menu of package types
    val TypesList = listOf("Envelop", "Small Package", "Large Package")

    // Variables for controls
    lateinit var deliveryType: AutoCompleteTextView
    lateinit var sendButten: Button

    /*lateinit var isFragileCB: CheckBox
    lateinit var packageType: EditText
    lateinit var pkgWeightTET: TextInputEditText
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

    fun readCounter() {
        val refErr =FirebaseDatabase.getInstance().getReference("ErrorCounter")
        refErr.get().addOnSuccessListener {
            errorCounter=(it.value.toString().toInt())
        }
        val refCounter =FirebaseDatabase.getInstance().getReference("NewPackageCounter")
        refCounter.get().addOnSuccessListener {
            counter=(it.value.toString().toInt())
        }
    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_package)
        supportActionBar?.hide()


        //findView
        deliveryType = findViewById<AutoCompleteTextView>(R.id.addPkgTextPackageTypes)
        val adapter: ArrayAdapter<*> =
            ArrayAdapter(applicationContext, R.layout.package_type_dropdown, TypesList)
        (deliveryType as? AutoCompleteTextView)?.setAdapter(adapter)

        // cancel the error if package changed
        deliveryType.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(s: CharSequence, start: Int,
                                           count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence, start: Int,
                                       before: Int, count: Int) {
                deliveryType.error = null
            }
        })

        sendButten = findViewById<Button>(R.id.addPkgButtonSend)

        sendButten.setOnClickListener {
            try {

                //setCounter
                readCounter()

                var flagIsEmpty : Boolean = false
                // find view
                var pkgName = findViewById<TextInputEditText>(R.id.addPkgTextAddresseeName).text.toString()
                var pkgAddress = findViewById<TextInputEditText>(R.id.addPkgTextAddresseeAddress).text.toString()
                var pkgType = deliveryType.text.toString()
                var pkgWeight = findViewById<TextInputEditText>(R.id.addPkgTextWeight).text.toString()
                var isFragileCB = findViewById<CheckBox>(R.id.addPkgCBisFragile).isChecked
                var lang = findViewById<TextInputEditText>(R.id.addPkgTextLongitude).text.toString()
                var lat = findViewById<TextInputEditText>(R.id.addPkgTextLatitude).text.toString()

                // Validate there is not empty fields
                if(pkgName == ""){
                    findViewById<TextInputEditText>(R.id.addPkgTextAddresseeName).
                    error = getString(R.string.enterValue)
                    flagIsEmpty = true
                }
                if(pkgAddress == ""){
                    findViewById<TextInputEditText>(R.id.addPkgTextAddresseeAddress).
                    error = getString(R.string.enterValue)
                    flagIsEmpty = true
                }
                if(pkgType == ""){
                    deliveryType.error = getString(R.string.selectType)
                    flagIsEmpty = true
                }
                if(pkgWeight == ""){
                    findViewById<TextInputEditText>(R.id.addPkgTextWeight).
                    error = getString(R.string.enterValue)
                    flagIsEmpty = true
                }
                if(lang == ""){
                    findViewById<TextInputEditText>(R.id.addPkgTextLongitude).
                    error = getString(R.string.enterValue)
                    flagIsEmpty = true
                }
                if(lat == ""){
                    findViewById<TextInputEditText>(R.id.addPkgTextLatitude).
                    error = getString(R.string.enterValue)
                    flagIsEmpty = true
                }
                if(flagIsEmpty){
                    return@setOnClickListener
                }
                // Validate correction weight
                var calcWeight:String =  calculateWeight(pkgWeight,pkgType)
                if (calcWeight != "") {
                    Toast.makeText(this, calcWeight, Toast.LENGTH_SHORT).show()
                    deliveryType.error = getString(R.string.ChangePackage)
                    return@setOnClickListener
                }

                addPackage(
                    PackageDeliver(
                        convertPackageTypeToEnum(pkgType),
                        isFragileCB,
                        pkgWeight.toDouble(),
                        Coordinate(lang.toDouble(), lat.toDouble()),
                        pkgName,
                        pkgAddress
                    ))

                val positiveButtonClick = {dialog: DialogInterface,which:Int ->
                    finish()
                }

                val alertSuccessBuilder = AlertDialog.Builder(this)
                alertSuccessBuilder.setTitle("Package added")
                    .setMessage("The package was added successfully!")
                    .setIcon(R.drawable.ic_baseline_done_25)
                    .setPositiveButton("OK",DialogInterface.OnClickListener(positiveButtonClick))
                    .setCancelable(false)
                val alertDialog = alertSuccessBuilder.create()
                alertDialog.show()

            }
            catch (E: Exception) {
                val alertDialogBuilder = AlertDialog.Builder(this)
                alertDialogBuilder.setTitle("ERROR")
                    .setMessage("We apologize but an error occurred while trying to send the information\n\n" + E.toString())
                    .setIcon(R.drawable.ic_baseline_error_24)
                val alertDialog = alertDialogBuilder.create()
                alertDialog.show()
                myRef.child("ERRORS").child("ERROR ${++errorCounter}").setValue(E.toString())
                myRef.child("ErrorCounter").setValue(errorCounter)
            }

        }
    }

    fun calculateWeight(pkgWeight: String, pkgType: String):String {
        var weight: Double = pkgWeight.toDouble()
        var type: PackageTypesEnum = convertPackageTypeToEnum(pkgType)

        if (type == PackageTypesEnum.ENVELOPE && weight > 0.5)
            return "An envelope can weigh no more than 500 grams"
        else if(type == PackageTypesEnum.SMALL_PACKAGE && weight > 3.0)
            return "An small package can weigh no more than 3 kg"
        else if(weight <= 0.0)
            return "The package can not weigh less than zero"
        return ""
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
        myRef.child("NewPackageCounter").setValue(counter)

    }
}