package com.example.esemkaesport

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.esemkaesport.databinding.ActivityLoginBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

class Login : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_login)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signIn.setOnClickListener {

            val usernameOrEmail = binding.username.text.toString()
            val password = binding.password.text.toString()

            if (usernameOrEmail.isEmpty() or password.isEmpty()) {
                Toast.makeText(this@Login, "Data harus diisi semua!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (usernameOrEmail != password) {
                Toast.makeText(this@Login, "Username dan password harus sama!", Toast.LENGTH_SHORT)
                    .show()
                return@setOnClickListener
            }

            val jsonObject = JSONObject()
            jsonObject.put("usernameOrEmail", usernameOrEmail)
            jsonObject.put("password", password)

            lifecycleScope.launch(Dispatchers.IO) {
                val con = URL(Variabel.url + "/api/sign-in").openConnection() as HttpURLConnection
                con.doOutput = true
                con.doInput = true
                con.requestMethod = "POST"
                con.setRequestProperty("Content-Type", "application/json")
                con.setRequestProperty("accept", "application/json")
                val outputStreamWriter = OutputStreamWriter(con.outputStream)
                outputStreamWriter.write(jsonObject.toString())
                outputStreamWriter.flush()

                if (con.responseCode == 404) {
                    runOnUiThread {
                        Toast.makeText(this@Login, "Data user tidak ditemukan", Toast.LENGTH_SHORT)
                            .show()
                    }

                }
                else {

                    val inputString = con.inputStream.bufferedReader().readText()
                    val jsonObject = JSONObject(inputString)
                    Variabel.username = jsonObject.getString("username")

                    runOnUiThread {
                        Toast.makeText(this@Login, "Berhasil login!", Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@Login, TimPemain::class.java)
                        startActivity(intent)
                    }
                }

            }
        }
    }
}