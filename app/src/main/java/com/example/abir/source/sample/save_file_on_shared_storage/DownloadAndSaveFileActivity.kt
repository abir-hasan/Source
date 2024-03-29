package com.example.abir.source.sample.save_file_on_shared_storage

import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.DocumentsContract
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.abir.source.R
import com.example.abir.source.databinding.ActivityDownloadAndSaveFileBinding
import com.example.abir.source.utils.logDebug
import com.example.abir.source.utils.model.APIResponse
import com.example.abir.source.utils.model.Status

class DownloadAndSaveFileActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "DownloadAndSaveFileActi"
    }

    private lateinit var binding: ActivityDownloadAndSaveFileBinding

    private val mViewModel: DownloadAndSaveFileViewModel by lazy {
        ViewModelProvider(this).get(DownloadAndSaveFileViewModel::class.java)
    }

    private val fileUriObserver = Observer<APIResponse<Uri>> { onFileUriResponse(it) }

    private var filePathUri: Uri? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDownloadAndSaveFileBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
        setupObservers()
        // TODO - Take Runtime Permission
        //  for API 21-28 @see https://developer.android.com/training/data-storage
        getPdfFile()
    }

    private fun initView() {
        binding.btnOpenFile.setOnClickListener {
            if (filePathUri == null) {
                Toast.makeText(this, getString(R.string.no_uri), Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            openFile(filePathUri!!)
        }
    }

    private fun openFile(filePathUri: Uri) {
        "openFileForAndroidTenAndBelow() called with: filePathUri = $filePathUri".logDebug(TAG)
        Intent(Intent.ACTION_VIEW).apply {
            setDataAndType(
                filePathUri,
                DownloadAndSaveFileViewModel.DOWNLOADABLE_FILE_MIME_TYPE_PDF
            )
            setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
            startActivity(this)
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun openFileLocation(filePathUri: Uri) {
        "openFileForAndroidElevenAndAbove() called with: filePathUri = $filePathUri".logDebug(TAG)
        Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            setType(DownloadAndSaveFileViewModel.DOWNLOADABLE_FILE_MIME_TYPE_PDF)
            putExtra(DocumentsContract.EXTRA_INITIAL_URI, filePathUri)
            setFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(this)
        }
    }

    private fun setupObservers() {
        mViewModel.fileUriLiveData.observe(this, fileUriObserver)
    }

    private fun getPdfFile() {
        mViewModel.downloadPdfFile()
    }

    private fun onFileUriResponse(response: APIResponse<Uri>) {
        when (response.status) {
            Status.SUCCESS -> {
                binding.btnOpenFile.visibility = View.VISIBLE
                filePathUri = response.data
                Toast.makeText(this, getString(R.string.success), Toast.LENGTH_SHORT).show()
            }
            Status.ERROR -> {
                Toast.makeText(
                    this, getString(R.string.something_went_wrong), Toast.LENGTH_SHORT
                ).show()
            }
            Status.LOADING -> {
                Toast.makeText(
                    this, getString(R.string.downloading),
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
}