package com.example.abir.source

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.abir.source.sample.aes_encryption.AESEncryption
import com.example.abir.source.sample.custom_text.CustomTextSampleActivity
import com.example.abir.source.sample.dialog.CardDialog
import com.example.abir.source.sample.file_download_retrofit.FileDownloadActivity
import com.example.abir.source.sample.guided_tutorial.GuideActivity
import com.example.abir.source.sample.jwt_token_example.JWTSample
import com.example.abir.source.sample.property_animation.PropertyAnimationActivity
import com.example.abir.source.sample.save_file_on_shared_storage.DownloadAndSaveFileActivity
import com.example.abir.source.utils.logDebug
import com.example.abir.source.utils.logInfo
import com.example.abir.source.utils.showSnackBarShort
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {

    companion object {
        private const val TAG = "MainActivity"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setButton1()
        setButton2()
        setButton3()
        setButton4()
        setButton5()
        setButton6()
        setButton7()
        setButton8()
    }

    private fun setButton1() {
        button1.setOnClickListener {
            Intent(this, FileDownloadActivity::class.java).apply {
                startActivity(this)
            }
        }
    }

    /**
     *  Exploring TourGuide Library for Showcasing
     *  @link - https://github.com/worker8/TourGuide
     *  Also Check out below which supports custom view and has good API
     *  @link - https://github.com/faruktoptas/FancyShowCaseView
     */
    private fun setButton2() {
        button2.setOnClickListener {
            Intent(this, GuideActivity::class.java).apply {
                startActivity(this)
            }
        }
    }

    private fun setButton3() {
        button3.setOnClickListener {
            Intent(this, PropertyAnimationActivity::class.java).apply {
                startActivity(this)
            }
        }
    }

    private fun setButton4() {
        button4.setOnClickListener {
            Intent(this, CustomTextSampleActivity::class.java).apply {
                startActivity(this)
            }
        }
    }

    private fun setButton5() {
        button5.setOnClickListener {
            val message = "Custom Title Text"
            val buttonText = "Okay"
            val fragment = CardDialog(
                titleText = message,
                buttonText = buttonText,
                cardIcon = R.drawable.ic_smiley_face,
                onCrossClick = null,
                onButtonClick = null
            )
            fragment.isCancelable = false
            fragment.show(supportFragmentManager, "tag")
        }
    }

    /**
     * Check AES Encryption - Basic Implementation
     */
    private fun setButton6() {
        button6.setOnClickListener {
            val encryptedMessage = AESEncryption().encryptMessage("01533337777")
            "onCreate() encrypted message: $encryptedMessage".logDebug(TAG)
            "onCreate() Decrypted message: ${AESEncryption().decryptMessage(encryptedMessage)}"
                .logInfo(TAG)
            Toast.makeText(this, "Check LOG_CAT", Toast.LENGTH_SHORT).show()
        }
    }

    private fun setButton7() {
        button7.setOnClickListener {
            Intent(this, DownloadAndSaveFileActivity::class.java).apply {
                startActivity(this)
            }
        }
    }

    private fun setButton8() {
        button8.setOnClickListener {
            val jwtSampleObject = JWTSample()
            val encryptedMessage = jwtSampleObject.encryptMessage("01533337777")
            "setButton8() JWT encrypted message: $encryptedMessage".logDebug(TAG)
            "setButton8() JWT Decrypted message: ${jwtSampleObject.decryptMessage(encryptedMessage)}"
                .logInfo(TAG)
            showSnackBarShort("Check LOG_CAT: JWT")
        }
    }
}