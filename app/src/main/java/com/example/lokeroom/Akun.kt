package com.example.lokeroom

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.database.sqlite.SQLiteDatabase
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.ComponentActivity
import androidx.activity.result.contract.ActivityResultContracts
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream

class Akun : AppCompatActivity() {
    var img_tambah: ImageView? = null
    var uriGambar: Uri? = null
    var bitmapGambar: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.akun)

        val session:SharedPreferences = getSharedPreferences("pengguna", MODE_PRIVATE)
        val id_pelogin:String = session.getString("id_pengguna",null).toString()
        val db:SQLiteDatabase = openOrCreateDatabase("loker", MODE_PRIVATE,null)
        val query = db.rawQuery("SELECT * FROM pengguna WHERE id_pengguna = '$id_pelogin'",null)
        query.moveToNext()
        val edt_email:EditText = findViewById(R.id.edt_email)
        val edt_password:EditText = findViewById(R.id.edt_password)
        val edt_nama:EditText = findViewById(R.id.edt_nama)
        val edt_nomor:EditText = findViewById(R.id.edt_nomor)
        val btn_ubah:Button = findViewById(R.id.btn_ubah)
        val foto_profil:ImageView? = findViewById(R.id.profile_image)
        val email:String = query.getString(2)
        val password:String = query.getString(3)
        val nama:String = query.getString(1)
        val nomor:String = query.getString(4)
        val foto:ByteArray? = query.getBlob(5)

        try {
            val blobGambar = ByteArrayInputStream(foto)
            val gambarBitmap : Bitmap = BitmapFactory.decodeStream(blobGambar)
            foto_profil?.setImageBitmap(gambarBitmap)
        }catch (a:Exception){
            val gambarBitmap: Bitmap = BitmapFactory.decodeResource(this.resources,R.drawable.orang)
            foto_profil?.setImageBitmap(gambarBitmap)
        }

        val pilihGambar = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
            if(it.resultCode == Activity.RESULT_OK){
                val gambarDiperoleh = it.data
                if(gambarDiperoleh!=null){
                    uriGambar = gambarDiperoleh.data
                    bitmapGambar = MediaStore.Images.Media.getBitmap(contentResolver,uriGambar)
                    foto_profil?.setImageBitmap(bitmapGambar)
                }
            }
        }

        foto_profil?.setOnClickListener {
            val bukaGaleri: Intent = Intent(Intent.ACTION_PICK,MediaStore.Images.Media.INTERNAL_CONTENT_URI)
            pilihGambar.launch(bukaGaleri)
        }



        edt_email.setText(email)
        edt_password.setText(password)
        edt_nama.setText(nama)
        edt_nomor.setText(nomor)


        btn_ubah.setOnClickListener{
            val isi_email:String = edt_email.text.toString()
            val isi_password:String = edt_password.text.toString()
            val isi_nama:String = edt_nama.text.toString()
            val isi_nomor:String = edt_nomor.text.toString()
            val bos = ByteArrayOutputStream()
            bitmapGambar?.compress(Bitmap.CompressFormat.JPEG, 100, bos)
            val byteArrayGambar = bos.toByteArray()
            val db:SQLiteDatabase = openOrCreateDatabase("loker", MODE_PRIVATE,null)
            val edit =
                "UPDATE pengguna SET nama_pengguna = ?,email_pengguna = ?,pass_pengguna=?,nomor_pengguna=?,foto_pengguna=? WHERE id_pengguna = '$id_pelogin'"
            val statement = db.compileStatement(edit)
            statement.clearBindings()
            statement.bindString(1, isi_nama)
            statement.bindString(2, isi_email)
            statement.bindString(3, isi_password)
            statement.bindString(4,isi_nomor)
            statement.bindBlob(5, byteArrayGambar)
            statement.executeUpdateDelete()

            val pindah:Intent = Intent(this,Dashboard::class.java)
            startActivity(pindah)
        }


    }
}