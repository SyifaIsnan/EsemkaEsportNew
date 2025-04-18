package com.example.esemkaesport

import android.app.Activity
import android.content.Context
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import java.net.HttpURLConnection
import java.net.URL

class PemainAdapter(val listPemain: MutableList<PemainModel>, val context: Context): RecyclerView.Adapter<PemainAdapter.PemainAdapterView>() {

    class PemainAdapterView(val view: View): RecyclerView.ViewHolder(view){
        val image: ImageView = view.findViewById(R.id.fotoPemain)
        val nama: TextView = view.findViewById(R.id.namaPemain)
        val posisi: TextView = view.findViewById(R.id.posisiPemain)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PemainAdapterView {
        val layout = LayoutInflater.from(context).inflate(R.layout.list_pemain, parent, false)
        return PemainAdapterView(layout)
    }

    override fun getItemCount(): Int {
        return listPemain.size
    }

    override fun onBindViewHolder(holder: PemainAdapterView, position: Int) {
        val pemain = listPemain[position]

        UpdateUI(holder, pemain)

    }

    private fun UpdateUI(holder: PemainAdapterView, pemain: PemainModel) = runBlocking {
//        launch(Dispatchers.IO){
//
//            val kon = URL(Variabel.url + "/logos/" + pemain.logo256).openConnection() as HttpURLConnection
//            val inputStream = kon.inputStream
//            val gambar = BitmapFactory.decodeStream(inputStream)
//            (context as Activity).runOnUiThread{
//                holder.image.setImageBitmap(gambar)
//                holder.nama.text = pemain.name
//                holder.posisi.text = pemain.posisi
//            }
//
//        }


    }


}