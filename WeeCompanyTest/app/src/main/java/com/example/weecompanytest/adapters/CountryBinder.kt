package com.example.weecompanytest.adapters

import android.view.View
import android.view.View.inflate
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.bumptech.glide.Glide
import com.example.weecompanytest.R
import com.example.weecompanytest.models.Country
import com.google.android.material.card.MaterialCardView
import kotlinx.android.synthetic.main.item_country.view.*
import mva2.adapter.ItemBinder
import mva2.adapter.ItemViewHolder
import java.text.FieldPosition

interface ICountryBinder{
    fun seleccion(item: Country, position: Int)
}

class CountryBinder(val callback: ICountryBinder): ItemBinder<Country, CountryBinder.ViewHolder>() {
    override fun bindViewHolder(holder: CountryBinder.ViewHolder, item: Country?) {
        holder.title.text = item?.country
        if (item?.isSelect == true) {
            holder.select.visibility = View.VISIBLE
        }else{
            holder.select.visibility = View.GONE
        }
        holder.item.setOnClickListener {
            callback.seleccion(item!!, holder.adapterPosition)
        }
    }

    override fun createViewHolder(parent: ViewGroup): CountryBinder.ViewHolder {
        return ViewHolder(inflate(parent, R.layout.item_country))
    }

    override fun canBindData(item: Any?): Boolean {
        return item is Country
    }

    inner class ViewHolder(view: View): ItemViewHolder<Country>(view) {
        val item: MaterialCardView = view.item
        val title: TextView = view.tv_country
        val select: ImageView = view.iv_select
    }
}