package app.shipcalc

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

public class PackagesAdapter(packagesArrayList : ArrayList<Package>)
    : RecyclerView.Adapter<PackagesAdapter.PackageViewHolder>() {
    var packagesList : ArrayList<Package> = packagesArrayList


    inner class PackageViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        lateinit var addresseeNameTV : TextView
        lateinit var packageTypeTV : TextView
        init {
            addresseeNameTV = itemView.findViewById(R.id.textView_addressee_name)
            packageTypeTV = itemView.findViewById(R.id.textView_package_type)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PackageViewHolder {
        var packagesView = LayoutInflater.from(parent.context).
        inflate(R.layout.recycle_package_item,parent,false)
        return PackageViewHolder(packagesView)
    }

    override fun onBindViewHolder(holder: PackageViewHolder, position: Int) {
        var currentPackage = packagesList[position]
        holder.addresseeNameTV.text = currentPackage.name
        holder.packageTypeTV.text = packageTypeString(currentPackage.packageType)
    }

    override fun getItemCount(): Int {
        return packagesList.size
    }

    fun packageTypeString(type : PackageTypesEnum) : String{
        return when(type){
            PackageTypesEnum.ENVELOPE -> "Envelop"
            PackageTypesEnum.SMALL_PACKAGE -> "Small package"
            PackageTypesEnum.LARG_PACKAGE -> "Large package"
        }
    }
}

