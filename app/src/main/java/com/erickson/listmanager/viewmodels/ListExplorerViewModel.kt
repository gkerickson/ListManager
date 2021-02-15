package com.erickson.listmanager.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.erickson.listmanager.model.DatabaseHandler
import kotlinx.coroutines.launch

class ListExplorerViewModel : ViewModel() {

    private suspend fun refresh() {
        mutableLists.value = DatabaseHandler.getLists()
    }

    private val mutableLists: MutableLiveData<List<DatabaseHandler.TodoList>> =
        object : MutableLiveData<List<DatabaseHandler.TodoList>>() {
            override fun onActive() {
                viewModelScope.launch {
                    refresh()
                    if (value?.isEmpty() != false) setupExample()
                    super.onActive()
                }
            }
        }

    fun addList(list: String) {
        viewModelScope.launch {
            DatabaseHandler.addList(list)
            refresh()
        }
    }

    private suspend fun setupExample() {
        DatabaseHandler.addList("Example List")
        DatabaseHandler.getLists()[0].uid?.let { uid ->
            DatabaseHandler.addItem("Example complete to-do item", true, uid)
            DatabaseHandler.addItem("Example incomplete to-do item", false, uid)
            DatabaseHandler.addItem("Other things to take care of", false, uid)
        }
        refresh()
    }

    val lists: LiveData<List<DatabaseHandler.TodoList>> = mutableLists
}
