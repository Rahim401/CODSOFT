package com.s2bytes.todolist.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.s2bytes.todolist.data.ToDoMessage
import com.s2bytes.todolist.data.fakeTaskList
import com.s2bytes.todolist.ui.theme.ToDoListTheme


val megItemVPadding = 10.dp
@Composable
fun MessageItem(msg:ToDoMessage, onChange:(Boolean)->Unit, modifier: Modifier = Modifier, postModifier: Modifier= Modifier){
    Row(
        modifier = modifier.clip(ShapeDefaults.Small)
            .background(MaterialTheme.colorScheme.primaryContainer)
            .then(postModifier).padding(horizontal = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ){
        val isDescAvailable = msg.desc.isNotBlank()
        Checkbox(msg.isDone, onChange)
        Column(Modifier.padding(start = 5.dp, end = 15.dp), verticalArrangement = Arrangement.Bottom){
            Text(
                msg.title, style = MaterialTheme.typography.titleMedium,
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                maxLines = 1, overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(end = 20.dp, top = megItemVPadding,
                    bottom = if(isDescAvailable) 0.dp else megItemVPadding
                )
            )
            if(isDescAvailable) Text(
                msg.desc, style = MaterialTheme.typography.bodySmall.copy(lineHeight = 12.5.sp),
                color = MaterialTheme.colorScheme.onPrimaryContainer,
                maxLines = 2, overflow = TextOverflow.Ellipsis,
                modifier = Modifier.padding(bottom = megItemVPadding)
            )
        }
    }
}

@Composable
private fun ItemHeader(text: String, modifier: Modifier=Modifier) {
    Box(
        modifier = modifier
            .heightIn(min = 25.dp),
        contentAlignment = Alignment.BottomStart
    ) {
        Text(
            text,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
fun ToDoList(
    msgList:List<ToDoMessage>, modifier: Modifier = Modifier,
    onMsgStateChanged:(ToDoMessage, Boolean)->Unit={_,_-> },
    onMsgClicked: (ToDoMessage)->Unit={}, headerMap:Map<Int,String> = HashMap(),
    listState:LazyListState = rememberLazyListState()
){
    LazyColumn(modifier.padding(10.dp), listState){
        itemsIndexed(msgList){idx,msg ->
            if(headerMap.containsKey(idx))
                ItemHeader(headerMap[idx]!!, Modifier.padding(start=5.dp))

            MessageItem(
                msg,{ state -> onMsgStateChanged(msg,state) },
                Modifier.fillMaxWidth().padding(5.dp),
                postModifier = Modifier.clickable(true,onClick= { onMsgClicked(msg) })
            )
        }
    }
}


@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun ListPreview() {
    ToDoListTheme {
        MessageItem(fakeTaskList[0],{},Modifier.width(400.dp))
    }
}
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun ListPreview2() {
    ToDoListTheme {
        val sortedList = fakeTaskList.sortedByDescending { if(it.isDone) 1 else 0 }
        ToDoList(sortedList, headerMap = mapOf(4 to "Checked once may be", 15 to "Fucked may be couple of times"))
    }
}