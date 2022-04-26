package com.fedx.listcountries

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.bumptech.glide.Glide
import com.fedx.listcountries.databinding.ActivityDetailBinding
import com.fedx.listcountries.network.Countries
import java.util.ArrayList

class ActivityDetail : AppCompatActivity() {
    lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val bundle = intent.getParcelableExtra<Countries>("countries")
        binding.tvNameDetail.text = bundle?.name?.common
        val uri = bundle?.flags?.getValue("png")
        Glide.with(this)
            .load(uri).into(binding.ivFlagDetail)
        binding.tvRegion.text = bundle?.region
        bundle?.capital?.forEach {
            binding.tvCapitalDetail.text = it
        }

        var lang = ArrayList<String>()
        bundle?.languages?.forEach {
            lang.add(it.value)
            binding.tvLanguageDetail.text = lang.toString()
        }
    }
}