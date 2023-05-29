package com.soneralci.cryptocoinretrofit.model

import com.google.gson.annotations.SerializedName

data class CryptoModel(
    //@SerializedName("currency")
    val currency : String,
    //@SerializedName("price")
    val price : String) { // if use data class open constractor
}