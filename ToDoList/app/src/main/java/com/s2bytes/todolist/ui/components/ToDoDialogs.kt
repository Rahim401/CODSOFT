package com.s2bytes.todolist.ui.components

import android.content.res.Configuration
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import com.s2bytes.todolist.data.ToDoMessage
import com.s2bytes.todolist.data.dateFormat
import com.s2bytes.todolist.ui.theme.ToDoListTheme


private val DialogPadding = PaddingValues(start = 24.dp, top = 24.dp, end = 24.dp, bottom = 14.dp)
private val IconPadding = PaddingValues(bottom = 16.dp)
private val TitlePadding = PaddingValues(bottom = 8.dp)
private val TextPadding = PaddingValues(bottom = 4.dp);

private val MinWidth = 280.dp
private val MaxWidth = 560.dp

@Composable
fun DialogButton(onClick:()->Unit, text:String, color:Color=Color.Unspecified, modifier: Modifier=Modifier){
    TextButton(
        onClick, modifier,
        shape = ShapeDefaults.Medium
    ){
        Text(
            text,
            style = MaterialTheme.typography.labelLarge,
            color = color
        )
    }
}

@Composable
fun ToDoViewer(msg:ToDoMessage, onEdit:()->Unit = {}, onDelete:()->Unit = {}, onCancel:()->Unit = {}, onDismiss:()->Unit = {}){
    Dialog(onDismiss){
        Surface(
            shape = ShapeDefaults.Medium,
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 6.dp,
        ) {
            Column(
                modifier = Modifier
                    .sizeIn(minWidth = MinWidth, maxWidth = MaxWidth)
                    .padding(DialogPadding),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    "on ${dateFormat.format(msg.timeStamp)}", Modifier.padding(bottom = 4.dp).align(Alignment.Start),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
                )
                Text(
                    msg.title,
                    style = MaterialTheme.typography.headlineSmall.copy(lineHeight = 25.sp)
                )
                if(msg.desc.isNotBlank()) Text(
                    msg.desc, Modifier.padding(top = 8.dp),
                    style = MaterialTheme.typography.bodyMedium
                )
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth().padding(top = 4.dp)
                ){
                    DialogButton(onEdit, "Edit", modifier = Modifier.weight(1f))
                    DialogButton(onDelete, "Delete", modifier = Modifier.weight(1f))
                    DialogButton(onCancel, "Cancel", modifier = Modifier.weight(1f))
                }
            }
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ToDoEditor(
    msg: ToDoMessage? = null,
    onSave: (msg: ToDoMessage) -> Unit = {},
    onCancel: () -> Unit = {},
    onDismiss: () -> Unit = {}
) {
    var taskTitle by remember { mutableStateOf(msg?.title ?: "") }
    var taskDesc by remember { mutableStateOf(msg?.desc ?: "") }

    Dialog(onDismiss) {
        Surface(
            shape = ShapeDefaults.Medium,
            color = MaterialTheme.colorScheme.surface,
            tonalElevation = 6.dp,
            modifier = Modifier.height(600.dp).wrapContentHeight()
        ) {
            Column(
                modifier = Modifier.padding(DialogPadding),
                horizontalAlignment = Alignment.Start
            ) {
                Text(
                    if(msg==null) "Create Task" else "Edit Task",
                    style = MaterialTheme.typography.headlineSmall,
                )
                OutlinedTextField(
                    value = taskTitle,
                    onValueChange = { taskTitle = it },
                    modifier = Modifier.padding(top = 8.dp)
                        .heightIn(TextFieldDefaults.MinHeight),
                    label = { Text("Title of the Task") },
                    singleLine = true
                )
                OutlinedTextField(
                    value = taskDesc,
                    onValueChange = { taskDesc = it },
                    modifier = Modifier.padding(top = 8.dp)
                        .heightIn(TextFieldDefaults.MinHeight),
                    label = { Text("Description") },
                    maxLines = 10
                )

                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth().padding(top = 8.dp)
                ){
                    DialogButton(
                        {
                            onSave(
                                msg?.copy(title = taskTitle, desc = taskDesc) ?:
                                ToDoMessage(taskTitle,taskDesc)
                            )
                        },
                        "Save", modifier = Modifier.weight(1f)
                    )
                    DialogButton(onCancel, "Cancel", modifier = Modifier.weight(1f))
                }
            }
        }
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun DialogPreview3() {
    ToDoListTheme {
        ToDoEditor(ToDoMessage("Fuck the Fish","To the Hell"))
    }
}

@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
@Composable
fun DialogPreview() {
    ToDoListTheme {
        ToDoViewer(ToDoMessage("Fuck the fucking shit to the hell till where we fucking actually can", "Hell is shell the the beutyful suset of the hell's sky, and atlast bey bey "))
    }
}
@Preview(uiMode = Configuration.UI_MODE_NIGHT_NO)
@Composable
fun DialogPreview2() {
    ToDoListTheme {
        ToDoViewer(ToDoMessage("Fuck the Fish","To the Hell"))
    }
}