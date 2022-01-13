package app.shipcalc

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class FragmentWaitingPackages : Fragment(){
    private var repository: Repository  = Repository()
    private var layoutManager : RecyclerView.LayoutManager? = null
    private var packagesAdapter: RecyclerView.Adapter<PackagesAdapter.PackageViewHolder>? = null
    private lateinit var waitPackageViewModel: WaitPackageViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_waiting_packages,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        waitPackageViewModel = ViewModelProvider(this).get(WaitPackageViewModel::class.java)
        waitPackageViewModel.getPackages().observe(viewLifecycleOwner,{ packages ->

            // find recycleView
            val recycleView : RecyclerView = view.findViewById(R.id.recyclerViewWaitingPackage)

            // init layoutManager
            layoutManager = LinearLayoutManager(this.context)

            // init recycleView.layoutManager
            recycleView.layoutManager = layoutManager

            // post
            packagesAdapter = PackagesAdapter(packages as ArrayList<Package>)
            recycleView.adapter = packagesAdapter

            // for Double click
            (packagesAdapter as PackagesAdapter).setOnItemClickListener(object : PackagesAdapter.onItemClickListener {
                override fun onItemClick(position: Int) {
                    // init
                    val view = View.inflate(context, R.layout.package_details_dialog_wait, null)
                    val builder = AlertDialog.Builder(context)
                    builder.setView(view)

                    // create
                    val dialog = builder.create()
                    dialog.show()
                    dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)


                    var addresseAddressTV: TextView = view.findViewById(R.id.DaddrsseAddress)
                    var addresseNameTV: TextView = view.findViewById(R.id.DaddresseeName)
                    var typeTV: TextView = view.findViewById(R.id.Dtype)
                    var warehouseAddressTV: TextView = view.findViewById(R.id.DwarehouseAddress)
                    var weightTV: TextView = view.findViewById(R.id.Dweight)
                    var fragileTV: TextView = view.findViewById(R.id.Dfragile)
                    var waitBtn: Button? = view.findViewById(R.id.buttonWait)
                    var cancelButton: Button? = view.findViewById(R.id.buttonWaitCencel)

                    //set
                    addresseAddressTV.text = packages[position].address
                    addresseNameTV.text = packages[position].name
                    typeTV.text = packages[position].packageType?.let { packageTypeString(it) }
                    warehouseAddressTV.text = "${packages[position].coordinate?.latitude},${packages[position].coordinate?.longitude}"
                    weightTV.text = packages[position].weight.toString()
                    fragileTV.text = packages[position].isFragile.toString()
                    waitBtn?.setOnClickListener {
                        repository.updatePackage(packages[position].id,PackageStatusEnum.DONE,"test2")
                        Toast.makeText(context,"Congratulations, you delivered the package",Toast.LENGTH_SHORT).show()
                        dialog.dismiss()
                    }
                    cancelButton?.setOnClickListener{
                        dialog.dismiss()
                    }
                }

            })
        })
        super.onViewCreated(view, savedInstanceState)
    }
    fun packageTypeString(type : PackageTypesEnum) : String{
        return when(type){
            PackageTypesEnum.ENVELOPE -> "Envelop"
            PackageTypesEnum.SMALL_PACKAGE -> "Small package"
            PackageTypesEnum.LARG_PACKAGE -> "Large package"
        }
    }
}