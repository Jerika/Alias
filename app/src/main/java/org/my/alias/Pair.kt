package org.my.alias

import java.io.Serializable

class Pair(word: String, var isGuess: Boolean) : Serializable {
    var word: String
        internal set

    init {
        this.word = word
    }
}
