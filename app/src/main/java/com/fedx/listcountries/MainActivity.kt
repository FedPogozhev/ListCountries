package com.fedx.listcountries

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.fedx.listcountries.databinding.ActivityMainBinding
import com.fedx.listcountries.network.Countries
import java.util.ArrayList

class MainActivity : AppCompatActivity(), AdapterCountries.Listener {
    lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: ViewModel
    val adapterCountries = AdapterCountries(this, this)

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        binding.lifecycleOwner = this
        viewModel = ViewModelProvider(this).get(ViewModel::class.java)
        viewModel.property.observe(this, Observer {
            adapterCountries.data = it
        })

        binding.rv.adapter = adapterCountries
        viewModel.getCountries()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_search, menu)

        var menuItemSearch = menu!!.findItem(R.id.itemSearch)
        var searchView = menuItemSearch.actionView as SearchView

        searchView.maxWidth = Int.MAX_VALUE

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }
            override fun onQueryTextChange(newText: String?): Boolean {
                adapterCountries.filter.filter(newText)
                return true
            }
        })
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId){
            R.id.itemSort -> {
                adapterCountries.sorted()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun detail(countries: Countries) {
        val intent = Intent(this, ActivityDetail::class.java)
        intent.putExtra("countries", countries)

        startActivity(intent)
    }
}