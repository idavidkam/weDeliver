package app.shipcalc

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase


class Repository {

    private var mAuto: FirebaseAuth = Firebase.auth
    private var dataBase = FirebaseDatabase.getInstance()
    private val myRefPackages = dataBase.getReference("packages")
    private val myRefUsers = dataBase.getReference("usersDetails")
    var packagesList = mutableListOf<Package>()

    fun addUser(user: User) {
        user.id = myRefUsers.push().key.toString()
        myRefUsers.child(user.id).setValue(user)
    }

    fun updatePackage(id: String?, newStatus: PackageStatusEnum, guy: String) {

        val mapStatus = HashMap<String, Any>()
        mapStatus["status"] = newStatus

        val mapGuy = HashMap<String, Any>()
        mapGuy["deliveryGuy"] = guy

        myRefPackages.child(id.toString()).updateChildren(mapStatus)
        myRefPackages.child(id.toString()).updateChildren(mapGuy)
    }
}


