package com.example.codexdictionary.dictionary

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.codexdictionary.domain.DictionaryResult
import com.example.codexdictionary.network.Definition
import com.example.codexdictionary.network.DictionaryApi
import com.example.codexdictionary.network.DictionaryWord
import com.example.codexdictionary.network.Meaning
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class DictionaryViewModel : ViewModel(){
    init {
    }

    private var viewModelJob = Job()
    private val coroutineScope = CoroutineScope(viewModelJob + Dispatchers.Main)



    private val _word = MutableLiveData<String>()
    val word : LiveData<String>
        get() = _word

    private val _dictionaryWord = MutableLiveData<List<DictionaryWord>>()
    val dictionaryWord : LiveData<List<DictionaryWord>>
        get() = _dictionaryWord

    private val _dictionaryResult = MutableLiveData<List<DictionaryResult>>()
    val dictionaryResult : LiveData<List<DictionaryResult>>
        get() = _dictionaryResult

    private val _definition = MutableLiveData<String>()
    val definition: LiveData<String>
        get() = _definition

    private val _example = MutableLiveData<String>()
    val example : LiveData<String>
        get() = _example

    private val _buttonClicked = MutableLiveData<Boolean>()
    val buttonClicked : LiveData<Boolean>
        get() = _buttonClicked


    val receivedMeaningsList : MutableList<List<Meaning>> = mutableListOf()

    val receivedDefinitionsList : MutableList<List<Definition>> = mutableListOf()

    val receivedResultsList : MutableList<DictionaryResult> = mutableListOf() //A list of meaning and example strings

    fun onFindButtonClick(){
        _buttonClicked.value = true
    }

    fun onClick(queriedWord: String){
        getDictionaryWord(queriedWord)

    }

    fun onFindButtonClickComplete(){
        _buttonClicked.value = false
    }




    private fun getDictionaryWord(queriedWord : String){
        coroutineScope.launch{
            var getWordsDeferred = DictionaryApi.retrofitService.getWord(queriedWord)
            try{
                var resultWord = getWordsDeferred.await()
                _dictionaryWord.value = resultWord
                _word.value = resultWord[0].word


                receivedMeaningsList.clear()
                resultWord.map{
                        receivedMeaningsList.add(it.meanings)
                    }

                receivedDefinitionsList.clear()
                receivedMeaningsList.map{
                    it.forEach{
                        receivedDefinitionsList.add(it.definitions)
                    }
                }


                receivedResultsList.clear()
                receivedDefinitionsList.map{
                    it.forEach{
                        receivedResultsList.add(DictionaryResult(it.definition,it.example))
                    }
                }
                _dictionaryResult.value = receivedResultsList

            }catch (t:Throwable){
                _word.value = "Word not found"
                _dictionaryResult.value = listOf()
            }
        }
    }


    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}