package org.my.alias

import java.io.Serializable

class Team(number: Int) : Serializable {
    var score: Int = 0
    var steps: Int = 0
    var number: Int = 0
        internal set

    init {
        score = 0
        steps = 0
        this.number = number
    }


}
