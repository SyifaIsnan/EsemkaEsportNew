package com.example.esemkaesport

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import com.example.esemkaesport.databinding.ActivityMainBinding
import com.example.esemkaesport.databinding.ActivityRegisterBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
import java.io.OutputStreamWriter
import java.net.HttpURLConnection
import java.net.URL

class Register : AppCompatActivity() {

    private lateinit var binding: ActivityRegisterBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_register)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        binding = ActivityRegisterBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.signUp.setOnClickListener {

            val fullName = binding.fullName.text.toString()
            val username = binding.username.text.toString()
            val email = binding.email.text.toString()
            val password = binding.password.text.toString()
            val confirmPassword = binding.confirmPassword.text.toString()


            if (fullName.isEmpty() or username.isEmpty() or email.isEmpty() or password.isEmpty() or confirmPassword.isEmpty()) {
                Toast.makeText(this@Register, "Data harus diisi semua!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if (password != confirmPassword) {
                Toast.makeText(this@Register, "Password harus sama!", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            if ((username.length < 6) or (password.length < 6)) {
                Toast.makeText(
                    this@Register,
                    "Data tidak boleh kurang dari 6 karakter",
                    Toast.LENGTH_SHORT
                ).show()
                return@setOnClickListener
            }

            val jsonobject = JSONObject()
            jsonobject.put("fullName", fullName)
            jsonobject.put("username", username)
            jsonobject.put("email", email)
            jsonobject.put("phoneNumber", "08")
            jsonobject.put("password", password)

            lifecycleScope.launch(Dispatchers.IO) {
                val con = URL(Variabel.url + "/api/sign-up").openConnection() as HttpURLConnection
                con.doOutput = true
                con.doInput = true
                con.requestMethod = "POST"
                con.setRequestProperty("Content-Type", "application/json")
                con.setRequestProperty("accept", "application/json")
                val outputStreamWriter = OutputStreamWriter(con.outputStream)
                outputStreamWriter.write(jsonobject.toString())
                outputStreamWriter.flush()
                if (con.responseCode == 409) {
                    runOnUiThread {
                        Toast.makeText(this@Register, "Error", Toast.LENGTH_SHORT).show()
                    }

                } else {
                    runOnUiThread {
                        Toast.makeText(this@Register, "Berhasil membuat akun!", Toast.LENGTH_SHORT)
                            .show()
                        val intent = Intent(this@Register, Login::class.java)
                        startActivity(intent)

                    }
                }

            }
        }
    }
}