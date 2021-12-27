package com.main

import com.mashape.unirest.http.HttpResponse
import com.mashape.unirest.http.Unirest
import okhttp3.*
import java.util.concurrent.TimeUnit


class getAll {
    private val client: OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(10, TimeUnit.SECONDS)
        .writeTimeout(10, TimeUnit.SECONDS)
        .readTimeout(10, TimeUnit.SECONDS)
        .build()

    fun get(): Double {
        val arrayList: ArrayList<String>
        val arrayList1: ArrayList<String> = ArrayList()

        val request = Request.Builder()
            .url("https://www.binance.com/api/v3/klines?limit=35&symbol=BTCUSDT&interval=15m")
            .build()
        client.newCall(request).execute().use { response ->

            arrayList = response.body()?.string()?.split("],[") as ArrayList<String>
        }
        for (i in arrayList) {
            arrayList1.add(i.split(",")[2].replace("\"", "") + "," + i.split(",")[3].replace("\"", ""))
        }
        var sum5: Double = 0.0
        var sum34: Double = 0.0
        arrayList1.removeLast()

        for ((i, j) in arrayList1.reversed().withIndex()) {

            if (i <= 4) {

                sum5 += (j.split(",")[0].toDouble() + j.split(",")[1].toDouble()) / 2
            }
            sum34 += (j.split(",")[0].toDouble() + j.split(",")[1].toDouble()) / 2


        }
        sum5 /= 5
        sum34 /= 34
//        println(sum5)
//        println(sum34)
//        println(sum5 - sum34)
        return sum5 - sum34
    }

    fun sendMessage(message: String, userID: String) {
        Unirest.setTimeouts(0, 0)
        val response: HttpResponse<String> =
            Unirest.post("https://api.telegram.org/bot5037121354:AAFXaT3XjpHvklpntF7gghsmsKrUjG-RoBQ/sendMessage")
                .field("chat_id", userID)
                .field("text", message)
                .asString()


    }


}