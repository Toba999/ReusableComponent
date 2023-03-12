package com.example.reusablecomponents

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.encryptionHelper.DataStoreEncryptor
import com.example.recycleViewDecoration.CirclePagerIndicatorDecoration
import com.example.reusablecomponents.databinding.ActivityMainBinding
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setupRecyclerView()

        val dataStoreEncryptor = DataStoreEncryptor(this)

        val nameKey = stringPreferencesKey("name_key")


        // launch a coroutine to encrypt and save data
        lifecycleScope.launch {
            val valueToSave = "Hello World"
            dataStoreEncryptor.encryptAndSaveData(nameKey, valueToSave)
        }

        // launch a coroutine to decrypt and read data
        lifecycleScope.launch {
            val retrievedValue = dataStoreEncryptor.decryptAndReadData(nameKey)
            println(retrievedValue) // Output: Hello World
        }


    }

    private fun setupRecyclerView() {
        binding.recyclerview.apply {
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