package com.example.contacts.data

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL

class Client {
    suspend fun getContact() : Deferred<Contact?> {
        return GlobalScope.async {
            var contact : Contact? = null
            val retrofit = Retrofit.Builder()
                .baseUrl("https://randomuser.me/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            val api : RandomUserAPI = retrofit.create(RandomUserAPI::class.java)
            val call = api.getContacts()
            val user = call.execute()
            Log.e("EXEC", user.body().toString())
            if(user.code() != 200)
                Log.e("FAIL", user.code().toString())
            else contact = user.body()
            Log.e("CLIENT0", contact.toString())
            contact
        }
    }

    fun getBitmap(path : String) : Deferred<Bitmap?> {
        return GlobalScope.async {
            var input : InputStream? = null
            var bmp : Bitmap? = null
            try {
                val url = URL(path)
                val conn : HttpURLConnection = url.openConnection() as HttpURLConnection
                conn.doInput = true
                conn.connect()
                val responseCode = conn.responseCode
                if(responseCode == HttpURLConnection.HTTP_OK){
                    input = conn.inputStream
                    bmp = BitmapFactory.decodeStream(input)
                    input.close()
                }
            }catch (e : Exception){
                Log.e("EXCEPTION", e.printStackTrace().toString())
            }
            bmp
        }
    }
}