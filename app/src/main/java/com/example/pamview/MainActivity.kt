package com.example.pamview

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.textfield.TextInputEditText
import androidx.appcompat.widget.Toolbar



data class TodoItem(val date: String, val task: String)

class MainActivity : AppCompatActivity() {
    private lateinit var todoAdapter: TodoAdapter
    private val todoList = mutableListOf(
        TodoItem("18 Sep 2024", "Les Masterprima"),
        TodoItem("19 Sep 2024", "Les Masterprima"),
        TodoItem("21 Sep 2024", "Me Time Ke Gramedia"),
        TodoItem("22 Okt 2024", "Tes SKD ")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val toolbar: Toolbar = findViewById(R.id.toolbar)
        setSupportActionBar(toolbar)

        val recyclerView: RecyclerView = findViewById(R.id.todoRecyclerView)
        todoAdapter = TodoAdapter(todoList)
        recyclerView.adapter = todoAdapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        val dateEditText: TextInputEditText = findViewById(R.id.dateEditText)
        val taskEditText: TextInputEditText = findViewById(R.id.taskEditText)
        val addButton: Button = findViewById(R.id.addButton)

        addButton.setOnClickListener {
            val date = dateEditText.text.toString()
            val task = taskEditText.text.toString()

            if (date.isNotEmpty() && task.isNotEmpty()) {
                val newTodoItem = TodoItem(date, task)
                todoAdapter.addItem(newTodoItem)
                recyclerView.scrollToPosition(todoList.size - 1)

                // Clear input fields
                dateEditText.text?.clear()
                taskEditText.text?.clear()
            } else {
                Toast.makeText(this, "Please fill both date and task", Toast.LENGTH_SHORT).show()
            }
        }
    }

    inner class TodoAdapter(private val todoList: MutableList<TodoItem>) :
        RecyclerView.Adapter<TodoAdapter.TodoViewHolder>() {

        inner class TodoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
            val dateTextView: TextView = itemView.findViewById(R.id.dateTextView)
            val taskTextView: TextView = itemView.findViewById(R.id.taskTextView)
            val deleteButton: ImageButton = itemView.findViewById(R.id.deleteButton)
        }


        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TodoViewHolder {
            val itemView = LayoutInflater.from(parent.context)
                .inflate(R.layout.todo, parent, false)
            return TodoViewHolder(itemView)
        }


        override fun onBindViewHolder(holder: TodoViewHolder, position: Int) {
            val currentItem = todoList[position]
            holder.dateTextView.text = currentItem.date
            holder.taskTextView.text = currentItem.task

            holder.deleteButton.setOnClickListener {
                deleteItem(position)
            }
        }

        override fun getItemCount() = todoList.size

        fun addItem(todoItem: TodoItem) {
            todoList.add(todoItem)
            notifyItemInserted(todoList.size - 1)
        }

        private fun deleteItem(position: Int) {
            if (position >= 0 && position < todoList.size) {
                todoList.removeAt(position)
                notifyItemRemoved(position)
            } else {
                Toast.makeText(this@MainActivity, "Invalid position", Toast.LENGTH_SHORT).show()
            }
        }

    }
}