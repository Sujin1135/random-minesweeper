package com.socar.minesweeper.domain

import java.util.Random
import kotlin.math.abs

/**
 * A field of the minesweeper game
 *
 * @property rowNumber the number of rows in this field
 * @property mineNumber the number of mines
 */
class Field(
    private val rowNumber: Int = 10,
    private val mineNumber: Int = 10,
) {
    val mineLocations = createRandomMineLocations(rowNumber * rowNumber)
    val cells: Array<IntArray> by lazy {
        this.createCells()
    }

    /**
     * Create random mine locations[acc] by the number[limit]
     *
     * @return a random location of mines
     */
    private tailrec fun createRandomMineLocations(limit: Int, acc: IntArray = intArrayOf()): IntArray = when (acc.size) {
        mineNumber -> acc
        else -> {
            var num = Random().nextInt(limit)

            while (acc.contains(num)) {
                num = Random().nextInt(limit)
            }

            createRandomMineLocations(limit, acc.plus(num))
        }
    }

    /**
     * Calculate a distance between two parameters[num1] [num2]
     *
     * @return distance between parameters
     */
    private fun calculateDistance(num1: Int, num2: Int): Int {
        return abs(num1 - num2)
    }

    /**
     * Calculate the number of mines around a cell[num]
     *
     * @return the number of mines around a cell
     */
    private tailrec fun countAroundMines(num: Int, index: Int = 0, acc: Int = 0): Int = when (index) {
        mineLocations.size -> acc
        else -> {
            val location = mineLocations[index]
            val verticalDistance = calculateDistance(num / rowNumber, location / rowNumber)
            val horizontalDistance = calculateDistance(num % rowNumber, location % rowNumber)
            val isAround = verticalDistance < 2 && horizontalDistance < 2

            val additionalCount = if (isAround) 1 else 0

            countAroundMines(num, index + 1, acc + additionalCount)
        }
    }

    /**
     * Create cells of the minesweeper game
     * -1 is a mine
     * Other numbers is number of mines around a cell
     *
     * @return minesweeper cells
     */
    private fun createCells(): Array<IntArray> {
        return Array(rowNumber) { i -> IntArray(rowNumber) {
            j -> when {
                mineLocations.contains(i * 10 + j) -> -1
                else -> countAroundMines(i * 10 + j)
            }
        } }
    }
}
