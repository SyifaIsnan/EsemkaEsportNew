package com.example.esemkaesport.timdanpemain

import android.app.Activity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import com.example.esemkaesport.R
import com.example.esemkaesport.TimAdapter
import com.example.esemkaesport.TimModel
import com.example.esemkaesport.Variabel
import com.example.esemkaesport.databinding.FragmentTimBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONArray
import java.net.HttpURLConnection
import java.net.URL

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [Tim.newInstance] factory method to
 * create an instance of this fragment.
 */
class Tim : Fragment() {
    // TODO: Rename and change types of parameters


    private var _binding: FragmentTimBinding? = null
    private val binding get () = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentTimBinding.inflate(inflater, container, false)

        val listTim: MutableList<TimModel> = mutableListOf()

        lifecycleScope.launch(Dispatchers.IO){

            val kon = URL(Variabel.url + "/api/teams").openConnection() as HttpURLConnection //
            if(kon.responseCode == 200){

                val inputString = kon.inputStream.bufferedReader().readText() //
                Log.d("TAG", "onCreateView: API berhasil $inputString")
                val jsonArray = JSONArray(inputString)
                for(i in 0 until jsonArray.length()){
                    val jsonObject = jsonArray.getJSONObject(i)
                    listTim.add(TimModel(
                        id = jsonObject.getString("id"),
                        name = jsonObject.getString("name"),
                        about = jsonObject.getString("about"),
                        kills = jsonObject.getString("kills"),
                        deaths = jsonObject.getString("deaths"),
                        assists = jsonObject.getString("assists"),
                        gold = jsonObject.getString("gold"),
                        damage = jsonObject.getString("damage"),
                        lordKills = jsonObject.getString("lordKills"),
                        tortoiseKills = jsonObject.getString("tortoiseKills"),
                        towerDestroy = jsonObject.getString("towerDestroy"),
                        logo256 = jsonObject.getString("logo256"),
                        logo500 = jsonObject.getString("logo500"),

                        ))
                }
//                (requireActivity()).runOnUiThread {
                    Log.d("TAG", "onCreateView: $listTim")
//                    binding.fragmentTeamRecyclerView.adapter = TimAdapter(listTim, requireActivity())
//                    val layoutManager = GridLayoutManager(requireActivity(), 2)
//                    binding.fragmentTeamRecyclerView.layoutManager = layoutManager

                    binding.fragmentTeamRecyclerView.apply {
                        this.layoutManager = GridLayoutManager(requireActivity(), 2)
                        this.adapter = TimAdapter(listTim, requireActivity())
                    }

                //}
            } else{
                Log.d("TAG", "onCreateView: Gagal")
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
         * @return A new instance of fragment Tim.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance(param1: String, param2: String) =
            Tim().apply {
                arguments = Bundle().apply {
                    putString(ARG_PARAM1, param1)
                    putString(ARG_PARAM2, param2)
                }
            }
    }
}