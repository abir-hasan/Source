package com.example.abir.source.sample.work_manager_sample.workers

import android.content.Context
import android.graphics.BitmapFactory
import android.net.Uri
import android.text.TextUtils
import androidx.work.Worker
import androidx.work.WorkerParameters
import androidx.work.workDataOf
import com.example.abir.source.sample.work_manager_sample.KEY_IMAGE_URI
import com.example.abir.source.utils.logError

class BlurWorker(context: Context, params: WorkerParameters) : Worker(context, params) {

    companion object {
        private const val TAG = "BlurWorker"
    }

    override fun doWork(): Result {
        val appContext = applicationContext

        val resourceUri = inputData.getString(KEY_IMAGE_URI)

        makeStatusNotification("Blurring image", appContext)

        // ADD THIS TO SLOW DOWN THE WORKER
        sleep()
        // ^^^^

        return try {
            // REMOVE THIS
            //    val picture = BitmapFactory.decodeResource(
            //            appContext.resources,
            //            R.drawable.android_cupcake)

            if (TextUtils.isEmpty(resourceUri)) {
                "Invalid input uri".logError(TAG)
                throw IllegalArgumentException("Invalid input uri")
            }

            val resolver = appContext.contentResolver

            val picture = BitmapFactory.decodeStream(
                resolver.openInputStream(Uri.parse(resourceUri))
            )

            val output = blurBitmap(picture, appContext)

            // Write bitmap to a temp file
            val outputUri = writeBitmapToFile(appContext, output)

            makeStatusNotification("Output is $outputUri", appContext)

            val workOutput = workDataOf(KEY_IMAGE_URI to outputUri.toString())

            Result.success(workOutput)
        } catch (throwable: Throwable) {
            "doWork() Error applying blur".logError(TAG)
            Result.failure()
        }
    }

}