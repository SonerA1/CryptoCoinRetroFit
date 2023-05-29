package com.soneralci.cryptocoinretrofit.service

import com.soneralci.cryptocoinretrofit.model.CryptoModel
import io.reactivex.Observable
import retrofit2.http.GET

interface CryptoAPI {

    //GET POST UPDATE DELETE
    //GET -> Pull Database
    //POST -> Set or get database
    @GET("atilsamancioglu/K21-JSONDataSet/master/crypto.json")
    fun getData() : Observable<List<CryptoModel>> //RxJava
    //fun getData(): Call<List<CryptoModel>>


}