package com.example.reusablecomponents

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.MyApp
import com.example.localizationHelper.LanguageCodes
import com.example.localizationHelper.LocaleHelper
import com.example.recycleViewDecoration.CirclePagerIndicatorDecoration
import com.example.reusablecomponents.databinding.ActivityMainBinding
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        setupRecyclerView()
        var locale = Locale.getDefault()
        Log.d("MyApp", "Current Locale: ${locale.language}_${locale.country}")
        Log.d("MyApp", "Current Locale: ${LocaleHelper.localizeNumber(123.44)}")
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