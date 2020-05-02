package com.example.abir.source.sample.file_download_retrofit

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Streaming

interface FileDownloadInterface {

    @GET("nature/lonesome-autumn-tree-losing-its-leaves-on-the-field-54569-2560x1600.jpg")
    @Streaming
    suspend fun getImage(): ResponseBody // Make sure the ResponseBody is from okhttp3 not other
}