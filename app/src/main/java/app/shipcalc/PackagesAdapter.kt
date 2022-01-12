package app.shipcalc

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

public class PackagesAdapter(packagesArrayList : ArrayList<Package>)
    : RecyclerView.Adapter<PackagesAdapter.PackageViewHolder>() {
    var packagesList : ArrayList<Package> = packagesArrayList
    lateinit var mListener: onItemClickListener

    public interface onItemClickListener{
        fun onItemClick(position:Int)
    }

    inner class PackageViewHolder(itemView : View, listener: onItemClickListener) : RecyclerView.ViewHolder(itemView) {
        lateinit var addresseeNameTV : TextView
        lateinit var packageTypeTV : TextView
        init {
            addresseeNameTV = itemView.findViewById(R.id.textView_addressee_name)
            packageTypeTV = itemView.findViewById(R.id.textView_package_type)
            itemView.setOnClickListener {
                listener.onItemClick(adapterPosition)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PackageViewHolder {
        var packagesView = LayoutInflater.from(parent.context).
        inflate(R.layout.recycle_package_item,parent,false)
        return PackageViewHolder(packagesView,mListener)
    }

    override fun onBindViewHolder(holder: PackageViewHolder, position: Int) {
        var currentPackage = packagesList[position]
        holder.addresseeNameTV.text = currentPackage.name
        holder.packageTypeTV.text = currentPackage.packageType?.let { packageTypeString(it) }
    }

    override fun getItemCount(): Int {
        return packagesList.size
    }

    public fun setOnItemClickListener(listener: onItemClickListener){
        mListener = listener
    }

    fun packageTypeString(type : PackageTypesEnum) : String{
        return when(type){
            PackageTypesEnum.ENVELOPE -> "Envelop"
            PackageTypesEnum.SMALL_PACKAGE -> "Small package"
            PackageTypesEnum.LARG_PACKAGE -> "Large package"
        }
    }


}

