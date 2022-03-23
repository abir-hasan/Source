package com.example.abir.source.sample.work_manager_sample.workers

import android.content.Context
import android.graphics.BitmapFactory
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.abir.source.R
import com.example.abir.source.utils.logError

class BlurWorker(context: Context, params: WorkerParameters) : Worker(context, params) {

    companion object{
        private const val TAG = "BlurWorker"
    }

    override fun doWork(): Result {
        val appContext = applicationContext

        makeStatusNotification("Blurring image", appContext)

        return try {
            val picture = BitmapFactory.decodeResource(
                appContext.resources,
                R.drawable.android_cupcake)

            val output = blurBitmap(picture, appContext)

            // Write bitmap to a temp file
            val outputUri = writeBitmapToFile(appContext, output)

            makeStatusNotification("Output is $outputUri", appContext)

            Result.success()
        } catch (throwable: Throwable) {
            "doWork() Error applying blur".logError(TAG)
            Result.failure()
        }
    }

}