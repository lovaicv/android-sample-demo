package com.my.recyclerviewwithdatabinding.scene.rates

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.my.recyclerviewwithdatabinding.R
import com.my.recyclerviewwithdatabinding.databinding.ActivityRatesBinding
import com.my.recyclerviewwithdatabinding.other.Status
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class RatesActivity : AppCompatActivity(), SearchView.OnQueryTextListener {

    private lateinit var binding: ActivityRatesBinding
    private lateinit var adapter: RatesAdapter
    private val viewModel by viewModels<RatesViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_rates)
        binding.apply {
            viewModel = this@RatesActivity.viewModel
            lifecycleOwner = this@RatesActivity
        }
        setContentView(binding.root)

//        supportActionBar?.setDisplayHomeAsUpEnabled(true)
//        supportActionBar?.setDisplayShowHomeEnabled(true)

        binding.search.setOnQueryTextListener(this)
        binding.amount.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable) {}
            override fun beforeTextChanged(
                s: CharSequence, start: Int,
                count: Int, after: Int
            ) {
            }

            override fun onTextChanged(
                s: CharSequence, start: Int,
                before: Int, count: Int
            ) {
                if (s.isNotBlank() && s.first().toString() != ".") {
                    adapter.updateList(s.toString().toDouble())
                } else {
                    adapter.updateList(1.0)
                }
            }
        })

        adapter = RatesAdapter()
        binding.rvEmployees.layoutManager = LinearLayoutManager(this)
        binding.rvEmployees.adapter = adapter

        viewModel.res.observe(this, Observer {
            when (it.status) {
                Status.SUCCESS -> {
                    binding.progress.visibility = View.GONE
                    binding.rvEmployees.visibility = View.VISIBLE
                    it.data.let { res ->
                        if (res != null) {
//                            val list = viewModel.updateList(res.rates)
//                            res.let { it1 -> adapter.submitList(it.data) }
                            adapter.submitList(res, viewModel.isRefresh.value)
                            viewModel.setRefresh(false)
                            binding.amount.text.clear()
                        } else {
                            Snackbar.make(binding.rootView, "Status = false", Snackbar.LENGTH_SHORT)
                                .show()
                        }
                    }
                }

                Status.LOADING -> {
                    binding.progress.visibility = View.VISIBLE
                    binding.rvEmployees.visibility = View.GONE
                }

                Status.ERROR -> {
                    binding.progress.visibility = View.GONE
                    binding.rvEmployees.visibility = View.VISIBLE
                    if (it.message.isNullOrBlank()) {
                        Snackbar.make(
                            binding.rootView,
                            "Something went wrong",
                            Snackbar.LENGTH_SHORT
                        ).show()
                    } else {
                        Snackbar.make(
                            binding.rootView,
                            it.message,
                            Snackbar.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // handle arrow click here
        when (item.itemId) {
            android.R.id.home -> {
                finish() // close this activity and return to preview activity (if there is any)
            }

            R.id.refresh_btn -> {
                viewModel.setRefresh(true)
                viewModel.getCurrencies()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onQueryTextSubmit(query: String?): Boolean {
        adapter.filter.filter(query)
//        viewModel.res.value?.data?.rates?.let { viewModel.updateList(it,query?.toDouble()) }?.let {
//            adapter.submitList(
//                it
//            )
//        }
        return false
    }

    override fun onQueryTextChange(query: String?): Boolean {
        adapter.filter.filter(query)
        return false
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.rates_menu, menu)
        return true
    }
}