package com.soneralci.cryptocoinretrofit.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.soneralci.cryptocoinretrofit.R
import com.soneralci.cryptocoinretrofit.adapter.RecyclerViewAdapter
import com.soneralci.cryptocoinretrofit.model.CryptoModel
import com.soneralci.cryptocoinretrofit.service.CryptoAPI
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory


class MainActivity : AppCompatActivity(), RecyclerViewAdapter.Listener {
    private val BASE_URL = "https://raw.githubusercontent.com/"
    private var cryptoModel: ArrayList<CryptoModel>? = null
    private var recyclerViewAdapter: RecyclerViewAdapter? = null
    private lateinit var recyclerView: RecyclerView

    //Disposable RxJava
    private var compositeDisposable: CompositeDisposable? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        compositeDisposable = CompositeDisposable()


        //recyclerView
        recyclerView = findViewById(R.id.recyclerView)

        val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = layoutManager

        loadData()
    }

    private fun loadData() {
        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build().create(CryptoAPI::class.java)

        compositeDisposable?.add(
            retrofit.getData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponse)
        )


        /*
        val service = retrofit.create(CryptoAPI::class.java)
        val call = service.getData()

        call.enqueue(object : Callback<List<CryptoModel>> {
            override fun onResponse(
                call: Call<List<CryptoModel>>,
                response: Response<List<CryptoModel>>
            ) {
                if (response.isSuccessful) {
                    response.body()?.let {
                        cryptoModel = ArrayList(it)
                        cryptoModel?.let { // Nullability kontrolü eklendi
                            recyclerViewAdapter = RecyclerViewAdapter(it, this@MainActivity)
                            recyclerView.adapter = recyclerViewAdapter
                        }
                    }
                }
            }

            override fun onFailure(call: Call<List<CryptoModel>>, t: Throwable) {
                t.printStackTrace()
            }
        })

         */
    }

    private fun handleResponse(cryptoList: List<CryptoModel>) {
        cryptoModel = ArrayList(cryptoList)
        cryptoModel?.let { // Nullability kontrolü eklendi
            recyclerViewAdapter = RecyclerViewAdapter(it, this@MainActivity)
            recyclerView.adapter = recyclerViewAdapter
        }

    }

    override fun onItemClick(cryptoModel: CryptoModel) {
        Toast.makeText(this, "Clicked: ${cryptoModel.currency}", Toast.LENGTH_LONG).show()
    }

    override fun onDestroy() {
        super.onDestroy()
        compositeDisposable?.clear()
    }
}

