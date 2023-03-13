package com.example.encryptionHelper

import android.content.Context
import androidx.core.content.edit
import androidx.datastore.preferences.core.Preferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken


class DataStoreEncryptor(val context: Context) {


     val gson = Gson()

     val masterKey = MasterKey.Builder(context, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    inline fun <reified T> encryptAndSaveData(key: Preferences.Key<T>, value: T) {
        val encryptedSharedPreferences = EncryptedSharedPreferences.create(
            context,
            "encrypted_data_store",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
        val json = gson.toJson(value)
        encryptedSharedPreferences.edit {
            putString(key.toString(), json)
        }
    }

    inline fun <reified T> decryptAndReadData(key: Preferences.Key<T>): T? {
        val encryptedSharedPreferences = EncryptedSharedPreferences.create(
            context,
            "encrypted_data_store",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

        val json = encryptedSharedPreferences.getString(key.toString(), null)
        return gson.fromJson(json, object : TypeToken<T>() {}.type)
    }
}
