package com.example.reusablecomponents


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.reusablecomponents.databinding.CardItemBinding



class MyAdapter(private val list: List<String>) : RecyclerView.Adapter<MyAdapter.MyView>() {

    class MyView(private var itemBinding: CardItemBinding) :
        RecyclerView.ViewHolder(itemBinding.root) {
        fun bindItem(hero: String) {
            itemBinding.textview.text = hero
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyView {
        val viewItem = CardItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return MyView(viewItem)
    }

    override fun onBindViewHolder(holder: MyView, position: Int) {
        holder.bindItem(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }
}