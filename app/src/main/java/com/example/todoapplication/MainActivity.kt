package com.example.todoapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.viewModelFactory
import androidx.room.Room
import com.example.todoapplication.todo.TodoScreen
import com.example.todoapplication.todo.TodoViewModel
import com.example.todoapplication.todo.data.TodoDatabase
import com.example.todoapplication.todo.data.repository.TodoRepository
import com.example.todoapplication.ui.theme.TodoApplicationTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val database by lazy {
            Room.databaseBuilder(
                context = this,
                klass = TodoDatabase::class.java,
                name = "todo_db"
            ).fallbackToDestructiveMigration().build()
        }
        val viewModel:TodoViewModel by viewModels(
            factoryProducer = {
                object : ViewModelProvider.Factory{
                    override fun <T : ViewModel> create(modelClass: Class<T>): T {
                        return TodoViewModel(repository = TodoRepository(database.dao())) as T
                    }
                }
            }
        )
        setContent {
            TodoApplicationTheme{
                TodoScreen(viewModel = viewModel)
            }
        }
    }
}

