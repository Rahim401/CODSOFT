package com.s2bytes.todolist.data

data class ToDoMessage(
    val title: String,
    val desc: String, val isDone: Boolean,
    val timeStamp: Long = System.currentTimeMillis()
)