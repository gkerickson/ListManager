package com.erickson.listmanager.viewmodels

import androidx.lifecycle.*
import androidx.room.Database
import com.erickson.listmanager.model.DatabaseHandler
import kotlinx.coroutines.launch

class TodoListViewModel: ViewModel() {

    private var mutableTodoItems: MutableLiveData<List<DatabaseHandler.TodoItem>> = object: MutableLiveData<List<DatabaseHandler.TodoItem>>() {
        override fun onActive() {
            viewModelScope.launch {
                refreshItems()

            }
        }
    }

    private suspend fun refreshItems() {
        mutableTodoItems.value = DatabaseHandler.getItemsForList(SELECTED_LIST_UID)
    }

    fun addItem(name: String) {
        viewModelScope.launch {
            DatabaseHandler.addItem(name, false, SELECTED_LIST_UID)
            refreshItems()
        }
    }

    fun deleteItem(itemId: Int) {
        viewModelScope.launch {
            DatabaseHandler.deleteTodoItem(itemId)
            refreshItems()
        }
    }

    fun setCheckedForItem(itemId: Int, checked: Boolean) {
        viewModelScope.launch {
            DatabaseHandler.updateItem(itemId, checked)
            refreshItems()
        }
    }

    var todoItems: LiveData<List<DatabaseHandler.TodoItem>> = mutableTodoItems

    var listTitle: LiveData<String> = liveData {
        emit(DatabaseHandler.getList(SELECTED_LIST_UID).name)
    }

    companion object {
        var SELECTED_LIST_UID: Int = -1
    }
}