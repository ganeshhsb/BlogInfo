package com.markata.ganesh_hs.data.blog.repo

import com.markata.ganesh_hs.common.EveryTenthCharNotFoundException
import com.markata.ganesh_hs.common.TenthCharNotFoundException
import com.markata.ganesh_hs.common.UniqueWordCountNotFoundException


data class Blog(val content: String) {
    fun getCharAt(position: Int): Result<Char> {
        return try {
            Result.success(content.elementAt(position - 1))
        } catch (e: Exception) {
            Result.failure(TenthCharNotFoundException())
        }
    }

    fun getEveryTenthChar(): Result<String> {
        return if (content.isNotEmpty()) {
            var result: Result<String>
            try {
                val totalTenthChars = (content.length / 10)
                var everyTenthCharArray = ""
                for (i in 1..totalTenthChars) {
                    everyTenthCharArray += content.elementAt((i * 10) - 1)
                }
                result = Result.success(everyTenthCharArray)
            } catch (e: Exception) {
                result = Result.failure(EveryTenthCharNotFoundException())
            }
            result
        } else {
            Result.failure(EveryTenthCharNotFoundException())
        }
    }

    fun countWords(): Result<Int> {
        var result: Result<Int>
        try {
            var uniqueWords = 0
            val stringToProcess = content.trim()
            val array = "\\s+".toRegex().split(stringToProcess) // \s+ // \P{L}]+
            val map: HashMap<String, Int> = HashMap()
            for (item in array) {
                var count = 0
                if (map[item] != null) {
                    count = map[item] ?: 0
                }
                map[item] = ++count
            }

            for (entry in map.entries) {
                if (entry.value == 1) {
                    uniqueWords++
                }
            }

            result = Result.success(uniqueWords)
        } catch (e: Exception) {
            result = Result.failure(UniqueWordCountNotFoundException())
        }
        return result
    }
}