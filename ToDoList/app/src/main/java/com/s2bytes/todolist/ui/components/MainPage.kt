@file:Suppress("FunctionName")

package com.s2bytes.todolist.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.s2bytes.todolist.data.SortTaskBy
import com.s2bytes.todolist.data.sortToDoListBy
import com.s2bytes.todolist.ui.theme.ToDoListTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(){
    ModalNavigationDrawer(
        { ToDoDrawerSheet(.53f) },
    ) {
        Scaffold(
            topBar = { ToDoAppBar() },
            floatingActionButton = { FloatingActionButton({}){} },
            floatingActionButtonPosition = FabPosition.End
        ) { pd ->
            val (sortedList, headerMap) = sortToDoListBy(msgList, SortTaskBy.ByTitle)
            ToDoList(sortedList, Modifier.padding(pd), headerMap = headerMap)
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun Preview() {
    ToDoListTheme {
        HomePage()
    }
}