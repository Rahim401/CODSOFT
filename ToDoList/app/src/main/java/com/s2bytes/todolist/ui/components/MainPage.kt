@file:Suppress("FunctionName")

package com.s2bytes.todolist.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.s2bytes.todolist.ui.theme.ToDoListTheme




@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToDoDrawer(
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    onSortByPressed:(()->Unit)={},
) = ModalNavigationDrawer({}, drawerState = drawerState){

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomePage(){
    ModalNavigationDrawer(
        { ToDoDrawerSheet(.53f) },
    ) {
        Scaffold(
            topBar = { ToDoAppBar() }
        ) { pd ->
            Text("", modifier = Modifier.padding(pd))
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