package com.iebayirli.recyclerviewwithdatabinding.scene.rates

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.RecyclerView
import com.iebayirli.recyclerviewwithdatabinding.R
import com.iebayirli.recyclerviewwithdatabinding.database.Currency
import com.iebayirli.recyclerviewwithdatabinding.databinding.CurrencyItemBinding

class RatesAdapter : RecyclerView.Adapter<RatesAdapter.ViewHolder>(), Filterable {
    private var oriEmployeeList: MutableList<Currency> = mutableListOf()
    private var filteredEmployeeList: List<Currency> = emptyList()

    class ViewHolder(currencyItemBinding: CurrencyItemBinding) :
        RecyclerView.ViewHolder(currencyItemBinding.root) {
        var employeeItemBinding: CurrencyItemBinding

        init {
            this.employeeItemBinding = currencyItemBinding
        }

        fun bind(obj: Any?) {
            employeeItemBinding.setVariable(BR.currency, obj)
            employeeItemBinding.executePendingBindings()
        }
    }


//    private val diffCallback = object : DiffUtil.ItemCallback<Currency>() {
//        override fun areItemsTheSame(oldItem: Currency, newItem: Currency): Boolean {
//            return oldItem.currencySymbol == newItem.currencySymbol
//        }
//
//        override fun areContentsTheSame(oldItem: Currency, newItem: Currency): Boolean {
//            return oldItem.hashCode() == newItem.hashCode()
//        }
//    }
//    private val differ = AsyncListDiffer(this, diffCallback)

    fun updateList(exchangeAmount: Double) {
        for (currency in oriEmployeeList) {
            val existingCurrency =
                oriEmployeeList.find { it.currencySymbol == currency.currencySymbol }
            if (existingCurrency != null) {
                existingCurrency.exchangeAmount = exchangeAmount
            }
        }
        notifyDataSetChanged()
    }

    fun submitList(list: List<Currency>, isRefresh: Boolean? = false) {
        if (oriEmployeeList.isEmpty() || isRefresh == true) {
            oriEmployeeList = list.toMutableList()
            filteredEmployeeList = oriEmployeeList
            notifyDataSetChanged()
        } else {
            for (newUser in list) {
                val existingUser =
                    oriEmployeeList.find { it.currencySymbol == newUser.currencySymbol }
                if (existingUser == null) {
                    oriEmployeeList.add(newUser)
                    //                    oriEmployeeList.sortBy { it.currencySymbol }
                    notifyItemInserted(oriEmployeeList.size - 1)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: CurrencyItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.currency_item, parent, false
        )

        return ViewHolder(binding)
    }

    override fun getItemCount(): Int {
        return filteredEmployeeList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val dataModel = filteredEmployeeList[position]
        holder.bind(dataModel)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val charString = constraint?.toString() ?: ""
                Log.d("check", charString);
                filteredEmployeeList = if (charString.isEmpty()) {
                    oriEmployeeList
                } else {
                    oriEmployeeList.filter {
                        (it.currencySymbol.isNotBlank() and (it.currencySymbol.contains(
                            charString,
                            true
                        )))
                    }
                }

                return FilterResults().apply { values = filteredEmployeeList }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredEmployeeList = results?.values as List<Currency>
                filteredEmployeeList.forEach {
                    Log.d("check", it.currencySymbol)
                }
                notifyDataSetChanged()
            }
        }
    }
}