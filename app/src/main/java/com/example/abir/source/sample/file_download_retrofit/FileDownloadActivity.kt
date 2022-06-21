package com.example.abir.source.sample.file_download_retrofit

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.abir.source.R
import com.example.abir.source.databinding.ActivityFileDownloadBinding
import com.example.abir.source.utils.logDebug
import com.squareup.picasso.Picasso
import java.io.File

class FileDownloadActivity : AppCompatActivity() {

    companion object {
        const val TAG = "FileDownloadActivity"
    }

    private lateinit var binding: ActivityFileDownloadBinding

    private val mViewModel: FileDownloadViewModel by lazy {
        ViewModelProvider(this).get(FileDownloadViewModel::class.java)
    }

    private val downloadProgressObserver = Observer<Int> { onProgress(it) }

    private val imageFileObserver = Observer<File> { onImageStoreSuccess(it) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFileDownloadBinding.inflate(layoutInflater)
        setContentView(binding.root)
        mViewModel.downloadProgressLiveData.observe(this, downloadProgressObserver)
        mViewModel.filePathLiveData.observe(this, imageFileObserver)
        mViewModel.downLoadAXmlFile()
    }

    private fun onImageStoreSuccess(it: File) {
        Picasso.get().load(it).placeholder(R.drawable.ic_launcher_background)
            .into(binding.ivDownloadedImage)
        binding.tvStatus.text = "Image loaded"
    }

    private fun onProgress(it: Int) {
        "onProgress() called $it".logDebug(TAG)
        if (it < 100) {
            binding.tvStatus.text = "Storing the image... Completion($it%)"
        } else {
            binding.tvStatus.text = "Stored Successfully"
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            binding.progressDownload.setProgress(it, true)
        } else {
            binding.progressDownload.setProgress(it)
        }
    }
}