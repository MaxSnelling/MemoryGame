package com.maxsnelling.memorygame

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity

/**
 * Activity for selecting difficulty setting and opening the game page
 * @author Max Snelling
 * @version 14/07/20
 */
class MainActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {
    private var difficultyLevel = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        addAdapterToSpinner()
        findViewById<Button>(R.id.PlayButton).setOnClickListener {
            if(difficultyLevel > 0) {
                val intent = Intent(this, GameActivity::class.java)
                intent.putExtra("difficulty", difficultyLevel)
                startActivity(intent)
            }
        }
    }

    private fun addAdapterToSpinner() {
        val spinner = findViewById<Spinner>(R.id.difficultySpinner)
        val difficultyLevelList: Array<String> = resources.getStringArray(R.array.difficultyList)
        spinner.onItemSelectedListener = this
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, difficultyLevelList)
        spinner.adapter = adapter
    }

    override fun onItemSelected(arg0: AdapterView<*>, arg1: View, position: Int, id: Long) {
        val difficultyLevelList: Array<String> = resources.getStringArray(R.array.difficultyList)
        difficultyLevel = difficultyLevelList[position].toInt()
    }

    override fun onNothingSelected(parent: AdapterView<*>?) {
        difficultyLevel = 0
    }
}