package com.example.todoapplication.todo.data

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(
    entities = [TodoEntity::class],
    version = 2
)
abstract class TodoDatabase:RoomDatabase() {
    abstract fun dao():TodoDao
}