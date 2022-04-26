package com.fedx.listcountries

import android.annotation.SuppressLint
import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.fedx.listcountries.databinding.ItemCountryBinding
import com.fedx.listcountries.network.Countries
import kotlinx.coroutines.withContext
import java.util.*
import kotlin.collections.ArrayList

class AdapterCountries(private val context: Context, val listener: Listener)
    : RecyclerView.Adapter<
        AdapterCountries.CountriesViewHolder>(), Filterable {

    var dataFilter = ArrayList<Countries>()

    var data = ArrayList<Countries>()
        set(value) {
            field = value
            dataFilter = value
            notifyDataSetChanged()
        }

    inner class CountriesViewHolder(private var viewBinding: ItemCountryBinding) :
        RecyclerView.ViewHolder(viewBinding.root) {
        @SuppressLint("CheckResult")
        fun bind(countries: Countries, listener: Listener) {
            viewBinding.tvName.text = countries.name?.common

            Glide.with(context)
                .load(countries.flags["png"])
                .into(viewBinding.ivFlag)

            itemView.setOnClickListener {
                listener.detail(countries)
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdapterCountries.CountriesViewHolder {
        return CountriesViewHolder(ItemCountryBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: AdapterCountries.CountriesViewHolder, position: Int) {
        val src = dataFilter[position]
        holder.bind(src, listener)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                var filterResults = FilterResults()
                if (constraint == null || constraint.isEmpty()) {
                    Log.d("MyLog", "constraint == null ${constraint}")
                    filterResults.count = data.size
                    filterResults.values = dataFilter
                } else {
                    Log.d("MyLog", "constraint != null ${constraint}")
                    var searchChr: String = constraint.toString()
                    var itemModal = ArrayList<Countries>()
                    for (items in data) {
                        //Log.d("MyLog", "listFilter ${items.name.common}")
                        if (items.name!!.common!!.lowercase().contains(searchChr.lowercase())) {
                            itemModal.add(items)
                            Log.d("MyLog", "list tru ${itemModal}")
                        } else {
                            Log.d("MyLog", "list false  ${itemModal}")
                        }
                    }
                    filterResults.count = itemModal.size
                    filterResults.values = itemModal
                }
                return filterResults
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                dataFilter = results!!.values as ArrayList<Countries>
                notifyDataSetChanged()
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun sorted(){
        data.sortWith(compareBy({ it.name?.common }))
        notifyDataSetChanged()
    }
    override fun getItemCount(): Int {
        return dataFilter.size
    }

    interface Listener{
        fun detail(countries: Countries)
    }

}
