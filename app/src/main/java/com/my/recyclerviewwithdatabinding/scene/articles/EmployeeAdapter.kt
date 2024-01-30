package com.my.recyclerviewwithdatabinding.scene.articles

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Filter
import android.widget.Filterable
import androidx.databinding.DataBindingUtil
import androidx.databinding.library.baseAdapters.BR
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.my.recyclerviewwithdatabinding.R
import com.my.recyclerviewwithdatabinding.data.article.Employee
import com.my.recyclerviewwithdatabinding.databinding.EmployeeItemBinding

class EmployeeAdapter : RecyclerView.Adapter<EmployeeAdapter.ViewHolder>(), Filterable {
    private var filteredEmployeeList: List<Employee> = emptyList()

    class ViewHolder(employeeItemBinding: EmployeeItemBinding) :
        RecyclerView.ViewHolder(employeeItemBinding.root) {
        var employeeItemBinding: EmployeeItemBinding

        init {
            this.employeeItemBinding = employeeItemBinding
        }

        fun bind(obj: Any?) {
            employeeItemBinding.setVariable(BR.employee, obj)
            employeeItemBinding.executePendingBindings()
        }
    }


    private val diffCallback = object : DiffUtil.ItemCallback<Employee>() {
        override fun areItemsTheSame(oldItem: Employee, newItem: Employee): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: Employee, newItem: Employee): Boolean {
            return oldItem.hashCode() == newItem.hashCode()
        }
    }

    private val differ = AsyncListDiffer(this, diffCallback)
    fun submitList(list: List<Employee>) {
        differ.submitList(list)
        filteredEmployeeList = differ.currentList
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding: EmployeeItemBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.employee_item, parent, false
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
                    differ.currentList
                } else {
                    differ.currentList.filter {
                        (!it.title.isNullOrBlank() and it.title?.contains(charString, true)!!) or
                                (!it.brand.isNullOrBlank() and it.brand?.contains(
                                    charString,
                                    true
                                )!!) or
                                (!it.description.isNullOrBlank() and it.description?.contains(
                                    charString,
                                    true
                                )!!)
                    }
                }

                return FilterResults().apply { values = filteredEmployeeList }
            }

            override fun publishResults(constraint: CharSequence?, results: FilterResults?) {
                filteredEmployeeList = results?.values as List<Employee> ?: emptyList()
                filteredEmployeeList.forEach {
                    Log.d("check", "${it.title}")
                }
                notifyDataSetChanged()
            }
        }
    }
}