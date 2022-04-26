package com.fedx.listcountries.network

import android.os.Parcel
import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Countries(
    val name: Detail?,
    val region: String?,
    val capital: List<String>? = null,
    val languages: Map<String, String>? = null,
    val flags: Map<String, String>
) : Parcelable

@Parcelize
data class Detail(
    val common: String?
) : Parcelable