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

    fun deleteList(listId: Int) {
        viewModelScope.launch {
            DatabaseHandler.deleteTodoItemList(listId)
            refresh()
        }
    }

    private suspend fun setupExample() {
        DatabaseHandler.addList("Example List 1")
        DatabaseHandler.getLists()[0].uid?.let { uid ->
            DatabaseHandler.addItem("Example complete to-do item", true, uid)
            DatabaseHandler.addItem("Example incomplete to-do item", false, uid)
            DatabaseHandler.addItem("Other things to take care of", false, uid)
        }

        DatabaseHandler.addList("Example List 2")
        DatabaseHandler.getLists()[1].uid?.let { uid ->
            DatabaseHandler.addItem("Example complete to-do item", true, uid)
            DatabaseHandler.addItem("Example incomplete to-do item", false, uid)
            DatabaseHandler.addItem("Other things to take care of", false, uid)
        }

        DatabaseHandler.addList("Example List 3")
        DatabaseHandler.getLists()[2].uid?.let { uid ->
            DatabaseHandler.addItem("Example complete to-do item", true, uid)
            DatabaseHandler.addItem("Example incomplete to-do item", false, uid)
            DatabaseHandler.addItem("Other things to take care of", false, uid)
        }
        refresh()
    }

    val lists: LiveData<List<DatabaseHandler.TodoList>> = mutableLists
}
