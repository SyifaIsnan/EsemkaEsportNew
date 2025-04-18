package com.example.esemkaesport

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.esemkaesport.Detail_Tim.PemainTimDetail
import com.example.esemkaesport.Detail_Tim.Prestasi
import com.example.esemkaesport.Detail_Tim.Statistik
import com.example.esemkaesport.Detail_Tim.Tentang
import com.example.esemkaesport.databinding.ActivityDetailTimBinding
import com.example.esemkaesport.timdanpemain.Pemain
import com.example.esemkaesport.timdanpemain.Tim

class DetailTim : AppCompatActivity() {

    private lateinit var binding: ActivityDetailTimBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityDetailTimBinding.inflate(layoutInflater)

        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        val id = intent.getStringExtra("id").toString()

        gantiFragment(Tentang.newInstance(id))

        binding.bottomNavDetail.setOnItemSelectedListener {

            when (it.itemId){
                R.id.tentang -> gantiFragment(Tentang.newInstance(id))
                R.id.prestasi -> gantiFragment(Prestasi())
                R.id.statistik -> gantiFragment(Statistik.newInstance(id))
                R.id.pemain -> gantiFragment(PemainTimDetail())


            }

            true

        }


    }

    private fun gantiFragment(fragment: Fragment) {


        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout,fragment)
        fragmentTransaction.commit()

    }


}