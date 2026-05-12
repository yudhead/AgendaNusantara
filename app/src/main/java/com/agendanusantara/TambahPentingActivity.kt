package com.agendanusantara

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.agendanusantara.data.AppDatabase
import com.agendanusantara.data.task
import android.app.DatePickerDialog
import java.util.Calendar
class TambahPentingActivity : AppCompatActivity() {

    lateinit var edtJudul: EditText
    lateinit var edtDeskripsi: EditText
    lateinit var txtTanggal: EditText
    lateinit var btnSimpan: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tambah_penting)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        supportActionBar?.title = "Tambah Tugas Penting"

        txtTanggal = findViewById(R.id.txtTanggal)
        edtJudul = findViewById(R.id.edtJudul)
        edtDeskripsi = findViewById(R.id.edtDeskripsi)
        btnSimpan = findViewById(R.id.btnSimpan)
        // DATABASE
        val db = AppDatabase.getDatabase(this)
        txtTanggal.setOnClickListener {

            val calendar = Calendar.getInstance()

            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePicker = DatePickerDialog(
                this,
                { _, y, m, d ->

                    txtTanggal.setText("$d/${m + 1}/$y")

                },
                year,
                month,
                day
            )

            datePicker.show()
        }
        btnSimpan.setOnClickListener {

            if (
                txtTanggal.text.toString().isEmpty() ||
                edtJudul.text.toString().isEmpty() ||
                edtDeskripsi.text.toString().isEmpty()
            ) {

                Toast.makeText(
                    this,
                    "Semua field harus diisi",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            val data = task(
                judul = edtJudul.text.toString(),
                deskripsi = edtDeskripsi.text.toString(),
                tanggal = txtTanggal.text.toString(),
                kategori = "Penting",
                status = 0
            )

            db.taskDao().insertTask(data)

            Toast.makeText(
                this,
                "Berhasil disimpan",
                Toast.LENGTH_SHORT
            ).show()

            finish()
        }
    }
    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}