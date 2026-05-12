package com.agendanusantara.data

import androidx.room.*

@Dao
interface taskdao {

    @Insert
    fun insertTask(task: task)

    @Query("SELECT * FROM task ORDER BY id DESC")
    fun getAllTask(): List<task>

    @Update
    fun updateTask(task: task)

    @Query("SELECT COUNT(*) FROM task WHERE status = 1")
    fun getJumlahSelesai(): Int

    @Query("SELECT COUNT(*) FROM task WHERE status = 0")
    fun getJumlahBelum(): Int

    @Query(
        """
    SELECT * FROM task
    WHERE
    (:kategori = 'Semua' OR kategori = :kategori)
    AND
    (
        :status = -1 OR
        status = :status
    )
    ORDER BY id DESC
    """
    )
    fun getTaskFilter(
        kategori: String,
        status: Int
    ): List<task>
    @Query(
        """
    SELECT tanggalSelesai, kategori, COUNT(*) as total
    FROM task
    WHERE status = 1
    GROUP BY tanggalSelesai, kategori
    ORDER BY tanggalSelesai ASC
    """
    )
    fun getGrafikTaskKategori(): List<GrafikTask>
}