package com.example.todoapplication.todo.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import kotlinx.coroutines.flow.Flow


@Dao
interface TodoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addTodo(todo:TodoEntity)

    @Query("SELECT * FROM todoEntity")
    fun getAllTodos(): Flow<List<TodoEntity>>

    @Query("SELECT * FROM todoEntity WHERE id=:id")
    suspend fun getTodo(id:Int):TodoEntity

    @Delete
    suspend fun deleteTodo(todo: TodoEntity)

    @Update
    suspend fun updateTodo(todo: TodoEntity)

}