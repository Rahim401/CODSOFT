package com.s2bytes.todolist.ui.components

import android.content.res.Configuration.UI_MODE_NIGHT_NO
import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsTopHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.twotone.Person
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.s2bytes.todolist.R
import com.s2bytes.todolist.ui.theme.ToDoListTheme


@Composable private fun DevProfileHeader(devName:String, onClick:(String)->Unit){
    Column(
        Modifier.fillMaxWidth()
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(top=40.dp, bottom = 20.dp, start = 15.dp, end = 15.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ){
        Box(
            Modifier.fillMaxWidth(0.5f).aspectRatio(1f)
                .align(Alignment.CenterHorizontally).clip(CircleShape)
                .background(MaterialTheme.colorScheme.primary.copy(alpha = .75f))
        ){
            Icon(
                Icons.TwoTone.Person,null,
                Modifier.fillMaxSize().padding(10.dp),
                tint = MaterialTheme.colorScheme.primaryContainer
            )
        }
        Text(
            "Designed and Developed By",
            modifier = Modifier.padding(top=30.dp),
            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = .7f),
            style = MaterialTheme.typography.labelLarge,
        )
        Text(
            devName,
            color = MaterialTheme.colorScheme.onPrimaryContainer,
            style = MaterialTheme.typography.headlineSmall,
        )

        Row(
            Modifier.fillMaxWidth().padding(top = 10.dp),
            horizontalArrangement = Arrangement.SpaceEvenly
        ){
            MediaIcon(R.drawable.ic_github){ onClick("Github") }
            MediaIcon(R.drawable.ic_linkedin){ onClick("LinkedIn") }
            MediaIcon(R.drawable.ic_twitter){ onClick("Twitter") }
        }
    }
}
@Composable private fun DrawerItem(name:String, @DrawableRes iconId:Int=-1, onClick: () -> Unit){
    val isJustText = iconId == -1
    Row(
        modifier = Modifier.fillMaxWidth().height(50.dp)
            .clickable(onClick=onClick).padding(horizontal=25.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        if(!isJustText) Icon(
            painter = painterResource(id = iconId),
            modifier = Modifier.padding(end=25.dp),
            tint = MaterialTheme.colorScheme.primary,
            contentDescription = null
        )
        Text(
            name,
            style = if(isJustText) MaterialTheme.typography.labelLarge
            else MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.primary
        )
    }
}
@Composable private fun DividerItem(modifier: Modifier = Modifier) {
    Divider(
        modifier = modifier,
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
    )
}
@Composable private fun MediaIcon(@DrawableRes iconId:Int, onClick:()->Unit){
    IconButton(onClick, modifier = Modifier.size(50.dp)){
        Icon(
            painterResource(iconId), null,
            modifier = Modifier.fillMaxSize().padding(10.dp),
            tint = MaterialTheme.colorScheme.primary
        )
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToDoDrawerContent(
    tasksRatio:Float=1f, onMediaClicked:(String)->Unit={},
    onItemClicked:(String)->Unit={}
){
    ModalDrawerSheet(
        modifier = Modifier.width(300.dp),
    ){
        Column(Modifier.verticalScroll(rememberScrollState())){
            Spacer(Modifier.windowInsetsTopHeight(WindowInsets.statusBars))
            DevProfileHeader("H.Rahim", onMediaClicked)

            Box(Modifier.fillMaxWidth().background(MaterialTheme.colorScheme.primary.copy(.5f))){
                Divider(Modifier.fillMaxWidth(tasksRatio),5.dp,MaterialTheme.colorScheme.primary)
            }

            DrawerItem("Tasks Completed: ${(tasksRatio*100).toInt()}%"){ onItemClicked("TaskRatio") }
            DividerItem()

            DrawerItem("Sort Tasks By", R.drawable.ic_sort){ onItemClicked("Sort") }
            DrawerItem("Clear All Tasks", R.drawable.ic_delete_sweep){ onItemClicked("Delete") }
        }
    }
}




@Preview(uiMode = UI_MODE_NIGHT_YES)
@Composable
fun DrawerPreview() {
    ToDoListTheme {
        ToDoDrawerContent()
    }
}
@Preview(uiMode = UI_MODE_NIGHT_NO)
@Composable
fun DrawerPreview2() {
    ToDoListTheme {
        ToDoDrawerContent(.28f)
    }
}