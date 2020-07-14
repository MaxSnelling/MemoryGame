package com.maxsnelling.memorygame

import android.content.Intent
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
    private var difficultyLevel = 0
    private lateinit var tileAssignmentList : IntArray
    private var tileList = arrayListOf<Button>()
    private lateinit var pairFoundList : BooleanArray
    private var tileSelectFirst = -1
    private var tileSelectSecond = -1
    private var score = 0
    private val pairColourMap = hashMapOf(
        0 to Color.BLUE,
        1 to Color.RED,
        2 to Color.YELLOW,
        3 to Color.MAGENTA,
        4 to Color.CYAN,
        5 to Color.DKGRAY,
        6 to Color.WHITE
    )

    private fun initialiseVariables() {
        difficultyLevel = intent.extras!!.getInt("difficulty") + 1
        tileAssignmentList = createTileAssignmentList()
        pairFoundList = BooleanArray(difficultyLevel)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_game)
        initialiseVariables()
        addTiles()
        findViewById<Button>(R.id.gameBackButton).setOnClickListener{
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }

    private fun tileSelect(tileNumber: Int) {
        if (tileSelectFirst < 0) tileSelectFirst = tileNumber
        else if (tileSelectSecond < 0) tileSelectSecond = tileNumber
        tileList[tileNumber].setBackgroundColor(pairColourMap[tileAssignmentList[tileNumber]]!!)

        if (tileSelectFirst >= 0 && tileSelectSecond >= 0) matchCheck()
    }

    private fun matchCheck() {
        val pairNumber1 = tileAssignmentList[tileSelectFirst]
        val pairNumber2 = tileAssignmentList[tileSelectSecond]
        val tile1 = tileList[tileSelectFirst]
        val tile2 = tileList[tileSelectSecond]
        score++
        findViewById<TextView>(R.id.gameScore).text = "Score: " + score
        val handler = Handler()
        if (pairNumber1 == pairNumber2 && !pairFoundList[pairNumber1 - 1]) {
            pairFoundList[pairNumber1 - 1] = true
            handler.postDelayed(Runnable {
                tile1.setBackgroundColor(Color.GREEN)
                tile2.setBackgroundColor(Color.GREEN)
                if(!pairFoundList.contains(false)) gameWon()
            }, 1000)
        } else {
            handler.postDelayed(Runnable {
                tile1.setBackgroundColor(Color.LTGRAY)
                tile2.setBackgroundColor(Color.LTGRAY)
            }, 1000)

        }
        tileSelectFirst = -1
        tileSelectSecond = -1
    }

    private fun createTileAssignmentList(): IntArray {
        val tileCount = difficultyLevel * 2
        val tileAssignmentList = IntArray(tileCount)
        var listPosition = 0
        for (x in 1..difficultyLevel) {
            tileAssignmentList[listPosition] = x
            listPosition++
            tileAssignmentList[listPosition] = x
            listPosition++
        }

        val listRange = (0 until tileCount)
        for (x in 0..10000) {
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

        for (x in 0 until difficultyLevel*2) {
            val tile = Button(this)
            tile.text = (x + 1).toString()
            tile.setBackgroundColor(Color.LTGRAY)
            tile.id = View.generateViewId()
            constraintLayout.addView(tile)
            tileIDList[x] = tile.id
            tileList.add(tile)

            tile.setOnClickListener {
                if (!pairFoundList[tileAssignmentList[x] - 1])
                    tileSelect(x)
            }
        }
        val(oddTileIDs, evenTileIDs) = filterListByEvenIndex(tileIDList)
        createVerticalTileChains(oddTileIDs, evenTileIDs, scoreText.id, backButton.id, constraintLayout)
        createHorizontalTileChains(tileIDList, constraintLayout)
    }

    private fun filterListByEvenIndex(tileIDList: IntArray) : Pair<IntArray, IntArray> {
        val oddTileIDs = IntArray(difficultyLevel)
        val evenTileIDs = IntArray(difficultyLevel)
        var i = 0
        var j = 0
        for(x in tileIDList) {
            if(x%2 > 0) {
                oddTileIDs[i] = x
                i++
            } else {
                evenTileIDs[j] = x
                j++
            }
        }
        return Pair(oddTileIDs, evenTileIDs)
    }

    private fun createVerticalTileChains(evenTileIDs: IntArray, oddTileIDs: IntArray, upperID: Int, lowerID: Int, constraintLayout: ConstraintLayout) {
        val constraintSet = ConstraintSet()
        constraintSet.clone(constraintLayout)
        constraintSet.createVerticalChain(
            upperID,
            ConstraintSet.BOTTOM,
            lowerID,
            ConstraintSet.TOP,
            oddTileIDs,
            null,
            ConstraintSet.CHAIN_SPREAD
        )
        constraintSet.createVerticalChain(
            upperID,
            ConstraintSet.BOTTOM,
            lowerID,
            ConstraintSet.TOP,
            evenTileIDs,
            null,
            ConstraintSet.CHAIN_SPREAD
        )
        constraintSet.applyTo(constraintLayout)
    }

    private fun createHorizontalTileChains(tileIDList: IntArray, constraintLayout: ConstraintLayout) {
        val constraintSet = ConstraintSet()
        constraintSet.clone(constraintLayout)
        for(x in 0..tileIDList.size-2 step 2) {
            constraintSet.createHorizontalChain(
                constraintLayout.id,
                ConstraintSet.LEFT,
                constraintLayout.id,
                ConstraintSet.RIGHT,
                tileIDList.copyOfRange(x, x+2),
                null,
                ConstraintSet.CHAIN_SPREAD
            )
        }
        constraintSet.applyTo(constraintLayout)
    }

    private fun gameWon() {
        val constraintLayout = findViewById<ConstraintLayout>(R.id.constraint_layout_game)
        for(x in tileList) {
            constraintLayout.removeView(constraintLayout.findViewById<Button>(x.id))
        }

        val playAgainButton = Button(this)
        playAgainButton.text = getString(R.string.PlayAgainText)
        playAgainButton.id = View.generateViewId()
        constraintLayout.addView(playAgainButton)
        playAgainButton.setOnClickListener {
            score = 0
            tileAssignmentList = createTileAssignmentList()
            tileList = arrayListOf<Button>()
            pairFoundList = BooleanArray(difficultyLevel)
            addTiles()
            constraintLayout.removeView(playAgainButton)
        }

        val constraintSet = ConstraintSet()
        constraintSet.clone(constraintLayout)
        constraintSet.connect(playAgainButton.id, ConstraintSet.LEFT, constraintLayout.id ,ConstraintSet.LEFT)
        constraintSet.connect(playAgainButton.id, ConstraintSet.RIGHT, constraintLayout.id ,ConstraintSet.RIGHT)
        constraintSet.connect(playAgainButton.id, ConstraintSet.BOTTOM, constraintLayout.id ,ConstraintSet.BOTTOM)
        constraintSet.connect(playAgainButton.id, ConstraintSet.TOP, constraintLayout.id ,ConstraintSet.TOP)

        constraintSet.applyTo(constraintLayout)


    }
}