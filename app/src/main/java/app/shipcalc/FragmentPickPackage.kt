package app.shipcalc

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FragmentPickPackage : Fragment(){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        var packagesList = ArrayList<Package>()
        packagesList.add(app.shipcalc.Package(PackageTypesEnum.LARG_PACKAGE,true,
            12.1,Coordinate(11.1,12.22),"ssss","qqq",PackageStatusEnum.DONE
        ))



        var rootview = inflater.inflate(R.layout.fragment_pick_package, container,false)
        val recycleView : RecyclerView? = rootview?.findViewById(R.id.recyclerViewPickPackage)
        var layoutManager : RecyclerView.LayoutManager = LinearLayoutManager(context)
        if (recycleView != null) {
            recycleView.layoutManager = layoutManager
        }
        else{
            Toast.makeText(context,packagesList[0].name,Toast.LENGTH_SHORT).show()
        }
        var packagesAdapter = PackagesAdapter(packagesList)
        if (recycleView != null) {
            recycleView.adapter = packagesAdapter
        }










        return inflater.inflate(R.layout.fragment_pick_package,container,false)
    }
}