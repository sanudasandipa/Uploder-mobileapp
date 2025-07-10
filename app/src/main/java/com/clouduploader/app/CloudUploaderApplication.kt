package com.clouduploader.app

import android.app.Application

class CloudUploaderApplication : Application() {
    
    companion object {
        // Replace with your actual server URL
        const val BASE_URL = "http://140.245.238.153/"
        const val API_BASE_URL = "${BASE_URL}api/"
    }
    
    override fun onCreate() {
        super.onCreate()
    }
}
