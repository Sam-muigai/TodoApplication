package com.example.todoapplication.todo.data.repository

import com.example.todoapplication.todo.data.TodoDao
import com.example.todoapplication.todo.data.TodoEntity
import kotlinx.coroutines.flow.Flow


class TodoRepository(
    private val todoDao: TodoDao
) {

    fun getAllTodos(): Flow<List<TodoEntity>> = todoDao.getAllTodos()

   suspend fun addTodo(todo: TodoEntity){
       todoDao.addTodo(todo)
   }

    suspend fun deleteTodo(todo: TodoEntity){
        todoDao.deleteTodo(todo)
    }

    suspend fun updateTodo(todo: TodoEntity){
        todoDao.updateTodo(todo)
    }

    suspend fun getTodo(id:Int):TodoEntity{
        return todoDao.getTodo(id)
    }
}