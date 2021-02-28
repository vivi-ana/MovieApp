package com.example.movieapplication.core

import kotlinx.coroutines.coroutineScope
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket

object InternetCheck {
    suspend fun isNetworkAwailable() = coroutineScope {
        return@coroutineScope try {
            val sock = Socket()
            val socketAdress = InetSocketAddress("8.8.8.8", 53)
            sock.connect(socketAdress, 2000)
            sock.close()
            true
        }catch (e: IOException){
            false
        }
    }
}