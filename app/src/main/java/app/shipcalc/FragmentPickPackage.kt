package app.shipcalc

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FragmentPickPackage : Fragment(){
    private var layoutManager : RecyclerView.LayoutManager? = null
    private var packagesAdapter: RecyclerView.Adapter<PackagesAdapter.PackageViewHolder>? = null
    private var repository: Repository = Repository()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_pick_package,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        var packagesList = mutableListOf<Package>()
        try {
            for(i in 1..10)
                packagesList.add(Package(PackageTypesEnum.SMALL_PACKAGE, true,  12.2,Coordinate(12.1,13.2),"testt","errre 12", PackageStatusEnum.DONE))
        }
        catch (e: Exception){
            Toast.makeText(context,e.toString(),Toast.LENGTH_SHORT).show()
        }

        // find recycleView
        val recycleView : RecyclerView = view.findViewById(R.id.recyclerViewPickPackage)

        // init layoutManager
        layoutManager = LinearLayoutManager(this.context)

        // init recycleView.layoutManager
        recycleView.layoutManager = layoutManager

        // post
        packagesAdapter = PackagesAdapter(packagesList as ArrayList<Package>)
        recycleView.adapter = packagesAdapter

        super.onViewCreated(view, savedInstanceState)
    }
}