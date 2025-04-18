package com.example.esemkaesport.Detail_Tim

import android.app.Activity
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.esemkaesport.R
import com.example.esemkaesport.StatistikAdapter
import com.example.esemkaesport.StatistikTim
import com.example.esemkaesport.Variabel
import com.example.esemkaesport.databinding.FragmentStatistikBinding
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
 * Use the [Statistik.newInstance] factory method to
 * create an instance of this fragment.
 */
class Statistik : Fragment() {
    // TODO: Rename and change types of parameters
    private var id: String? = null

    private var _binding: FragmentStatistikBinding? = null
    private val binding get() = _binding!!

    val listStatistik = mutableListOf<StatistikTim>(
        StatistikTim(jenis = "deaths", angka = 0, logo = R.drawable.deaths),
        StatistikTim(jenis = "Kills", angka = 0, logo = R.drawable.kills),
        StatistikTim(jenis = "Assists", angka = 0, logo = R.drawable.assists),
        StatistikTim(jenis = "Gold", angka = 0, logo = R.drawable.gold),
        StatistikTim(jenis = "Damage", angka = 0, logo = R.drawable.damage),
        StatistikTim(jenis = "Lord Kills", angka = 0, logo = R.drawable.lord_kills),
        StatistikTim(jenis = "Tortoise Kills", angka = 0, logo = R.drawable.tortoise_kills),
        StatistikTim(jenis = "Tower Destroys", angka = 0, logo = R.drawable.towe_destroy)
    )


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
        _binding = FragmentStatistikBinding.inflate(inflater, container, false)


        lifecycleScope.launch(Dispatchers.IO) {

            val kon = URL(Variabel.url + "/api/teams/" + id).openConnection() as HttpURLConnection
            if (kon.responseCode == 200) {

                val inputString = kon.inputStream.bufferedReader().readText()

                val jsonObject = JSONObject(inputString)
                val deaths = jsonObject.getInt("deaths")
                val kills = jsonObject.getInt("kills")
                val assists = jsonObject.getInt("assists")
                val gold = jsonObject.getInt("gold")
                val damage = jsonObject.getInt("damage")
                val lordkills = jsonObject.getInt("lordKills")
                val tortoisekills = jsonObject.getInt("tortoiseKills")
                val towerdestroy = jsonObject.getInt("towerDestroy")

                listStatistik[0].angka = deaths
                listStatistik[1].angka = kills
                listStatistik[2].angka = assists
                listStatistik[3].angka = gold
                listStatistik[4].angka = damage
                listStatistik[5].angka = lordkills
                listStatistik[6].angka = tortoisekills
                listStatistik[7].angka = towerdestroy

                Log.d("TAG", "onCreateView: Api berhasil $listStatistik")
                (context as Activity).runOnUiThread{
                    binding.recyclerViewStatistik.apply {
                        this.layoutManager = GridLayoutManager(requireActivity(), 2)
                        this.adapter = StatistikAdapter(listStatistik, requireActivity())

                    }
                    binding.recyclerViewStatistik.layoutManager = GridLayoutManager(requireActivity(), 2)
                    binding.recyclerViewStatistik.adapter = StatistikAdapter(listStatistik, requireActivity())
                }


            } else{
                Log.d("TAG", "onCreateView: Api gagal ${kon.responseCode} $id")
            }
        }



        // Inflate the layout for this fragment
        return binding.root
    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment Statistik.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String) =
            Statistik().apply {
                arguments = Bundle().apply {
                    putString(ARG_ID, param1)

                }
            }
    }
}