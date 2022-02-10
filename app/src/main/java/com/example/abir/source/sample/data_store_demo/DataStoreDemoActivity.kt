package com.example.abir.source.sample.data_store_demo

import android.os.Bundle
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.lifecycleScope
import com.example.abir.source.BaseActivity
import com.example.abir.source.databinding.ActivityDataStoreDemoBinding
import com.example.abir.source.utils.logDebug
import com.example.abir.source.utils.logInfo
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class DataStoreDemoActivity : BaseActivity() {

    companion object {
        private const val TAG = "DataStoreDemoActivity"
    }

    private lateinit var binding: ActivityDataStoreDemoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDataStoreDemoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnSaveKeyValue.setOnClickListener {
            val keyText = binding.etSaveKey.text.toString()
            val valueText = binding.etSaveValue.text.toString()
            lifecycleScope.launch {
                saveKeyValue(keyText, valueText)
            }
        }

        binding.btnReadValue.setOnClickListener {
            val keyText = binding.etReadKey.text.toString()
            lifecycleScope.launch {
                val valueText = readKeyValue(keyText)
                binding.tvReadValue.text = valueText ?: "No value for $keyText exists!"
            }
        }
    }

    private suspend fun saveKeyValue(key: String, value: String) {
        "saveKeyValue() called with: key = $key, value = $value".logDebug(TAG)
        val prefKey = stringPreferencesKey(key)
        demoPrefStore.edit { store -> store[prefKey] = value }
    }

    private suspend fun readKeyValue(key: String): String? {
        "readKeyValue() called with: key = $key".logInfo(TAG)
        val prefKey = stringPreferencesKey(key)
        val preferences = demoPrefStore.data.first()
        return preferences[prefKey]
    }
}