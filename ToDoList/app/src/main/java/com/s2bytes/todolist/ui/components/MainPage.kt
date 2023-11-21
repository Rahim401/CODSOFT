@file:Suppress("FunctionName")

package com.s2bytes.todolist.ui.components

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Info
import androidx.compose.material.icons.rounded.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DismissibleNavigationDrawer
import androidx.compose.material3.DrawerDefaults
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.s2bytes.todolist.ui.theme.ToDoListTheme


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToDoAppBar(
    onNavClicked:(()->Unit)={},
    onSortByPressed:(()->Unit)={},
) = CenterAlignedTopAppBar(
    title = {
        Text(
            "ToDo List",
            fontWeight = FontWeight.Bold, fontSize = 25.sp
        )
    },
    navigationIcon = {
        IconButton(
            onClick = {},
            Modifier.padding(start = 5.dp)
        ){
            Icon(
                Icons.Rounded.Menu,"",
                modifier = Modifier.size(25.dp)
            )
        }
    },
    actions = {
        IconButton(
            onClick = {},
            Modifier.padding(end = 5.dp)
        ){
            Icon(
                Icons.Rounded.Info,"",
                modifier = Modifier.size(30.dp)
            )
        }
    },
    colors = TopAppBarDefaults.mediumTopAppBarColors(
        containerColor = MaterialTheme.colorScheme.primary,
        titleContentColor = MaterialTheme.colorScheme.onPrimary,
        navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
        actionIconContentColor = MaterialTheme.colorScheme.onPrimary
    )
)

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
        { ToDoDrawerContent(.53f) },
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