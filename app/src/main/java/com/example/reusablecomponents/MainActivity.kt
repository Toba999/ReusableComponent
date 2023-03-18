package com.example.reusablecomponents

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.customToaster.GenericToast
import com.example.customToaster.Length
import com.example.customToaster.Mode
import com.example.customToaster.Type
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

        binding.btnSuccess.setOnClickListener {
            GenericToast.showToast(this, "Success", "Connection established successfully!",
                Length.LENGTH_SHORT, Type.SUCCESS, Mode.LITE,
                GenericToast.DEFAULT_FONT, GenericToast.DEFAULT_FONT,true)
        }

        binding.btnError.setOnClickListener {
            GenericToast.showToast(context = this, titleData = "Error","Connection error!",
                duration = Length.LENGTH_SHORT,type = Type.ERROR,mode = Mode.LITE,
                titleFont = GenericToast.DEFAULT_FONT, messageFont = GenericToast.DEFAULT_FONT)
        }


        binding.btnInfo.setOnClickListener {
            GenericToast.showToast(this, "Info", "Connection established successfully!",
                Length.LENGTH_SHORT, Type.INFO, Mode.LITE, GenericToast.DEFAULT_FONT,
                GenericToast.DEFAULT_FONT,true)
        }


        binding.btnWarning.setOnClickListener {
            GenericToast.showToast(this, "Warning", "Connection established successfully!",
                Length.LENGTH_SHORT, Type.WARNING, Mode.LITE,
                GenericToast.DEFAULT_FONT, GenericToast.DEFAULT_FONT,true)
        }


        binding.btnCustom.setOnClickListener {
            GenericToast.showToast(this, "CUSTOM", "Connection established successfully!",
                Length.LENGTH_SHORT, Type.CUSTOM, Mode.LITE,
                GenericToast.DEFAULT_FONT, GenericToast.DEFAULT_FONT,true)
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

