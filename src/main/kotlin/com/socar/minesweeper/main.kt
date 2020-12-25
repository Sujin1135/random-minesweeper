package com.socar.minesweeper

import com.socar.minesweeper.domain.Field

fun main(args: Array<String>) {
    val field = Field()

    // print cells of the minesweeper game
    for (arr in field.cells) {
        for (i in arr) {
            print("$i, ")
        }

        println("")
    }
}