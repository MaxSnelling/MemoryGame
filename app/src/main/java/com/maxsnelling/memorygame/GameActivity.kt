package com.maxsnelling.memorygame

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet

class GameActivity: AppCompatActivity() {
    private val difficultyLevel = 3
    private val tileList = createTileList()
    var tileSelectFirst = -1
    var tileSelectSecond = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        addTiles()
    }

    fun tileSelect(tileNumber: Int) {
        if(tileSelectFirst < 0) tileSelectFirst = tileNumber
        else if(tileSelectSecond < 0) tileSelectSecond = tileNumber

        if (tileSelectFirst >= 0 && tileSelectSecond >= 0) matchCheck()
    }

    private fun matchCheck() {
        if(tileList[tileSelectFirst] == tileList[tileSelectSecond]) {
            println("match")
        }
        println(tileList[tileSelectFirst].toString() + " " + tileList[tileSelectSecond])
        tileSelectFirst = -1
        tileSelectSecond = -1
    }

    private fun createTileList() : IntArray {
        val tileCount = difficultyLevel * 2
        val tileList = IntArray(tileCount)
        var listPosition = 0
        for(x in 1..difficultyLevel) {
            tileList[listPosition] = x
            listPosition++
            tileList[listPosition] = x
            listPosition++
        }

        val listRange = (0 until tileCount)
        for(x in 0..10000) {
            val position1 = listRange.random()
            val position2 = listRange.random()
            val temp = tileList[position1]
            tileList[position1] = tileList[position2]
            tileList[position2] = temp
        }
        return tileList
    }

    private fun addTiles() {
        val constraintLayout = findViewById<ConstraintLayout>(R.id.constraint_layout_game)
        val scoreText = findViewById<TextView>(R.id.gameScore)
        val backButton = findViewById<Button>(R.id.gameBackButton)
        val tileList = arrayListOf<Button>()
        val tileIDList = IntArray(difficultyLevel * 2)

        for (x in 0 until difficultyLevel * 2) {
            val set = ConstraintSet()
            val tile = Button(this)
            tile.text = (x+1).toString()
            tile.id = View.generateViewId()
            constraintLayout.addView(tile)

            set.clone(constraintLayout)
//            if(tileList.isEmpty()) {
//                set.connect(tile.id, ConstraintSet.TOP, scoreText.id, ConstraintSet.BOTTOM)
//            } else {
//                set.connect(tile.id, ConstraintSet.TOP, tileList.last().id, ConstraintSet.BOTTOM)
//            }
//            set.connect(tile.id, ConstraintSet.BOTTOM, backButton.id, ConstraintSet.BOTTOM)
            set.connect(tile.id, ConstraintSet.LEFT, constraintLayout.id, ConstraintSet.LEFT)
            set.connect(tile.id, ConstraintSet.RIGHT, constraintLayout.id, ConstraintSet.RIGHT)
            set.applyTo(constraintLayout)

            tileIDList[x] = tile.id
            tileList.add(tile)
        }

        val set = ConstraintSet()
        set.clone(constraintLayout)
        set.createVerticalChain(scoreText.id, ConstraintSet.TOP, backButton.id, ConstraintSet.TOP, tileIDList, null, ConstraintSet.CHAIN_SPREAD)
        set.applyTo(constraintLayout)
    }
}