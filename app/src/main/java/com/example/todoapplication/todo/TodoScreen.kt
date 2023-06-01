package com.example.todoapplication.todo

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.Checkbox
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.todoapplication.todo.data.TodoEntity

@Composable
fun TodoScreen(viewModel: TodoViewModel){

    val todos = viewModel.todos.collectAsState().value

    TodoScreenContent(
        title = viewModel.title,
        todos = todos,
        description = viewModel.description,
        onCheckChange = viewModel::onCheckChange,
        onSaveClicked = viewModel::addTodo,
        onDelete = viewModel::deleteTodo,
        onItemClick = viewModel::onTodoClick,
        onTitleChange = viewModel::onTitleChange,
        onDescriptionChange = viewModel::onDescriptionChange
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TodoScreenContent(
    modifier: Modifier = Modifier,
    title: String,
    todos: List<TodoEntity>,
    description: String,
    onDelete:(TodoEntity) ->Unit,
    onItemClick:(Int) ->Unit,
    onCheckChange: (Boolean, TodoEntity) -> Unit,
    onSaveClicked: () -> Unit,
    onTitleChange: (String) -> Unit,
    onDescriptionChange: (String) -> Unit,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(4.dp)
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            shape = RoundedCornerShape(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    value = title,
                    label = {
                            Text(text = "Title")
                    },
                    onValueChange = onTitleChange,
                    shape = RoundedCornerShape(8.dp)
                )
                OutlinedTextField(
                    modifier = Modifier.fillMaxWidth(),
                    label = {
                        Text(text = "Description")
                    },
                    value = description,
                    onValueChange = onDescriptionChange,
                    shape = RoundedCornerShape(8.dp)
                )
                Button(
                    modifier = Modifier.fillMaxWidth(),
                    onClick = onSaveClicked
                ) {
                    Text(text = "SAVE")
                }
            }
        }
        LazyColumn(
            content = {
                items(todos) {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(4.dp)
                            .clickable { onItemClick(it.id!!) },
                        shape = RoundedCornerShape(8.dp),
                    ) {
                         Row(
                            modifier = Modifier
                                .padding(6.dp)
                                .fillMaxWidth(),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Checkbox(
                                checked = it.isCompleted,
                                onCheckedChange = { checked ->
                                    onCheckChange(checked, it)
                                },
                                modifier = Modifier.padding(end = 4.dp)
                            )
                            Column {
                                Text(
                                    text = it.title,
                                    style = MaterialTheme.typography.titleLarge,
                                    color = Color.Black
                                )
                                Text(
                                    text = it.description,
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = Color.Gray
                                )
                            }
                             Row(modifier = Modifier.fillMaxWidth(),
                                 horizontalArrangement = Arrangement.End){
                                 IconButton(onClick = { onDelete(it) }) {
                                     Icon(imageVector = Icons.Default.Delete,
                                         contentDescription = "delete")
                                 }
                             }
                        }
                    }
                }
            }
        )
    }
}