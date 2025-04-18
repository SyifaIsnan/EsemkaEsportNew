package com.example.esemkaesport.Detail_Tim

import android.app.Activity
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import com.example.esemkaesport.R
import com.example.esemkaesport.Variabel
import com.example.esemkaesport.databinding.FragmentTentangBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_ID = "id"


/**
 * A simple [Fragment] subclass.
 * Use the [Tentang.newInstance] factory method to
 * create an instance of this fragment.
 */
class Tentang : Fragment() {
    // TODO: Rename and change types of parameters
    private var id: String? = null

    private var _binding: FragmentTentangBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            id = it.getString(ARG_ID)

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentTentangBinding.inflate(inflater, container, false)

        lifecycleScope.launch(Dispatchers.IO) {

        val kon = URL(Variabel.url + "/api/teams/" + id ).openConnection() as HttpURLConnection
        if(kon.responseCode == 200){

            val inputString = kon.inputStream.bufferedReader().readText()
            Log.d("TAG", "onCreateView: Api berhasil")
            val jsonObject = JSONObject(inputString)
            val name = jsonObject.getString("name")
            val about = jsonObject.getString("about")
            val logo = jsonObject.getString("logo256")



                val konGambar =
                    URL(Variabel.url + "/logos/" + logo).openConnection() as HttpURLConnection
                val inputStream = konGambar.inputStream
                val gambar = BitmapFactory.decodeStream(inputStream)
                (context as Activity).runOnUiThread {
                    binding.logoTimDetail.setImageBitmap(gambar)
                    binding.namaTimDetail.text = name
                    binding.tentangTimDetail.text = about

                }
            }

        }


        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Tentang.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String) =
            Tentang().apply {
                arguments = Bundle().apply {
                    putString(ARG_ID, param1)

                }
            }
    }
}