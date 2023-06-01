package com.example.todoapplication.todo

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoapplication.todo.data.TodoEntity
import com.example.todoapplication.todo.data.repository.TodoRepository
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch


class TodoViewModel(
    private val repository: TodoRepository
) : ViewModel() {

    var title: String by mutableStateOf("")
        private set

    var description: String by mutableStateOf("")
        private set

    var todoId: Int? by mutableStateOf(null)
        private set

    var isCompleted: Boolean by mutableStateOf(false)

    fun onTitleChange(title: String) {
        this.title = title
    }

    fun onDescriptionChange(description: String) {
        this.description = description
    }


    val todos = repository.getAllTodos()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000),
            initialValue = emptyList()
        )

    fun addTodo() {
        if (title.isNotEmpty() && description.isNotEmpty()) {
            val todo = TodoEntity(
                id = todoId,
                title = title,
                description = description,
                isCompleted = isCompleted
            )
            viewModelScope.launch {
                repository.addTodo(todo)
            }
           resetValues()
        }
    }

    private fun resetValues(){
        title = ""
        description = ""
        isCompleted = false
        todoId = null
    }

    fun deleteTodo(
        todo: TodoEntity
    ) {
        viewModelScope.launch {
            repository.deleteTodo(todo)
        }
    }

    fun updateTodo(
        todo: TodoEntity
    ) {
        viewModelScope.launch {
            repository.updateTodo(todo)
        }
    }


    fun onTodoClick(id: Int) {
        viewModelScope.launch {
            val todo = repository.getTodo(id)
            todoId = todo.id
            title = todo.title
            description = todo.description
            isCompleted = isCompleted
        }
    }

    fun onCheckChange(
        check: Boolean,
        todo: TodoEntity
    ) {
        val todoToUpdate = todo.copy(
            isCompleted = check
        )
        viewModelScope.launch {
            repository.updateTodo(todoToUpdate)
        }
    }
}