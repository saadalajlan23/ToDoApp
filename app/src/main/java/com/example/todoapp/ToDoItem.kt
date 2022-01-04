package com.example.todoapp

// A data class allows us to create a class that simply holds some data
// Refer to the Kotlin documentation for a full explanation
// https://kotlinlang.org/docs/data-classes.html
data class ToDoItem(val text: String, var checked: Boolean = false)