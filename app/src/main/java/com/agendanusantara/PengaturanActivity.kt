package com.agendanusantara

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.agendanusantara.databinding.ActivityPengaturanBinding

class PengaturanActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPengaturanBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPengaturanBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.title = "Pengaturan"
        // SHARED PREFERENCES
        val pref = getSharedPreferences("LOGIN", MODE_PRIVATE)

        // PASSWORD DEFAULT
        val passwordTersimpan =
            pref.getString("password", "user")

        // BUTTON SIMPAN PASSWORD
        binding.btnSimpanPassword.setOnClickListener {

            val passwordLama =
                binding.etPasswordLama.text.toString()

            val passwordBaru =
                binding.etPasswordBaru.text.toString()

            // VALIDASI KOSONG
            if (
                passwordLama.isEmpty() ||
                passwordBaru.isEmpty()
            ) {

                Toast.makeText(
                    this,
                    "Semua field harus diisi",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            // CEK PASSWORD LAMA
            if (passwordLama == passwordTersimpan) {

                // SIMPAN PASSWORD BARU
                pref.edit()
                    .putString("password", passwordBaru)
                    .apply()

                Toast.makeText(
                    this,
                    "Password berhasil diubah",
                    Toast.LENGTH_SHORT
                ).show()

                // KOSONGKAN INPUT
                binding.etPasswordLama.setText("")
                binding.etPasswordBaru.setText("")

            } else {

                Toast.makeText(
                    this,
                    "Password lama salah",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }


        // DATA DEVELOPER
        binding.tvNamaDeveloper.text = "Bagas Yudha A"
        binding.tvNimDeveloper.text = "254107027007"
    }
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}
