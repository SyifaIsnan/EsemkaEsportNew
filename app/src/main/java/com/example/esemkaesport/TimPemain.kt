package com.example.esemkaesport

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import com.example.esemkaesport.databinding.ActivityTimBinding
import com.example.esemkaesport.timdanpemain.Pemain
import com.example.esemkaesport.timdanpemain.Tim

class TimPemain : AppCompatActivity() {

    private lateinit var binding: ActivityTimBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        binding = ActivityTimBinding.inflate(layoutInflater)
        setContentView(binding.root)

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }


        gantiFragment(Tim())

        binding.bottomNav.setOnItemSelectedListener {

            when (it.itemId){
                R.id.tim -> gantiFragment(Tim())
                R.id.pemain -> gantiFragment(Pemain())

            }

            true

        }






    }

    private fun gantiFragment(fragment: Fragment) {


        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frame_layout,fragment)
        fragmentTransaction.commit()

    }


}