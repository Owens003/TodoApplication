package com.example.todoapplication

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import java.io.IOException
import java.nio.charset.Charset
import  org.apache.commons.io.FileUtils

class MainActivity : AppCompatActivity() {

    var listOftask = mutableListOf<String>()
   lateinit var adapter : TaskItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val onLongClickListener = object : TaskItemAdapter.OnLongClickListener{
            override fun onItemLongClicked(position: Int) {
                listOftask.removeAt(position)

                adapter.notifyDataSetChanged()

                saveItems()
            }

        }

//        // 1. let's detect when the user clicks on the add button
//        findViewById<Button>(R.id.button).setOnClickListener{
//            // Code in here is going to be executed when the user clicks on a button
//            Log.i("Owens", "User clicked on button")
//        }

        listOftask.add("Do laundry")
        listOftask.add("Go for a walk")


        loadItems()

        // look up recyclerView in layout
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        // Create adapter passing in the sample user data
        adapter = TaskItemAdapter(listOftask, onLongClickListener)
        // Attach the adapter to the recyclerview to populate items
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        //set up the button and input, so that the user can enter a task and add it to the list

        val inputTextField = findViewById<EditText>(R.id.addTaskField)

        findViewById<Button>(R.id.button).setOnClickListener {

            val userInputtedTask = inputTextField.text.toString()

            listOftask.add(userInputtedTask)

            adapter.notifyItemInserted(listOftask.size - 1)

            inputTextField.setText("")

            saveItems()
        }

    }

    fun getDataFile() : File {
        return File(filesDir, "data.txt")
    }

    fun loadItems() {
        try {

            listOftask = FileUtils.readLines(getDataFile(), Charset.defaultCharset())
        } catch (ioException: IOException) {
            ioException.printStackTrace()
        }
    }


    fun saveItems() {
        try {
            FileUtils.writeLines(getDataFile(), listOftask)
        }catch (ioException: IOException){
            ioException.printStackTrace()

        }
    }
}
