package com.sample.downloadfile

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.Environment
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File


class MainActivity : AppCompatActivity() {

    private val TAG = "PERMISSION"
    private val key = "I love Kotlin"
    private var isEncrypt = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn.setOnClickListener{
            if(isWriteStoragePermissionGranted() && isReadStoragePermissionGranted()){
                //create file
              val file =   File(Environment.getExternalStorageDirectory(),"sample.txt")
                if(!file.exists()){
                    file.createNewFile()
                }

                if(isEncrypt){
                    encryptFile(File(Environment.getExternalStorageDirectory(),"sample.txt"),
                        File(Environment.getExternalStorageDirectory(),"sample.encrypted")
                    )
                    isEncrypt = true
                }else{
                    decryptFile(File(Environment.getExternalStorageDirectory(),"sample.encrypted"),
                    File(Environment.getExternalStorageDirectory(),"sample.txt"))
                    isEncrypt = false
                }
            }
        }

    }

    //encrypt file
    private fun encryptFile(file:File, out:File){
        try{
            SecurityUtils().encrypt(key,file,out)
            Toast.makeText(this,"ENCRYPTED FILE",Toast.LENGTH_LONG).show()
        }catch (ex:SecurityException){
            Log.d("ENCRYPTION", ex.message!!)

        }
    }

    //decrypt file
    private fun decryptFile(file: File, out: File){
        try{
            SecurityUtils().decrypt(key,file,out)
            Toast.makeText(this,"DECRYPTED FILE",Toast.LENGTH_LONG).show()
        }catch (ex:SecurityException){
            Log.d("ENCRYPTION", ex.message!!)
        }
    }


    fun isReadStoragePermissionGranted(): Boolean {
        return if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED
            ) {
                Log.v(TAG, "Permission is granted1")
                true
            } else {
                Log.v(TAG, "Permission is revoked1")
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),
                    3
                )
                false
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG, "Permission is granted1")
            true
        }
    }

    fun isWriteStoragePermissionGranted(): Boolean {
        return if (Build.VERSION.SDK_INT >= 23) {
            if (checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                == PackageManager.PERMISSION_GRANTED
            ) {
                Log.v(TAG, "Permission is granted2")
                true
            } else {
                Log.v(TAG, "Permission is revoked2")
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
                    2
                )
                false
            }
        } else { //permission is automatically granted on sdk<23 upon installation
            Log.v(TAG, "Permission is granted2")
            true
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            2 -> {
                Log.d(TAG, "External storage2")
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.v(TAG, "Permission: " + permissions[0] + "was " + grantResults[0])
                    //resume tasks needing this permission

                } else {
                    Toast.makeText(this@MainActivity,"You can not proceed without permission", Toast.LENGTH_LONG).show()
                }
            }
            3 -> {
                Log.d(TAG, "External storage1")
                if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Log.v(TAG, "Permission: " + permissions[0] + "was " + grantResults[0])
                    //resume tasks needing this permission

                } else {
                    Toast.makeText(this@MainActivity,"You can not proceed without permission", Toast.LENGTH_LONG).show()
                }
            }
        }
    }


}