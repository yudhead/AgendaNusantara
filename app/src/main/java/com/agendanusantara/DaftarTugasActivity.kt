package com.agendanusantara

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.agendanusantara.adapter.TaskAdapter
import com.agendanusantara.data.AppDatabase
import com.agendanusantara.databinding.ActivityDaftarTugasBinding

class DaftarTugasActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDaftarTugasBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDaftarTugasBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // TOMBOL KEMBALI
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // JUDUL
        supportActionBar?.title = "Daftar Tugas"

        // DATA FILTER
        val kategori = arrayOf(
            "Semua",
            "Penting",
            "Biasa"
        )

        val status = arrayOf(
            "Semua",
            "Belum Selesai",
            "Selesai"
        )

        // ADAPTER SPINNER KATEGORI
        binding.spKategori.adapter =
            ArrayAdapter(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                kategori
            )

        // ADAPTER SPINNER STATUS
        binding.spStatus.adapter =
            ArrayAdapter(
                this,
                android.R.layout.simple_spinner_dropdown_item,
                status
            )

        // LISTENER FILTER KATEGORI
        binding.spKategori.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    loadData()
                }

                override fun onNothingSelected(
                    parent: AdapterView<*>?
                ) {
                }
            }

        // LISTENER FILTER STATUS
        binding.spStatus.onItemSelectedListener =
            object : AdapterView.OnItemSelectedListener {

                override fun onItemSelected(
                    parent: AdapterView<*>?,
                    view: View?,
                    position: Int,
                    id: Long
                ) {
                    loadData()
                }

                override fun onNothingSelected(
                    parent: AdapterView<*>?
                ) {
                }
            }

        // LOAD AWAL
        loadData()
    }

    private fun loadData() {

        val db = AppDatabase.getDatabase(this)

        // AMBIL FILTER KATEGORI
        val kategori =
            binding.spKategori.selectedItem.toString()

        // AMBIL FILTER STATUS
        val status =
            when (
                binding.spStatus.selectedItem.toString()
            ) {

                "Belum Selesai" -> 0

                "Selesai" -> 1

                else -> -1
            }

        // AMBIL DATA DATABASE
        val listTask =
            db.taskDao().getTaskFilter(
                kategori,
                status
            )

        // SET RECYCLERVIEW
        binding.rvTask.layoutManager =
            LinearLayoutManager(this)

        binding.rvTask.adapter =
            TaskAdapter(
                listTask,
                db.taskDao()
            )
    }

    // TOMBOL BACK
    override fun onSupportNavigateUp(): Boolean {

        finish()

        return true
    }
}