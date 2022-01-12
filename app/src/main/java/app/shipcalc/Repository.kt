package app.shipcalc

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.*
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

    fun getPickPackages(liveData: ArrayList<Package>) {

        myRefPackages.addChildEventListener(object :ChildEventListener{
            override fun onChildAdded(snapshot: DataSnapshot, previousChildName: String?) {
                for(packageSnapshot in snapshot.children) {
                    val myPackageValue = packageSnapshot.getValue<Package>()
                    val myPackageKey = packageSnapshot.key

                    if (myPackageValue != null && myPackageKey != null) {
                        if (myPackageValue.status == PackageStatusEnum.READY)
                            liveData.add(myPackageValue)
                    }
                }
            }

            override fun onChildChanged(snapshot: DataSnapshot, previousChildName: String?) {
                for(packageSnapshot in snapshot.children) {
                    val myPackageValue = packageSnapshot.getValue<Package>()
                    val myPackageKey = packageSnapshot.key

                    if (myPackageValue != null && myPackageKey != null) {
                        if (myPackageValue.status == PackageStatusEnum.READY)
                            liveData.add(myPackageValue)
                    }
                }
            }

            override fun onChildRemoved(snapshot: DataSnapshot) {
                for(packageSnapshot in snapshot.children) {
                    val myPackageValue = packageSnapshot.getValue<Package>()
                    val myPackageKey = packageSnapshot.key

                    if (myPackageValue != null && myPackageKey != null) {
                        if (myPackageValue.status == PackageStatusEnum.READY)
                            liveData.add(myPackageValue)
                    }
                }
            }

            override fun onChildMoved(snapshot: DataSnapshot, previousChildName: String?) {
                for(packageSnapshot in snapshot.children) {
                    val myPackageValue = packageSnapshot.getValue<Package>()
                    val myPackageKey = packageSnapshot.key

                    if (myPackageValue != null && myPackageKey != null) {
                        if (myPackageValue.status == PackageStatusEnum.READY)
                            liveData.add(myPackageValue)
                    }
                }
            }

            override fun onCancelled(error: DatabaseError) {
                //TODO: Remove
            }

        })
    }
}


