package com.sample.downloadfile

import android.media.MediaCodec.CryptoException
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec
import kotlin.jvm.Throws


class SecurityUtils{

    //define algorithm (AES) and Transformation
    private val algorithm:String = "AES"
    private val transformation: String = "AES"

    //encryption function
    @Throws(SecurityException::class)
    fun encrypt(key: String, inputFile: File, outputFile: File){
        encryption(Cipher.ENCRYPT_MODE, key, inputFile, outputFile)
    }

    //decryption function
    @Throws(SecurityException::class)
    fun decrypt(key: String, inputFile: File, outputFile: File){
        encryption(Cipher.DECRYPT_MODE, key, inputFile, outputFile)
    }

    //function that encrypts/decrypts
    private fun encryption(cipherMode: Int, key: String, inputFile: File, outputFile: File){

        try{

            //create key object
            val secretKey = SecretKeySpec(key.toByteArray(), algorithm)
            val cipher = Cipher.getInstance(transformation)
            //initialize cipher
            cipher.init(cipherMode, secretKey)

            //create input stream to read file
            val inputStream:FileInputStream = FileInputStream(inputFile)
            val inputBytes = ByteArray(inputFile.length().toInt())
            inputStream.read(inputBytes)

            //create output file bytes
            val outputBytes = cipher.doFinal(inputBytes)

            //create output streams
            val outputStream = FileOutputStream(outputFile)
            outputStream.write(outputBytes)

            //close all streams
            inputStream.close()
            outputStream.close()

        }catch (ex: Exception){

            throw SecurityException("Error encrypting/decrypting", ex)

        }

    }


}