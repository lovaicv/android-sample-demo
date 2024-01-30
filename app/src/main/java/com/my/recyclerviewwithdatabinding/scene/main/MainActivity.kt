package com.my.recyclerviewwithdatabinding.scene.main

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.my.recyclerviewwithdatabinding.R
import com.my.recyclerviewwithdatabinding.databinding.ActivityMainBinding
import com.my.recyclerviewwithdatabinding.scene.articles.ArticleListActivity
import com.my.recyclerviewwithdatabinding.scene.rates.RatesActivity
import dagger.hilt.android.AndroidEntryPoint

//todo add navigation drawer
//todo add bottom navigation bar
@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val viewModel by viewModels<MainViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.apply {
            viewModel = this@MainActivity.viewModel
            lifecycleOwner = this@MainActivity
        }
        setContentView(binding.root)

        binding.adapter = MovieAdapter(listOf(), listOf(), viewModel)

        viewModel.showToast.observe(this) {
            it.getContentIfNotHandled()?.let { movie ->
                Toast.makeText(this, "MovieClicked: ${movie.name}", Toast.LENGTH_SHORT).show()
            }
        }

        binding.fab.setOnClickListener {
            val myIntent = Intent(this, ArticleListActivity::class.java)
            startActivity(myIntent)
        }
        binding.fab2.setOnClickListener {
            val myIntent = Intent(this, RatesActivity::class.java)
            startActivity(myIntent)
        }

    }
}