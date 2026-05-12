package com.agendanusantara.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.agendanusantara.R
import com.agendanusantara.data.task
import com.agendanusantara.data.taskdao
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class TaskAdapter(
    private val listTask: List<task>,
    private val dao: taskdao
) : RecyclerView.Adapter<TaskAdapter.ViewHolder>() {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val judul =
            itemView.findViewById<TextView>(R.id.txtJudul)

        val tanggal =
            itemView.findViewById<TextView>(R.id.txtTanggal)

        val kategori =
            itemView.findViewById<TextView>(R.id.txtKategori)

        val check =
            itemView.findViewById<CheckBox>(R.id.checkSelesai)
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_task, parent, false)

        return ViewHolder(view)
    }

    override fun getItemCount(): Int = listTask.size

    override fun onBindViewHolder(
        holder: ViewHolder,
        position: Int
    ) {

        val task = listTask[position]

        // SET DATA
        holder.judul.text = task.judul
        holder.tanggal.text = task.tanggal
        holder.kategori.text = task.kategori

        // WARNA KATEGORI
        if (task.kategori == "Penting") {

            holder.kategori.setTextColor(
                Color.RED
            )

        } else {

            holder.kategori.setTextColor(
                Color.GREEN
            )
        }

        // STATUS CHECKBOX
        holder.check.setOnCheckedChangeListener(null)

        holder.check.isChecked = task.status == 1

        // UPDATE STATUS
        holder.check.setOnCheckedChangeListener { _, isChecked ->

            val tanggalHariIni =
                SimpleDateFormat(
                    "dd/MM/yyyy",
                    Locale.getDefault()
                ).format(Date())

            val updatedTask = task.copy(

                status =
                    if (isChecked) 1 else 0,

                tanggalSelesai =
                    if (isChecked)
                        tanggalHariIni
                    else
                        null
            )

            dao.updateTask(updatedTask)
        }
    }
}