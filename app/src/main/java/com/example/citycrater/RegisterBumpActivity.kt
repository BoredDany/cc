package com.example.citycrater

import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.citycrater.Permissions.Permiso

class RegisterBumpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_register_bump)

        val btnCamera = findViewById<ImageButton>(R.id.btnCamera)

        when {
            ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED -> {
                Toast.makeText(this, "puede usar la camara", Toast.LENGTH_SHORT).show()
            }
            ActivityCompat.shouldShowRequestPermissionRationale(this, android.Manifest.permission.CAMERA) -> {
                requestPermissions(arrayOf(android.Manifest.permission.CAMERA),
                    Permiso.MY_PERMISSION_REQUEST_CAMERA)
            }
            else -> {
                requestPermissions(arrayOf(android.Manifest.permission.CAMERA),
                    Permiso.MY_PERMISSION_REQUEST_CAMERA)
            }
        }

        btnCamera.setOnClickListener {
            takePic()
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == Permiso.REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            val imgBump = findViewById<ImageView>(R.id.imgBump)
            imgBump.setImageBitmap(imageBitmap)
        }
    }

    private fun takePic(){
        val permissionCheck = ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA)
        val takePictureIntent =  Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (permissionCheck == PackageManager.PERMISSION_GRANTED) {
            Toast.makeText(this, "HAY PERMISO DE CAMARA", Toast.LENGTH_SHORT).show()
            //takepic
            if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                startActivityForResult(takePictureIntent, Permiso.REQUEST_IMAGE_CAPTURE);
            } else {
                Toast.makeText(this, "No hay una cámara disponible en este dispositivo", Toast.LENGTH_SHORT).show();
            }

        } else {
            requestPermissions(arrayOf(android.Manifest.permission.CAMERA),
                Permiso.MY_PERMISSION_REQUEST_CAMERA)
        }
    }
    private fun requestPermission(context: Activity, permiso: String, justificacion: String, idCode: Int) {
        if (ContextCompat.checkSelfPermission(context, permiso) != PackageManager.PERMISSION_GRANTED) {
            //
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            Permiso.MY_PERMISSION_REQUEST_CAMERA -> {
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    Toast.makeText(this,"HAY PERMISO DE CAMARA", Toast.LENGTH_SHORT).show()
                } else {
                    Toast.makeText(this,"NO HAY PERMISO DE CAMARA", Toast.LENGTH_SHORT).show()
                }
                return
            }
            else -> {
            }
        }
    }
}