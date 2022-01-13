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

class FragmentHistoryPackages : Fragment(){
    private var layoutManager : RecyclerView.LayoutManager? = null
    private var packagesAdapter: RecyclerView.Adapter<PackagesAdapter.PackageViewHolder>? = null
    private lateinit var donePackageViewModel: DonePackageViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_history_package,container,false)
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        donePackageViewModel = ViewModelProvider(this).get(DonePackageViewModel::class.java)
        donePackageViewModel.getPackages().observe(viewLifecycleOwner,{ packages ->

            // find recycleView
            val recycleView : RecyclerView = view.findViewById(R.id.recyclerViewHistoryPackage)

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
                    val view = View.inflate(context, R.layout.package_details_dialog_done, null)
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
                    var cancelButton: Button? = view.findViewById(R.id.buttonDoneCencel)

                    //set
                    addresseAddressTV.text = packages[position].address
                    addresseNameTV.text = packages[position].name
                    typeTV.text = packages[position].packageType?.let { packageTypeString(it) }
                    warehouseAddressTV.text = "${packages[position].coordinate?.latitude},${packages[position].coordinate?.longitude}"
                    weightTV.text = packages[position].weight.toString()
                    fragileTV.text = packages[position].isFragile.toString()
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