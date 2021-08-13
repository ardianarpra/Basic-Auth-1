package com.sst.ngisiyuk.util

import kotlin.math.floor
import kotlin.math.log10

interface ThousandSeparator {
    fun thousandSeparator(n: Int, ch: String?): String? {

        // Counting number of digits
        var myNumber = n
        val l = floor(log10(myNumber.toDouble())).toInt() + 1
        val str = StringBuffer("")
        var count = 0
        var r = 0

        // Checking if number of digits is greater than 3
        if (l > 3) {
            for (i in l - 1 downTo 0) {
                r = myNumber % 10
                myNumber /= 10
                count++
                if (count % 3 == 0 && i != 0) {

                    // Parsing String value of Integer
                    str.append(r.toString())

                    // Appending the separator
                    str.append(ch)
                } else str.append(r.toString())
            }
            str.reverse()
        } else str.append(myNumber.toString())
        return str.toString()
    }
}