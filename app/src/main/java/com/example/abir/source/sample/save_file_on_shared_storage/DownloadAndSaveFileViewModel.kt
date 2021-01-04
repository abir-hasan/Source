package com.example.abir.source.sample.save_file_on_shared_storage

import android.app.Application
import android.content.ContentUris
import android.content.ContentValues
import android.database.Cursor
import android.net.Uri
import android.os.Build
import android.os.Environment
import android.provider.MediaStore
import androidx.core.content.FileProvider
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.abir.source.utils.logDebug
import com.example.abir.source.utils.logError
import com.example.abir.source.utils.logInfo
import com.example.abir.source.utils.logVerbose
import com.example.abir.source.utils.model.APIResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.ResponseBody
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.io.File
import java.io.OutputStream


class DownloadAndSaveFileViewModel(val mApplication: Application) : AndroidViewModel(mApplication) {

    companion object {
        const val TAG = "DownloadAndSaveFileVM"
        const val DOWNLOADABLE_FILE_NAME_PDF = "test_pdf_file.pdf"
        const val DOWNLOADABLE_FILE_MIME_TYPE_PDF = "application/pdf"
    }

    val fileUriLiveData: MutableLiveData<APIResponse<Uri>> by lazy {
        MutableLiveData<APIResponse<Uri>>()
    }


    private val mRetrofit: Retrofit by lazy {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.HEADERS

        val builder = OkHttpClient.Builder()
        builder.addInterceptor(interceptor)

        return@lazy Retrofit.Builder()
            .baseUrl("https://file-examples-com.github.io")
            .client(builder.build())
            .build()
    }

    private val mApiInterface: DownloadAndSaveFileInterface by lazy {
        mRetrofit.create(DownloadAndSaveFileInterface::class.java)
    }


    fun downloadPdfFile() {
        "downloadPdfFile() called".logDebug(TAG)
        fileUriLiveData.postValue(APIResponse.loading(null))
        viewModelScope.launch(Dispatchers.IO) {
            val mBody: ResponseBody = mApiInterface.getPdf()
            writeFileToPublicExternalStorage(mBody)
        }
    }

    /**
     * As [Environment.getExternalStoragePublicDirectory] is deprecated from Android Q/10
     * But we are setting 'requestLegacyExternalStorage' to true in manifest to make Android Q/10
     * work similar to Android Pie/9 or below
     * And for Android R/11 and above we are taking a different approach to save file
     */
    private suspend fun writeFileToPublicExternalStorage(mBody: ResponseBody) =
        withContext(Dispatchers.IO) {
            try {
                val uri: Uri
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                    val resolver = mApplication.contentResolver
                    val contentValues = ContentValues().apply {
                        put(MediaStore.MediaColumns.DISPLAY_NAME, DOWNLOADABLE_FILE_NAME_PDF)
                        put(MediaStore.MediaColumns.MIME_TYPE, DOWNLOADABLE_FILE_MIME_TYPE_PDF)
                        //put(MediaStore.MediaColumns.RELATIVE_PATH, "DCIM/Folder-1")
                    }

                    uri = resolver.insert(
                        MediaStore.Files.getContentUri("external"),
                        contentValues
                    )!!

                } else {
                    val file = File(
                        Environment.getExternalStoragePublicDirectory(
                            Environment.DIRECTORY_DOWNLOADS
                        ), DOWNLOADABLE_FILE_NAME_PDF
                    )
                    uri = FileProvider.getUriForFile(
                        mApplication,
                        mApplication.applicationContext.packageName + ".provider",
                        file
                    )
                }

                val outputStream: OutputStream = mApplication.contentResolver.openOutputStream(
                    uri
                )!!
                outputStream.write(mBody.bytes())
                outputStream.close()
                fileUriLiveData.postValue(APIResponse.success(uri))
                "writeFileToPublicExternalStorage() File Saved on External Storage: $uri".logInfo(
                    TAG
                )
            } catch (e: Exception) {
                fileUriLiveData.postValue(APIResponse.error(null, ""))
                e.printStackTrace()
            }
        }


    fun checkIfPdfFileExist(): Boolean {
        val contentUri = MediaStore.Files.getContentUri("external")

        // val selection = MediaStore.MediaColumns.RELATIVE_PATH + "=?"

        // val selectionArgs = arrayOf("DCIM/Folder-1")

        val cursor: Cursor? =
            mApplication.contentResolver.query(
                contentUri, null, null, null, null
            )
        cursor?.let {
            if (it.count == 0) return false
            var uri: Uri? = null
            while (it.moveToNext()) {
                val fileName = it.getString(it.getColumnIndex(MediaStore.MediaColumns.DISPLAY_NAME))
                "checkIfPdfFileExist() fileName: $fileName".logVerbose(TAG)
                if (fileName == DOWNLOADABLE_FILE_NAME_PDF) {
                    val id = cursor.getLong(it.getColumnIndex(MediaStore.MediaColumns._ID))
                    uri = ContentUris.withAppendedId(contentUri, id)
                    break
                }
            }
            it.close()
            return uri != null
        } ?: run {
            "checkIfPdfFileExist() Cursor Null".logError(TAG)
            return false
        }
    }
}