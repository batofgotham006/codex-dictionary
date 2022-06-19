package com.example.codexdictionary.dictionary

import android.annotation.SuppressLint
import android.widget.TextView
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.codexdictionary.data.DictionaryResult

@SuppressLint("NotifyDataSetChanged")
@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView,data : List<DictionaryResult>?){
    val adapter = recyclerView.adapter as DictionaryWordAdapter
    adapter.submitList(data)
    adapter.notifyDataSetChanged()
}

@BindingAdapter("setMeaning")
fun TextView.setMeaning(item :DictionaryResult?){
    item?.let{
        text = item.meaning
    }
}

@BindingAdapter("setExample")
fun TextView.setExample(item :DictionaryResult?){
    item?.let{
        text = item.example
    }
}