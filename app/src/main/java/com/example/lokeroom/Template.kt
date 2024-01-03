package com.example.lokeroom

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler

class Template(val a:Context,val id:MutableList<String>,val nama:MutableList<String>,val nomor:MutableList<String>,val foto:MutableList<Bitmap>,val pemosting:MutableList<String>): RecyclerView.Adapter<Template.ListViewHolder>(){
    val session:SharedPreferences = a.getSharedPreferences("pengguna",Context.MODE_PRIVATE)
    val id_pelogin:String = session.getString("id_pengguna",null).toString()

    class ListViewHolder(itemView:View) : RecyclerView.ViewHolder(itemView){
        val btn_hapus:Button = itemView.findViewById(R.id.btn_hapus)
        val btn_edit:Button = itemView.findViewById(R.id.btn_edit)
        val profile_image:ImageView = itemView.findViewById(R.id.profile_image)
        val nama:TextView = itemView.findViewById(R.id.nama)
        val nomor:TextView = itemView.findViewById(R.id.nomor)
        val pekerjaan:CardView = itemView.findViewById(R.id.pekerjaan)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): Template.ListViewHolder {
        val view: View = LayoutInflater.from(parent.context).inflate(R.layout.template,parent,false)
        return ListViewHolder(view)
    }

    override fun onBindViewHolder(holder: Template.ListViewHolder, position: Int) {
        holder.profile_image.setImageBitmap(foto.get(position))
        holder.nama.text = nama.get(position)
        holder.nomor.text = nomor.get(position)

        holder.btn_edit.setOnClickListener {

            val id_pekerjaan = id.get(position)
            val id_pemosting = pemosting.get(position)
            if (id_pemosting == id_pelogin){
                val pindah:Intent = Intent(a,Edit::class.java)
                pindah.putExtra("id_pekerjaan",id_pekerjaan)
                a.startActivity(pindah)
            }else{
                Toast.makeText(a,"Bukan Lowongan Yang Anda Buat", Toast.LENGTH_LONG).show()
            }

        }

        holder.btn_hapus.setOnClickListener {
            val id_pekerjaan = id.get(position)
            val id_pemosting = pemosting.get(position)
            if (id_pemosting == id_pelogin){
                val pindah:Intent = Intent(a,Hapus::class.java)
                pindah.putExtra("id_pekerjaan",id_pekerjaan)
                a.startActivity(pindah)
            }else{
                Toast.makeText(a,"Bukan Lowongan Yang Anda Buat", Toast.LENGTH_LONG).show()
            }

        }

        holder.pekerjaan.setOnClickListener {
            val id_pekerjaan = id.get(position)
                val pindah:Intent = Intent(a,Detail::class.java)
                pindah.putExtra("id_pekerjaan",id_pekerjaan)
                a.startActivity(pindah)

        }

    }

    override fun getItemCount(): Int {
        return nama.size
    }

}