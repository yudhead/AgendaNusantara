package com.agendanusantara

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.agendanusantara.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding
    private lateinit var prefs: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // SHARED PREFERENCES
        prefs = getSharedPreferences("LOGIN", MODE_PRIVATE)

        // DEFAULT ACCOUNT
        setupDefaultAccount()

        // BUTTON LOGIN
        binding.btnLogin.setOnClickListener {

            handleLogin()
        }
    }

    // AKUN DEFAULT
    private fun setupDefaultAccount() {

        if (!prefs.contains("username")) {

            prefs.edit().apply {

                putString("username", "user")
                putString("password", "user")

                apply()
            }
        }
    }

    // LOGIN
    private fun handleLogin() {

        val inputUsername =
            binding.etUsername.text.toString().trim()

        val inputPassword =
            binding.etPassword.text.toString().trim()

        // VALIDASI KOSONG
        if (
            inputUsername.isEmpty() ||
            inputPassword.isEmpty()
        ) {

            Toast.makeText(
                this,
                "Harap isi username dan password",
                Toast.LENGTH_SHORT
            ).show()

            return
        }

        // AMBIL DATA TERSIMPAN
        val savedUsername =
            prefs.getString("username", "user")

        val savedPassword =
            prefs.getString("password", "user")

        // CEK LOGIN
        if (
            inputUsername == savedUsername &&
            inputPassword == savedPassword
        ) {

            Toast.makeText(
                this,
                "Login berhasil",
                Toast.LENGTH_SHORT
            ).show()

            startActivity(
                Intent(this, HomeActivity::class.java)
            )

            finish()

        } else {

            Toast.makeText(
                this,
                "Username atau password salah",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}