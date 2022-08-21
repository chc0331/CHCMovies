package com.example.chcmovies.extension

fun List<String>.makeList(): String {
    val listString = StringBuilder()

    for (item in this) {
        listString.append(item)
            .append(',')
    }
    listString.deleteCharAt(listString.lastIndex)
    return listString.toString()
}