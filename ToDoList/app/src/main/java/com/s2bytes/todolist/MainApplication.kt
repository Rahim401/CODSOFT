package com.s2bytes.todolist

import android.app.Application
import android.content.SharedPreferences
import androidx.core.content.edit
import com.s2bytes.todolist.data.SortTaskBy
import com.s2bytes.todolist.data.ToDoMessage
import java.lang.ref.WeakReference

const val AppDataKey = "MainAppData"
const val TasksKey = "Tasks"
const val SortByKey = "SortBy"

class MainApplication: Application() {
    private lateinit var shPref:SharedPreferences
    override fun onCreate() {
        mainApp = WeakReference(this)
        shPref = getSharedPreferences(AppDataKey, MODE_PRIVATE)
        super.onCreate()
    }

    fun getSortBy():SortTaskBy {
        val sortBy = shPref.getString(SortByKey, "ByIsUnchecked") ?: "ByIsUnchecked"
        return SortTaskBy.valueOf(sortBy)
    }
    fun setSortBy(sortBy: SortTaskBy) =
        shPref.edit{ putString(SortByKey, sortBy.name) }

    fun getTasks() =
        (shPref.getStringSet(TasksKey, null) ?: mutableSetOf<String>())
            .toList().map{ ToDoMessage.decode(it) }
    fun setTasks(tasks: List<ToDoMessage>) = shPref.edit{
        putStringSet(TasksKey, tasks.map { it.encode() }.toMutableSet())
    }

    companion object{
        private lateinit var mainApp:WeakReference<MainApplication>
        fun getMainApp() = mainApp.get()
    }
}