package com.erol.teknafly.data.util

import android.content.Context
import java.io.InputStream
import java.lang.Exception

class ReadFile(var context: Context) {

    fun readFileFromJson(file:String): String? {
        try {
            val inputStream:InputStream = context.assets.open(file)
            var json = inputStream.bufferedReader().use { it.readText() }
            return json
        }catch (e:Exception){
            println("Hata : ${e.message}")
            return null;
        }
    }
}