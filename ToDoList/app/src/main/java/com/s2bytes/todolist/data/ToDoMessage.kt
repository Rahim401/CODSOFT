package com.s2bytes.todolist.data

import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

data class ToDoMessage(
    val title: String,
    val desc: String = "", val isDone: Boolean = false,
    val timeStamp: Long = System.currentTimeMillis()
)


enum class SortTaskBy(val type:Int, val isDescending:Boolean = false){
    ByIsChecked(1), ByIsUnchecked(1, true),
    ByDate(2), ByDateDescending(2, true),
    ByTitle(3), ByTitleDescending(3, true),
}

fun sortToDoListBy(msgList:List<ToDoMessage>, sortBy:SortTaskBy): Pair<List<ToDoMessage>,Map<Int,String>>{
    val sortedList = when(sortBy){
        SortTaskBy.ByIsChecked -> msgList.sortedBy { if(it.isDone) 0 else 1 }
        SortTaskBy.ByIsUnchecked -> msgList.sortedByDescending { if(it.isDone) 0 else 1 }
        SortTaskBy.ByDate -> msgList.sortedBy { it.timeStamp }
        SortTaskBy.ByDateDescending -> msgList.sortedByDescending { it.timeStamp }
        SortTaskBy.ByTitle -> msgList.sortedBy { it.title }
        SortTaskBy.ByTitleDescending -> msgList.sortedByDescending { it.title }
    }

    val headerMap = HashMap<Int,String>(sortedList.size/4)
    if(sortBy==SortTaskBy.ByIsChecked){
        headerMap[0] = "Tasks Completed"
        headerMap[sortedList.count { it.isDone }] = "UnCompleted Tasks"
    }
    else if(sortBy==SortTaskBy.ByIsUnchecked){
        headerMap[0] = "UnCompleted Tasks"
        headerMap[sortedList.count { !it.isDone }] = "Tasks Completed"
    }
    else if(sortBy.type==2) sortedList.forEachIndexed { idx, msg ->
        var msgDate = SimpleDateFormat("MMM d yyyy, EEE", Locale.getDefault())
            .format(Date(msg.timeStamp))
        msgDate = "on $msgDate"
        if(!headerMap.containsValue(msgDate))
            headerMap[idx] = msgDate
    }

    return Pair(sortedList, headerMap)
}