package com.example.reusablecomponents

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.encryptionHelper.DataStoreEncryptor
import com.example.recycleViewDecoration.CirclePagerIndicatorDecoration
import com.example.reusablecomponents.databinding.ActivityMainBinding
import com.google.gson.Gson
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        //setupRecyclerView()

        val dataStoreEncryptor = DataStoreEncryptor(this)
        val user = User("Alice", 25)
        val nameKey = stringPreferencesKey("name_key")

        binding.buttonSave.setOnClickListener {
            val value = binding.editText.text.toString()
            lifecycleScope.launch {
                dataStoreEncryptor.encryptAndSaveData(nameKey,value)
            }
            Toast.makeText(this, "Value saved successfully!", Toast.LENGTH_SHORT).show()
        }

        binding.btnLoad.setOnClickListener {
            lifecycleScope.launch {
                val retrievedValue = dataStoreEncryptor.decryptAndReadData(nameKey)
                Toast.makeText(this@MainActivity, "Value loaded: $retrievedValue", Toast.LENGTH_SHORT).show()
                binding.textView.text = retrievedValue
                println(retrievedValue) // Output: Hello World
            }
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
