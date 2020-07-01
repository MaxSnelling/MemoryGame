package com.maxsnelling.memorygame

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet

class GameActivity: AppCompatActivity() {
    private val difficultyLevel = 3
    private val tileAssignmentList = createTileAssignmentList()
    private val tileList = arrayListOf<Button>()
    private val pairFoundList = BooleanArray(difficultyLevel)
    private val pairColourMap = hashMapOf(
        0 to Color.BLUE,
        1 to Color.RED,
        2 to Color.YELLOW,
        3 to Color.MAGENTA,
        4 to Color.CYAN,
        5 to Color.DKGRAY
    )
    private var tileSelectFirst = -1
    private var tileSelectSecond = -1
    private var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        addTiles()
    }

    private fun tileSelect(tileNumber: Int) {
        if(tileSelectFirst < 0) tileSelectFirst = tileNumber
        else if(tileSelectSecond < 0) tileSelectSecond = tileNumber
        tileList[tileNumber].setBackgroundColor(pairColourMap[tileAssignmentList[tileNumber]]!!)

        if (tileSelectFirst >= 0 && tileSelectSecond >= 0) matchCheck()
    }

    private fun matchCheck() {
        val pairNumber1 = tileAssignmentList[tileSelectFirst]
        val pairNumber2 = tileAssignmentList[tileSelectSecond]
        val tile1 = tileList[tileSelectFirst]
        val tile2 = tileList[tileSelectSecond]
        val handler = Handler()
        if(pairNumber1 == pairNumber2 && !pairFoundList[pairNumber1 - 1]) {
            score++
            findViewById<TextView>(R.id.gameScore).text = "Score: " + score
            pairFoundList[pairNumber1 -1] = true
            handler.postDelayed(Runnable {
                tile1.setBackgroundColor(Color.GREEN)
                tile2.setBackgroundColor(Color.GREEN)
            }, 1000)
        } else {
            handler.postDelayed(Runnable {
                tile1.setBackgroundColor(Color.LTGRAY)
                tile2.setBackgroundColor(Color.LTGRAY)
            }, 1000)

        }
        println(tileAssignmentList[tileSelectFirst].toString() + " " + tileAssignmentList[tileSelectSecond])

        tileSelectFirst = -1
        tileSelectSecond = -1
    }

    private fun createTileAssignmentList() : IntArray {
        val tileCount = difficultyLevel * 2
        val tileAssignmentList = IntArray(tileCount)
        var listPosition = 0
        for(x in 1..difficultyLevel) {
            tileAssignmentList[listPosition] = x
            listPosition++
            tileAssignmentList[listPosition] = x
            listPosition++
        }

        val listRange = (0 until tileCount)
        for(x in 0..10000) {
            val position1 = listRange.random()
            val position2 = listRange.random()
            val temp = tileAssignmentList[position1]
            tileAssignmentList[position1] = tileAssignmentList[position2]
            tileAssignmentList[position2] = temp
        }
        return tileAssignmentList
    }

    private fun addTiles() {
        val constraintLayout = findViewById<ConstraintLayout>(R.id.constraint_layout_game)
        val scoreText = findViewById<TextView>(R.id.gameScore)
        val backButton = findViewById<Button>(R.id.gameBackButton)
        val tileIDList = IntArray(difficultyLevel * 2)

        for (x in 0 until difficultyLevel * 2) {
            val set = ConstraintSet()
            val tile = Button(this)
            tile.text = (x+1).toString()
            tile.setBackgroundColor(Color.LTGRAY)
            tile.id = View.generateViewId()
            constraintLayout.addView(tile)

            tile.setOnClickListener{
                if (!pairFoundList[tileAssignmentList[x]-1])
                    tileSelect(x)
            }

            set.clone(constraintLayout)
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