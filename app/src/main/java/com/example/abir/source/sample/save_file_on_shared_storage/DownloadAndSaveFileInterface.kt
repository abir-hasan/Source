package com.example.abir.source.sample.save_file_on_shared_storage

import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Streaming

interface DownloadAndSaveFileInterface {

    //https://file-examples-com.github.io/uploads/2017/10/file-sample_150kB.pdf

    @GET("/uploads/2017/10/file-sample_150kB.pdf")
    @Streaming
    suspend fun getPdf(): ResponseBody // Make sure the ResponseBody is from okhttp3 not other
}