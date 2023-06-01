package com.example.todoapplication.todo.data

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class TodoEntity(
    @PrimaryKey(autoGenerate = true)
    val id :Int? = null,
    val title:String,
    val description:String,
    val isCompleted:Boolean = false
)
