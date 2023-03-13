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

    inline fun <reified T : Any> encryptAndSavePrimitiveData(key: String, value: T) {
        val encryptedSharedPreferences = EncryptedSharedPreferences.create(
            context,
            "encrypted_data_store",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

        encryptedSharedPreferences.edit {
            when (value) {
                is String -> putString(key, value)
                is Int -> putInt(key, value)
                is Boolean -> putBoolean(key, value)
                is Float -> putFloat(key, value)
                else -> throw IllegalArgumentException("Type not supported")
            }
        }
    }

    inline fun <reified T : Any> decryptAndReadPrimitiveData(key: String): T? {
        val encryptedSharedPreferences = EncryptedSharedPreferences.create(
            context,
            "encrypted_data_store",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )

        return when (T::class) {
            String::class -> encryptedSharedPreferences.getString(key, null) as T?
            Int::class -> encryptedSharedPreferences.getInt(key, 0) as T?
            Boolean::class -> encryptedSharedPreferences.getBoolean(key, false) as T?
            Float::class -> encryptedSharedPreferences.getFloat(key, 0f) as T?
            else -> throw IllegalArgumentException("Type not supported")
        }
    }

    /**
     * Encrypts and saves an object of type T with the given key.
     */
    inline fun <reified T : Any> encryptAndSaveObject(key: Preferences.Key<String>, obj: T) {
        val encryptedSharedPreferences = EncryptedSharedPreferences.create(
            context,
            "encrypted_data_store",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
        val json = gson.toJson(obj)
        encryptedSharedPreferences.edit {
            putString(key.toString(), json)
        }
    }

    /**
     * Decrypts and reads an object of type T with the given key.
     * Returns null if the object is not found or cannot be decrypted.
     */
    inline fun <reified T : Any> decryptAndReadObject(key: Preferences.Key<String>): T? {
        val encryptedSharedPreferences = EncryptedSharedPreferences.create(
            context,
            "encrypted_data_store",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
        val json = encryptedSharedPreferences.getString(key.toString(), null)
        return if (json != null) {
            gson.fromJson(json, object : TypeToken<T>() {}.type)
        } else {
            null
        }
    }
}