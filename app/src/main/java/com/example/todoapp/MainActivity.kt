package com.example.todoapp

import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var items: ArrayList<ToDoItem>
    private lateinit var rvItems: RecyclerView
    private lateinit var rvAdapter: RVAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        items = arrayListOf()

        rvItems = findViewById<RecyclerView>(R.id.rvItems)
        rvAdapter = RVAdapter(items)
        rvItems.adapter = rvAdapter
        rvItems.layoutManager = LinearLayoutManager(this)

        val Add = findViewById<TextView>(R.id.fabAdd)
        Add.setOnClickListener {
            addDialog()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var itemsDeleted = 0
        for(i in items){
            if(i.checked){itemsDeleted++}
        }
        if(itemsDeleted > 0){
         deleteDialog()
        }else{
            Toast.makeText(this, "No items selected", Toast.LENGTH_LONG).show()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun addDialog(){
        val dialogBuilder = AlertDialog.Builder(this)

        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL

        val toDo = EditText(this)
        toDo.hint = "Enter to-do item"
        layout.addView(toDo)

        dialogBuilder.setPositiveButton("ADD", DialogInterface.OnClickListener {
                    dialog, id -> items.add(ToDoItem(toDo.text.toString()))
            })
            .setNegativeButton("CANCEL", DialogInterface.OnClickListener {
                    dialog, id -> dialog.cancel()
            })

        val alert = dialogBuilder.create()
        alert.setTitle("New Item")
        alert.setView(layout)
        alert.show()
    }
    private fun deleteDialog(){

        val dialogBuilder = AlertDialog.Builder(this)
        var itemsDeleted = 0
        for(i in items){
            if(i.checked){itemsDeleted++}
        }
        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL

        val toDo = TextView(this)
        toDo.text =" delete you list ?"
        layout.addView(toDo)
        if (itemsDeleted == 0){
            Toast.makeText(this, "No items selected", Toast.LENGTH_LONG).show()

        }
        else {
            dialogBuilder.setPositiveButton(
                "Delete",
                DialogInterface.OnClickListener { dialog, id ->
                    if (itemsDeleted > 0) {
                        Toast.makeText(this, "$itemsDeleted items deleted", Toast.LENGTH_LONG)
                            .show()
                    }
                    rvAdapter.deleteItems()
                })
                .setNegativeButton("CANCEL", DialogInterface.OnClickListener { dialog, id ->
                    dialog.cancel()
                })
        }
        val alert = dialogBuilder.create()
        alert.setTitle("Delete")
        alert.setView(layout)
        alert.show()
    }
}