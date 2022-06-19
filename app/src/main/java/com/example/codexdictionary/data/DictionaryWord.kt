package com.example.codexdictionary.data


data class DictionaryWord(
    val word : String,
    val phonetic : String = "None",
    val phonetics : List<Phonetic> = listOf(Phonetic()),
    val origin : String = "NA",
    val meanings : List<Meaning>
)

data class Meaning(
    val partOfSpeech : String,
    val definitions : List<Definition>,
)

data class Definition(
    val definition : String,
    val example : String = "None",
    val synonyms : List<String> = listOf("None"),
    val antonyms : List<String> = listOf("None")
)

data class Phonetic(
    val text : String = "None",
    val audio : String = "None"
)