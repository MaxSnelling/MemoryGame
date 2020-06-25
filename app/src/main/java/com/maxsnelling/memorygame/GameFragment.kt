package com.maxsnelling.memorygame

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController

class GameFragment : Fragment() {
    val difficultyLevel = 3
    val tileList = createTileList()
    var tileSelectFirst = -1
    var tileSelectSecond = -1

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_game, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        view.findViewById<Button>(R.id.button_back).setOnClickListener {
            findNavController().navigate(R.id.action_FragmentGame_to_fragmentMain)
        }
        view.findViewById<Button>(R.id.tile_1).setOnClickListener {
            tileSelect(0)
        }
        view.findViewById<Button>(R.id.tile_2).setOnClickListener {
            tileSelect(1)
        }
        view.findViewById<Button>(R.id.tile_3).setOnClickListener {
            tileSelect(2)
        }
        view.findViewById<Button>(R.id.tile_4).setOnClickListener {
            tileSelect(3)
        }
        view.findViewById<Button>(R.id.tile_5).setOnClickListener {
            tileSelect(4)
        }
        view.findViewById<Button>(R.id.tile_6).setOnClickListener {
            tileSelect(5)
        }
    }

    fun tileSelect(tileNumber: Int) {
        if(tileSelectFirst < 0) tileSelectFirst = tileNumber
        else if(tileSelectSecond < 0) tileSelectSecond = tileNumber

        if (tileSelectFirst >= 0 && tileSelectSecond >= 0) matchCheck()
    }

    fun matchCheck() {
        if(tileList[tileSelectFirst] == tileList[tileSelectSecond]) {
            println("match")
        }
        println(tileList[tileSelectFirst].toString() + " " + tileList[tileSelectSecond])
        tileSelectFirst = -1
        tileSelectSecond = -1
    }

    fun createTileList() : IntArray {
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
}