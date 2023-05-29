package com.soneralci.cryptocoinretrofit.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.soneralci.cryptocoinretrofit.R
import com.soneralci.cryptocoinretrofit.model.CryptoModel




class RecyclerViewAdapter(private val cryptoList : ArrayList<CryptoModel>, private val listener: Listener) : RecyclerView.Adapter<RecyclerViewAdapter.RowHolder>() {


    interface Listener {
        fun onItemClick(cryptoModel: CryptoModel)
    }

    private val colors : Array<String> = arrayOf("#2986cc","#6a329f","#6aa84f","#ffe599","#cc0000","#9fc5e8","#d5a6bd","#ce7e00")
    class RowHolder(view : View) : RecyclerView.ViewHolder(view) {
        private val textName = view.findViewById<TextView>(R.id.text_name)
        private val textPrice = view.findViewById<TextView>(R.id.text_price)
        fun bind(cryptoModel: CryptoModel, colors : Array<String>, position: Int,listener : Listener){
            itemView.setOnClickListener{
                listener.onItemClick(cryptoModel)
            }
            itemView.setBackgroundColor(Color.parseColor(colors[position %8]))
            textName.text = cryptoModel.currency
            textPrice.text = cryptoModel.price
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RowHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.row_layout,parent,false)
        return RowHolder(view)
    }

    override fun getItemCount(): Int {
        return cryptoList.size
    }

    override fun onBindViewHolder(holder: RowHolder, position: Int) {
        holder.bind(cryptoList[position],colors,position,listener)
    }
}