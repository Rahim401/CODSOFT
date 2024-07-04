@file:Suppress("FunctionName")

package com.s2bytes.todolist.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.s2bytes.todolist.data.SortTaskBy
import com.s2bytes.todolist.data.ToDoMessage
import com.s2bytes.todolist.data.addTask
import com.s2bytes.todolist.data.deleteTask
import com.s2bytes.todolist.data.fakeTaskList
import com.s2bytes.todolist.data.sortToDoListBy
import com.s2bytes.todolist.ui.theme.ToDoListTheme
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(){
    val coroutineScope = rememberCoroutineScope()
    val scrollState = rememberLazyListState()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val isOnTop = remember { derivedStateOf { scrollState.firstVisibleItemIndex == 0 } }

    var createTask by remember{ mutableStateOf(false) }
    var isViewingTask by remember{ mutableStateOf<ToDoMessage?>(null) }
    val dismissViewingTask = { isViewingTask = null }
    var isEditingTask by remember{ mutableStateOf<ToDoMessage?>(null) }
    val dismissEditingTask = { isEditingTask = null; createTask = false }

    if(isViewingTask!=null)
        ToDoViewer(
            isViewingTask!!,
            onEdit = { isEditingTask = isViewingTask; dismissViewingTask() },
            onDelete = { deleteTask(isViewingTask!!); dismissViewingTask() },
            onCancel = dismissViewingTask,
            onDismiss = dismissViewingTask,
        )
    else if(isEditingTask!=null || createTask)
        ToDoEditor(
            isEditingTask,
            onSave = { addTask(it); dismissEditingTask() },
            onCancel = dismissEditingTask,
            onDismiss = dismissEditingTask,
        )

    ModalNavigationDrawer(
        drawerContent = { ToDoDrawerSheet(.53f) },
        drawerState = drawerState
    ) {
        Scaffold(
            topBar = {
                println("Ommbu1 $drawerState")
                ToDoAppBar(
                    onNavClicked = {
                        println("Ommbu $drawerState")
                        if(!drawerState.isAnimationRunning) coroutineScope.launch {
                            if(drawerState.isClosed) drawerState.open()
                            else drawerState.close()
                        }
                    }
                )
            },
            floatingActionButton = {
                ToDoAction({ createTask = true }, isExpanded = isOnTop.value)
           },
            floatingActionButtonPosition = FabPosition.End
        ) { pd ->
            val (sortedList, headerMap) = sortToDoListBy(fakeTaskList, SortTaskBy.ByTitle)
            ToDoList(
                sortedList,
                Modifier.padding(pd),
                onMsgClicked = { isViewingTask = it; } ,
                headerMap = headerMap, listState = scrollState
            )
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