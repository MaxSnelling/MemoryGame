package com.maxsnelling.memorygame

import org.junit.Test

import org.junit.Assert.*

/**
 * Testing public methods from GameActivity
 * @author Max Snelling
 * @version 15/07/20
 */
class GameActivityTests {

    @Test
    fun filterListByEvenIndexTests() {
        val gameActivity = GameActivity()

        // Even number of elements
        val inputList1 = intArrayOf(1, 2, 3, 4)
        val expectedEvenList1 = intArrayOf(2, 4)
        val expectedOddList1 = intArrayOf(1, 3)
        val (actualOddList1, actualEvenList1) = gameActivity.filterListByEvenIndex(inputList1)
        assertArrayEquals(expectedEvenList1, actualEvenList1)
        assertArrayEquals(expectedOddList1, actualOddList1)

        // Odd number of elements
        val inputList2 = intArrayOf(1, 2, 3)
        val expectedEvenList2 = intArrayOf(2)
        val expectedOddList2 = intArrayOf(1, 3)
        val (actualOddList2, actualEvenList2) = gameActivity.filterListByEvenIndex(inputList2)
        assertArrayEquals(expectedEvenList2, actualEvenList2)
        assertArrayEquals(expectedOddList2, actualOddList2)

        // Empty input list
        val inputList3 = intArrayOf()
        val expectedEvenList3 = intArrayOf()
        val expectedOddList3 = intArrayOf()
        val (actualOddList3, actualEvenList3) = gameActivity.filterListByEvenIndex(inputList3)
        assertArrayEquals(expectedEvenList3, actualEvenList3)
        assertArrayEquals(expectedOddList3, actualOddList3)

        // Single element input
        val inputList4 = intArrayOf(1)
        val expectedEvenList4 = intArrayOf()
        val expectedOddList4 = intArrayOf(1)
        val (actualOddList4, actualEvenList4) = gameActivity.filterListByEvenIndex(inputList4)
        assertArrayEquals(expectedEvenList4, actualEvenList4)
        assertArrayEquals(expectedOddList4, actualOddList4)
    }
}