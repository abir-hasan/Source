package com.example.abir.source.sample.file_download_retrofit

import android.app.Application
import android.media.MediaScannerConnection
import android.os.Environment
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.abir.source.utils.logDebug
import com.example.abir.source.utils.logError
import com.example.abir.source.utils.logInfo
import com.example.abir.source.utils.logVerbose
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.io.*


class FileDownloadViewModel(private val mApplication: Application) :
    AndroidViewModel(mApplication) {

    companion object {
        const val TAG = "FileDownloadViewModel"
        const val DOWNLOADABLE_FILE_IMAGE = "DOWNLOADABLE_FILE_IMAGE.jpg"
    }

    val downloadProgressLiveData: MutableLiveData<Int> by lazy {
        MutableLiveData<Int>()
    }

    val filePathLiveData: MutableLiveData<File> by lazy {
        MutableLiveData<File>()
    }

    private val imageFile: File by lazy {
        val externalDirectory = mApplication.getExternalFilesDir(Environment.DIRECTORY_DOCUMENTS)
        return@lazy File(externalDirectory, DOWNLOADABLE_FILE_IMAGE)
    }

    private val mRetrofit: Retrofit by lazy {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val builder = OkHttpClient.Builder()
        builder.addInterceptor(interceptor)

        return@lazy Retrofit.Builder()
            .baseUrl("https://cdn.suwalls.com/wallpapers/")
            .client(builder.build())
            .build()
    }

    private val mApiInterface: FileDownloadInterface by lazy {
        mRetrofit.create(FileDownloadInterface::class.java)
    }


    fun downLoadAXmlFile() {
        viewModelScope.launch(Dispatchers.IO) {
            val mBody: ResponseBody = mApiInterface.getImage()
            val isSuccessful = writeResponseBodyToDisk(mBody)
            if (isSuccessful) {
                "File stored successfully".logVerbose(TAG)
                filePathLiveData.postValue(imageFile)
            } else {
                "File wasn't stored".logError(TAG)
            }
        }
    }

    private fun writeResponseBodyToDisk(body: ResponseBody): Boolean {
        try {
            var inputStream: InputStream? = null
            var outputStream: OutputStream? = null
            try {
                val fileReader = ByteArray(8192)
                val fileSize = body.contentLength()
                var fileSizeDownloaded: Long = 0
                inputStream = body.byteStream()
                outputStream = FileOutputStream(imageFile)
                while (true) {
                    val read = inputStream!!.read(fileReader)
                    if (read == -1) {
                        break
                    }
                    outputStream.write(fileReader, 0, read)
                    fileSizeDownloaded += read.toLong()

                    val percent =
                        calculateProgress(fileSize.toDouble(), fileSizeDownloaded.toDouble())
                    "File Writing $fileSizeDownloaded of $fileSize ($percent%)".logInfo(TAG)

                    outputStream.flush()
                }

                registerInMediaServer(imageFile)

                inputStream.close()
                outputStream.close()
                return true
            } catch (e: Exception) {
                e.printStackTrace()
                inputStream?.close()
                outputStream?.close()
                return false
            }
        } catch (e: Exception) {
            e.printStackTrace()
            return false
        }
    }

    private fun readTheFile() {
        val text = StringBuilder()
        try {
            val br = BufferedReader(FileReader(imageFile))
            var line: String? = ""
            while (br.readLine().also { line = it } != null) {
                text.append(line)
                text.append('\n')
            }
            br.close()
            "readTheFile() $text".logVerbose(TAG)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    private fun calculateProgress(totalSize: Double, downloadSize: Double): Double {
        val progress = ((downloadSize / totalSize) * 100)
        downloadProgressLiveData.postValue(progress.toInt())
        return progress
    }

    private fun registerInMediaServer(file: File) {
        MediaScannerConnection.scanFile(mApplication, arrayOf(file.toString()), null,
            MediaScannerConnection.OnScanCompletedListener { path, uri ->
                "ExternalStorage Scanned $path:".logInfo(TAG)
                "ExternalStorage -> uri=$uri".logDebug(TAG)
            })
    }

}