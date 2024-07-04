package com.s2bytes.todolist

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.Modifier
import androidx.core.view.WindowCompat
import com.s2bytes.todolist.data.SortTaskBy
import com.s2bytes.todolist.data.ToDoMessage
import com.s2bytes.todolist.data.deleteTask
import com.s2bytes.todolist.data.fakeTaskList
import com.s2bytes.todolist.data.sortToDoListBy
import com.s2bytes.todolist.ui.components.SelectorDialog
import com.s2bytes.todolist.ui.components.ToDoAction
import com.s2bytes.todolist.ui.components.ToDoAppBar
import com.s2bytes.todolist.ui.components.ToDoDrawerSheet
import com.s2bytes.todolist.ui.components.ToDoEditor
import com.s2bytes.todolist.ui.components.ToDoList
import com.s2bytes.todolist.ui.components.ToDoViewer
import com.s2bytes.todolist.ui.theme.ToDoListTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class MainActivity : ComponentActivity() {
    private var prefSortBy = SortTaskBy.ByTitle
    private lateinit var tasks:SnapshotStateList<ToDoMessage>

    private fun addTask(task:ToDoMessage){
        if(task.title.isBlank())
            Toast.makeText(this,"Can't Save Task without Title",Toast.LENGTH_SHORT).show()
        else{
            tasks.remove(task); tasks.add(task)
            MainApplication.getMainApp()?.setTasks(tasks)
        }
    }
    private fun removeTask(task:ToDoMessage){
        if(tasks.remove(task))
            MainApplication.getMainApp()?.setTasks(tasks)
    }
    private fun clearAllTask(){
        tasks.clear()
        MainApplication.getMainApp()?.setTasks(tasks)
    }

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        prefSortBy = MainApplication.getMainApp()?.getSortBy() ?: SortTaskBy.ByIsUnchecked
        tasks = MainApplication.getMainApp()?.getTasks()?.toMutableStateList() ?: mutableStateListOf()

        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            ToDoListTheme {
                var createTask by remember{ mutableStateOf(false) }
                var isViewingTask by remember{ mutableStateOf<ToDoMessage?>(null) }
                val dismissViewingTask = { isViewingTask = null }
                var isEditingTask by remember{ mutableStateOf<ToDoMessage?>(null) }
                val dismissEditingTask = { isEditingTask = null; createTask = false }
                var selectSortBy by remember{ mutableStateOf(false) }
                var sortTasksBy by remember{ mutableStateOf(prefSortBy) }
                val dismissSortBySelector = { selectSortBy = false }

                val scrollState = rememberLazyListState()
                val drawerState = rememberDrawerState(DrawerValue.Closed)
                val isOnTop = remember { derivedStateOf { scrollState.firstVisibleItemIndex == 0 } }
                val taskCompletionRatio by remember {
                    derivedStateOf {
                        if(tasks.isEmpty()) 1f
                        else tasks.count { it.isDone } / tasks.size.toFloat()
                    }
                }
                val coScope = rememberCoroutineScope()


                if(isViewingTask!=null)
                    ToDoViewer(
                        isViewingTask!!,
                        onEdit = { isEditingTask = isViewingTask; dismissViewingTask() },
                        onDelete = { removeTask(isViewingTask!!); dismissViewingTask() },
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
                else if(selectSortBy)
                    SelectorDialog(
                        title = "Sort Task By",
                        options = listOf(
                            Pair(R.drawable.ic_sort_bool_ascending_variant, "Pending Tasks First"),
                            Pair(R.drawable.ic_sort_bool_descending_variant, "Completed Tasks First"),
                            Pair(R.drawable.ic_sort_clock_ascending, "Date Ascending"),
                            Pair(R.drawable.ic_sort_clock_descending, "Date Descending"),
                            Pair(R.drawable.ic_sort_alphabetical_ascending, "Alphabetical Ascending"),
                            Pair(R.drawable.ic_sort_alphabetical_descending, "Alphabetical Descending"),
                        ), SortTaskBy.values().indexOf(sortTasksBy), { sortTasksBy = SortTaskBy.values()[it]; dismissSortBySelector()},
                        onDismiss = dismissSortBySelector
                    )

                ModalNavigationDrawer(
                    drawerContent = {
                        ToDoDrawerSheet(
                            tasksRatio = taskCompletionRatio,
                            onMediaClicked = {},
                            onItemClicked = {
//                                CoroutineScope(Dispatchers.Default)
//                                    .launch { drawerState.close() }
                                coScope.launch {
                                    drawerState.close()
                                    when(it){
                                        "SortTasks" -> selectSortBy = true
                                        "ClearAll" -> clearAllTask()
                                    }
                                }
                            }
                        )
                    } , drawerState = drawerState
                ) {
                    Scaffold(
                        topBar = {
                            ToDoAppBar(
                                onNavClicked = {
                                    if(!drawerState.isAnimationRunning) coScope.launch {
                                        if(drawerState.isClosed) drawerState.open()
                                        else drawerState.close()
                                    }
                                }
                            )
                        },
                        floatingActionButton = { ToDoAction({ createTask = true }, isExpanded = isOnTop.value) },
                        floatingActionButtonPosition = FabPosition.End
                    ) { pd ->
                        val (sortedList, headerMap) = sortToDoListBy(tasks, sortTasksBy)
                        ToDoList(
                            sortedList,
                            Modifier.padding(pd),
                            onMsgClicked = { isViewingTask = it; } ,
                            onMsgStateChanged = {task,isDone -> addTask(task.copy(isDone = isDone)) },
                            headerMap = headerMap, listState = scrollState
                        )
                    }
                }
            }
        }
    }
}