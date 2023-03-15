package com.example.reusablecomponents

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.customToaster.GenericToast
import com.example.recycleViewDecoration.CirclePagerIndicatorDecoration
import com.example.reusablecomponents.databinding.ActivityMainBinding


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        //setupRecyclerView()

        binding.buttonSave.setOnClickListener {
            GenericToast.showToast(this, "Success", "Connection established successfully!",
                GenericToast.LENGTH_SHORT, GenericToast.SUCCESS, GenericToast.LITE,
                GenericToast.DEFAULT_FONT, GenericToast.DEFAULT_FONT,true)
        }

        binding.btnLoad.setOnClickListener {
            GenericToast.showToast(context = this, titleData = "Error",
                duration = GenericToast.LENGTH_SHORT,type = GenericToast.ERROR,mode =  GenericToast.LITE,
                titleFont = GenericToast.DEFAULT_FONT, messageFont = GenericToast.DEFAULT_FONT)
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

