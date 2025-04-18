package com.example.esemkaesport

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.esemkaesport.PemainAdapter.PemainAdapterView

class StatistikAdapter(val listStatistik: MutableList<StatistikTim>, val context: Context):RecyclerView.Adapter<StatistikAdapter.StatistikAdapterView>()  {

    class StatistikAdapterView(val view: View): RecyclerView.ViewHolder(view) {
        val logo: ImageView = view.findViewById(R.id.statistikLogo)
        val jenis: TextView = view.findViewById(R.id.statistikJenisdanAngka)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StatistikAdapterView {
        val layout = LayoutInflater.from(context).inflate(R.layout.list_statistik, parent, false)
        return StatistikAdapterView(layout)
    }

    override fun getItemCount(): Int {
        return listStatistik.size
    }

    override fun onBindViewHolder(holder: StatistikAdapterView, position: Int) {
        val statistik = listStatistik[position]
        UpdateUI(holder, statistik)
    }

    private fun UpdateUI(holder: StatistikAdapterView, statistik: StatistikTim) {
        holder.jenis.text = statistik.angka.toString() + " " + statistik.jenis
        holder.logo.setImageDrawable(ContextCompat.getDrawable(holder.logo.context, statistik.logo))
    }


}