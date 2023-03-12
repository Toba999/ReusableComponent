package com.example.encryptionHelper

import android.content.Context
import androidx.core.content.edit
import androidx.datastore.preferences.core.Preferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey


class DataStoreEncryptor(private val context: Context) {


    private val masterKey = MasterKey.Builder(context, MasterKey.DEFAULT_MASTER_KEY_ALIAS)
        .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
        .build()

    fun <T> encryptAndSaveData(key: Preferences.Key<T>, value: T) {
        val encryptedSharedPreferences = EncryptedSharedPreferences.create(
            context,
            "encrypted_data_store",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

        encryptedSharedPreferences.edit {
            putString(key.toString(), value.toString())
        }
    }

    fun <T> decryptAndReadData(key: Preferences.Key<T>): String? {
        val encryptedSharedPreferences = EncryptedSharedPreferences.create(
            context,
            "encrypted_data_store",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

        return encryptedSharedPreferences.getString(key.toString(), null)
    }
}
