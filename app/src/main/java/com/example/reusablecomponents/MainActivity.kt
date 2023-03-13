package com.example.reusablecomponents

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.encryptionHelper.CryptoManager
import com.example.encryptionHelper.DataStoreEncryptor
import com.example.recycleViewDecoration.CirclePagerIndicatorDecoration
import com.example.reusablecomponents.databinding.ActivityMainBinding
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        //setupRecyclerView()

        // Create an instance of DataStoreEncryptor
        val dataStoreEncryptor = DataStoreEncryptor(this)

        // Encrypt and save a string value with key "my_string_key"
        dataStoreEncryptor.encryptAndSavePrimitiveData("my_string_key", "Hello, world!")

        // Encrypt and save an integer value with key "my_int_key"
        dataStoreEncryptor.encryptAndSavePrimitiveData("my_int_key", 42)

        // Encrypt and save a boolean value with key "my_bool_key"
        dataStoreEncryptor.encryptAndSavePrimitiveData("my_bool_key", true)

        // Encrypt and save a float value with key "my_float_key"
        dataStoreEncryptor.encryptAndSavePrimitiveData("my_float_key", 3.14f)

        // Decrypt and read the string value with key "my_string_key"
        val myString = dataStoreEncryptor.decryptAndReadPrimitiveData<String>("my_string_key")
        Log.d("MainActivity", "myString: $myString") // Output: "myString: Hello, world!"

        // Decrypt and read the integer value with key "my_int_key"
        val myInt = dataStoreEncryptor.decryptAndReadPrimitiveData<Int>("my_int_key")
        Log.d("MainActivity", "myInt: $myInt") // Output: "myInt: 42"

        // Decrypt and read the boolean value with key "my_bool_key"
        val myBool = dataStoreEncryptor.decryptAndReadPrimitiveData<Boolean>("my_bool_key")
        Log.d("MainActivity", "myBool: $myBool") // Output: "myBool: true"

        // Decrypt and read the float value with key "my_float_key"
        val myFloat = dataStoreEncryptor.decryptAndReadPrimitiveData<Float>("my_float_key")
        Log.d("MainActivity", "myFloat: $myFloat") // Output: "myFloat: 3.14"

        val nameKey = stringPreferencesKey("nameee_key")
        val user = User("Alice", 30)
        //dataStoreEncryptor.encryptAndSaveObject(nameKey, user)

        // Decrypt and read the User object with key "user_data"
        val decryptedUser = dataStoreEncryptor.decryptAndReadObject<User>(nameKey)
        Log.d("MainActivity", "decryptedUser: $decryptedUser")

        val cryptoManager = CryptoManager()

        binding.buttonSave.setOnClickListener {

            val bytes = binding.editText.text.toString().encodeToByteArray()
            val file = File(filesDir, "secret.txt")
            if(!file.exists()) {
                file.createNewFile()
            }
            val fos = FileOutputStream(file)

            val messageToDecrypt = cryptoManager.encrypt(
                bytes = bytes,
                outputStream = fos
            ).decodeToString()
            Toast.makeText(this, "Value saved successfully : $messageToDecrypt", Toast.LENGTH_SHORT).show()
        }

        binding.btnLoad.setOnClickListener {
            val filedecrypted = File(filesDir, "secret.txt")
            val messageToEncrypt = cryptoManager.decrypt(
                inputStream = FileInputStream(filedecrypted)
            ).decodeToString()
            Toast.makeText(this@MainActivity, "Value loaded: $messageToEncrypt", Toast.LENGTH_SHORT).show()

        }
    }




    private fun setupRecyclerView() {
        binding.recyclerview.apply {
            this.visibility = View.VISIBLE
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = MyAdapter(createHeroList())
            addItemDecoration(CirclePagerIndicatorDecoration
                (R.color.black,
                R.color.teal_200,
                CirclePagerIndicatorDecoration.DecorationPosition.BottomMiddle,
                CirclePagerIndicatorDecoration.DecorationSize.Large
            )
            )
        }
    }

    private fun createHeroList(): ArrayList<String> {
        return arrayListOf("Daniel Craig","Dwayne Johnson","Tom Hiddlestone", "Tony Stark","Daniel Craig","Dwayne Johnson","Tom Hiddlestone", "Tony Stark","Daniel Craig","Daniel Craig")
       //return arrayListOf("Daniel Craig","Dwayne Johnson","Tom Hiddlestone")

    }

}

data class User(val name: String, val age: Int)
