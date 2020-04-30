package com.example.abir.source.sample.file_download_retrofit

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.abir.source.R
import com.example.abir.source.utils.logDebug
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_file_download.*
import java.io.File

class FileDownloadActivity : AppCompatActivity() {

    companion object {
        const val TAG = "FileDownloadActivity"
    }

    private val mViewModel: FileDownloadViewModel by lazy {
        ViewModelProvider(this).get(FileDownloadViewModel::class.java)
    }

    private val downloadProgressObserver = Observer<Int> { onProgress(it) }

    private val imageFileObserver = Observer<File> { onImageStoreSuccess(it) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file_download)
        mViewModel.downloadProgressLiveData.observe(this, downloadProgressObserver)
        mViewModel.filePathLiveData.observe(this, imageFileObserver)
        mViewModel.downLoadAXmlFile()
    }

    private fun onImageStoreSuccess(it: File) {
        Picasso.get().load(it).into(ivDownloadedImage)
    }

    private fun onProgress(it: Int) {
        "onProgress() called $it".logDebug(TAG)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            progressDownload.setProgress(it, true)
        } else {
            progressDownload.setProgress(it)
        }
    }
}