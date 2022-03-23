package com.example.abir.source.sample.work_manager_sample.workers

import android.content.Context
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.abir.source.sample.work_manager_sample.OUTPUT_PATH
import com.example.abir.source.utils.logVerbose
import java.io.File

class CleanupWorker(context: Context, params: WorkerParameters) : Worker(context, params) {

    companion object{
        private const val TAG = "CleanupWorker"
    }

    override fun doWork(): Result {
        // Makes a notification when the work starts and slows down the work so that
        // it's easier to see each WorkRequest start, even on emulated devices
        makeStatusNotification("Cleaning up old temporary files", applicationContext)
        sleep()

        return try {
            val outputDirectory = File(applicationContext.filesDir, OUTPUT_PATH)
            if (outputDirectory.exists()) {
                val entries = outputDirectory.listFiles()
                if (entries != null) {
                    for (entry in entries) {
                        val name = entry.name
                        if (name.isNotEmpty() && name.endsWith(".png")) {
                            val deleted = entry.delete()
                            "Deleted $name - $deleted".logVerbose(TAG)
                        }
                    }
                }
            }
            makeStatusNotification("Clean up complete!", applicationContext)
            Result.success()
        } catch (exception: Exception) {
            exception.printStackTrace()
            Result.failure()
        }
    }

}