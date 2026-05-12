package com.agendanusantara.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "task")
data class task(

    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    val judul: String,
    val deskripsi: String,
    val tanggal: String,
    val kategori: String,

    var status: Int = 0,

    val tanggalSelesai: String? = null
)