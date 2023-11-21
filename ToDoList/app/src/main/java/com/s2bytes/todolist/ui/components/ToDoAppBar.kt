package com.s2bytes.todolist.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.s2bytes.todolist.ui.theme.ToDoListTheme


@Composable private fun AppBarButton(icon:ImageVector, onClick:()->Unit){
    IconButton(onClick, modifier = Modifier.size(50.dp)){
        Icon(
            icon, null,
            modifier = Modifier.fillMaxSize().padding(10.dp),
            tint = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToDoAppBar(onNavClicked:(()->Unit)={}) = CenterAlignedTopAppBar(
    title = { Text("ToDo List") },
    navigationIcon = { AppBarButton(Icons.Filled.Menu, onNavClicked) },
    colors = TopAppBarDefaults.mediumTopAppBarColors(
        containerColor = MaterialTheme.colorScheme.primary,
        titleContentColor = MaterialTheme.colorScheme.onPrimary,
        navigationIconContentColor = MaterialTheme.colorScheme.onPrimary,
        actionIconContentColor = MaterialTheme.colorScheme.onPrimary
    )
)

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun AppBarPreview() {
    ToDoListTheme {
        ToDoAppBar {  }
    }
}
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun AppBarPreview2() {
    ToDoListTheme {
        ToDoAppBar {  }
    }
}