package com.example.codexdictionary.dictionary

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.DiffUtil
import com.example.codexdictionary.databinding.MeaningItemViewBinding
import com.example.codexdictionary.data.DictionaryResult

class DictionaryWordAdapter : ListAdapter<DictionaryResult,DictionaryWordAdapter.MeaningViewHolder>(DiffCallback) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MeaningViewHolder {
        return MeaningViewHolder(MeaningItemViewBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: MeaningViewHolder, position: Int) {
        val dictionaryResult = getItem(position)
        holder.bind(dictionaryResult,position)
    }

    companion object DiffCallback : DiffUtil.ItemCallback<DictionaryResult>(){
        override fun areItemsTheSame(oldItem: DictionaryResult, newItem: DictionaryResult): Boolean {
            return oldItem === newItem
        }

        override fun areContentsTheSame(oldItem: DictionaryResult, newItem: DictionaryResult): Boolean {
            return oldItem.meaning == newItem.meaning
        }
    }

    override fun getItemCount(): Int {
        return super.getItemCount()
    }



    class MeaningViewHolder(private val binding : MeaningItemViewBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(dictionaryResult : DictionaryResult,position: Int){
            binding.result = dictionaryResult
            binding.executePendingBindings()
        }
    }

}