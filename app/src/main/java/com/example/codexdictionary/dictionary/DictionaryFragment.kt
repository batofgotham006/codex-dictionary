package com.example.codexdictionary.dictionary

import android.os.Bundle
import android.view.KeyEvent
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.codexdictionary.R
import com.example.codexdictionary.databinding.FragmentDictionaryBinding


class DictionaryFragment : Fragment() {

    private lateinit var viewModel : DictionaryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding : FragmentDictionaryBinding = DataBindingUtil.inflate(inflater,R.layout.fragment_dictionary,container,false)

        viewModel = ViewModelProvider(this).get(DictionaryViewModel::class.java)
        binding.setLifecycleOwner(this)
        binding.viewModel = viewModel

        //Setting the adapter to the DictionaryFragment's RecyclerView
        val adapter = DictionaryWordAdapter()
        binding.resultList.adapter = adapter


        viewModel.buttonClicked.observe(viewLifecycleOwner, Observer{
            if(it){
                val queriedWord = binding.searchEditText.text.toString()
                viewModel.onClick(queriedWord)
                binding.searchEditText.text.clear()
                viewModel.onFindButtonClickComplete()
            }
        })

        //Code to consider Enter key press as Button click
        binding.searchEditText.setOnKeyListener{_,keyCode,keyEvent->
            if(keyEvent.action == KeyEvent.ACTION_DOWN && keyCode == KeyEvent.KEYCODE_ENTER){
                val queriedWord = binding.searchEditText.text.toString()
                viewModel.onClick(queriedWord)
                binding.searchEditText.text.clear()
                return@setOnKeyListener true
            }
            return@setOnKeyListener false

        }

        return binding.root
    }
}