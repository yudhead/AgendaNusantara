package com.agendanusantara

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.agendanusantara.data.AppDatabase
import com.agendanusantara.databinding.ActivityHomeBinding
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter

class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // LOAD DATA
        loadData()
        loadGrafik()

        // BUTTON TAMBAH TUGAS PENTING
        binding.btnPenting.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    TambahPentingActivity::class.java
                )
            )
        }

        // BUTTON TAMBAH TUGAS BIASA
        binding.btnBiasa.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    TambahBiasaActivity::class.java
                )
            )
        }

        // BUTTON DAFTAR TUGAS
        binding.btnDaftar.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    DaftarTugasActivity::class.java
                )
            )
        }

        // BUTTON PENGATURAN
        binding.btnSetting.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    PengaturanActivity::class.java
                )
            )
        }

        // BUTTON LOGOUT
        binding.btnLogout.setOnClickListener {

            startActivity(
                Intent(
                    this,
                    LoginActivity::class.java
                )
            )

            finish()
        }
    }

    // LOAD TOTAL DATA
    private fun loadData() {

        val db = AppDatabase.getDatabase(this)

        val jumlahSelesai =
            db.taskDao().getJumlahSelesai()

        val jumlahBelum =
            db.taskDao().getJumlahBelum()

        binding.tvSelesai.text =
            jumlahSelesai.toString()

        binding.tvBelum.text =
            jumlahBelum.toString()
    }

    // LOAD GRAFIK
    private fun loadGrafik() {

        val db = AppDatabase.getDatabase(this)

        val dataGrafik =
            db.taskDao().getGrafikTaskKategori()

        // LIST TANGGAL
        val listTanggal =
            dataGrafik
                .map { it.tanggalSelesai }
                .distinct()

        val entriesPenting =
            ArrayList<BarEntry>()

        val entriesBiasa =
            ArrayList<BarEntry>()

        // LOOP DATA
        for (i in listTanggal.indices) {

            val tanggal = listTanggal[i]

            val jumlahPenting =
                dataGrafik.find {

                    it.tanggalSelesai == tanggal &&
                            it.kategori == "Penting"

                }?.total ?: 0

            val jumlahBiasa =
                dataGrafik.find {

                    it.tanggalSelesai == tanggal &&
                            it.kategori == "Biasa"

                }?.total ?: 0

            entriesPenting.add(
                BarEntry(
                    i.toFloat(),
                    jumlahPenting.toFloat()
                )
            )

            entriesBiasa.add(
                BarEntry(
                    i.toFloat(),
                    jumlahBiasa.toFloat()
                )
            )
        }

        // DATASET PENTING
        val dataSetPenting =
            BarDataSet(
                entriesPenting,
                "Penting"
            )

        dataSetPenting.color = Color.RED

        // DATASET BIASA
        val dataSetBiasa =
            BarDataSet(
                entriesBiasa,
                "Biasa"
            )

        dataSetBiasa.color = Color.BLUE

        // BAR DATA
        val barData =
            BarData(
                dataSetPenting,
                dataSetBiasa
            )

        val groupSpace = 0.2f
        val barSpace = 0.05f
        val barWidth = 0.35f

        barData.barWidth = barWidth

        binding.barChart.data = barData

        // X AXIS
        binding.barChart.xAxis.apply {

            valueFormatter =
                IndexAxisValueFormatter(
                    listTanggal
                )

            position =
                XAxis.XAxisPosition.BOTTOM

            granularity = 1f

            setCenterAxisLabels(true)

            axisMinimum = 0f

            axisMaximum =
                0f + binding.barChart.barData
                    .getGroupWidth(
                        groupSpace,
                        barSpace
                    ) * listTanggal.size

            setDrawGridLines(false)
        }

        // NONAKTIFKAN AXIS KANAN
        binding.barChart.axisRight.isEnabled = false

        // HILANGKAN DESCRIPTION
        binding.barChart.description.isEnabled = false

        // GROUP BAR
        binding.barChart.groupBars(
            0f,
            groupSpace,
            barSpace
        )

        // ANIMASI
        binding.barChart.animateY(1000)

        // REFRESH
        binding.barChart.invalidate()
    }

    // REFRESH SAAT KEMBALI
    override fun onResume() {
        super.onResume()

        loadData()
        loadGrafik()
    }
}