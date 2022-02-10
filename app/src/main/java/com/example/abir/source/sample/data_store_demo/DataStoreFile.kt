package com.example.abir.source.sample.data_store_demo

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore


// Use the property delegate created by preferencesDataStore to create an instance
// of Datastore<Preferences>. Call it once at the top level of your kotlin file,
// and access it through this property throughout the rest of your application.
// This makes it easier to keep your DataStore as a singleton
val Context.demoPrefStore: DataStore<Preferences> by preferencesDataStore(name = "demo_pref_store")