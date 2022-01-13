package app.shipcalc

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.firebase.database.ChildEventListener
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ktx.database
import com.google.firebase.database.ktx.getValue
import com.google.firebase.ktx.Firebase

class WaitPackageViewModel : ViewModel() {
    var listPackages = MutableLiveData<MutableList<Package>>().apply {
        value = mutableListOf()
        loadPackages()
    }

    fun getPackages(): LiveData<MutableList<Package>> {
        return listPackages
    }

    private fun loadPackages() {
        var dataBase = FirebaseDatabase.getInstance()
        val myRef = dataBase.getReference("packages")

        val tempListPackages = mutableListOf<Package>()

        val childEventListener = object : ChildEventListener {
            override fun onChildAdded(dataSnapshot: DataSnapshot, previousChildName: String?) {
                val myPackageValue = dataSnapshot.getValue<Package>()

                if (myPackageValue != null) {
                    if (myPackageValue.status == PackageStatusEnum.WAITING)
                        tempListPackages.add(myPackageValue)
                }
                listPackages.postValue(tempListPackages)
            }

            override fun onChildChanged(dataSnapshot: DataSnapshot, previousChildName: String?) {
                val myPackageValue = dataSnapshot.getValue<Package>()
                val myPackageKey = dataSnapshot.key

                if (myPackageValue != null && myPackageKey != null) {
                    val item = tempListPackages.find { pkg -> pkg.id == myPackageKey }
                    val index = tempListPackages.indexOf(item)
                    if (myPackageValue.status != null)
                        if (myPackageValue.status != PackageStatusEnum.WAITING) {
                            tempListPackages.remove(item)
                        } else {
                            tempListPackages[index] = myPackageValue
                        }
                }
                listPackages.postValue(tempListPackages)
            }

            override fun onChildRemoved(dataSnapshot: DataSnapshot) {
                val myPackageValue = dataSnapshot.getValue<Package>()
                val myPackageKey = dataSnapshot.key

                if (myPackageValue != null && myPackageKey != null) {
                    val item = tempListPackages.find { pkg -> pkg.id == myPackageKey }
                    tempListPackages.remove(item)
                }
                listPackages.postValue(tempListPackages)
            }

            override fun onChildMoved(p0: DataSnapshot, p1: String?) {

            }

            override fun onCancelled(p0: DatabaseError) {

            }

        }
        myRef.addChildEventListener(childEventListener)
    }
}