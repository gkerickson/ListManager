package com.erickson.listmanager.model

import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.room.*

object DatabaseHandler {
    lateinit var db: AppDatabase

    fun setup(context: Context) {
        db = Room.databaseBuilder(
            context,
            AppDatabase::class.java, "database-name"
        ).build()
    }

    private val todoDao by lazy { db.todoDao() }

    suspend fun addList(name: String) = todoDao.insertTodoList(TodoList(null, name))

    suspend fun getList(listId: Int): TodoList = todoDao.loadList(listId)

    suspend fun getLists(): List<TodoList> = todoDao.loadLists()

    suspend fun addItem(name: String, checked: Boolean, listId: Int) =
        todoDao.insertTodoItem(TodoItem(null, name, checked, listId))

    suspend fun getItem(itemId: Int): TodoItem? = todoDao.loadListItem(itemId)

    suspend fun getItemsForList(listId: Int): List<TodoItem> = todoDao.loadTodoItems(listId)

    suspend fun updateItem(itemId: Int, checked: Boolean) {
        getItem(itemId)?.apply {
            todoDao.insertTodoItem(
                TodoItem(uid, name, checked, list_id)
            )
        }
    }

    suspend fun deleteTodoItemList(listId: Int) {
        todoDao.deleteTodoList(getList(listId))
    }

    suspend fun deleteTodoItem(listId: Int) {
        getItem(listId)?.let { todoDao.deleteTodoItem(it) }
    }

    @VisibleForTesting
    fun reset() = db.clearAllTables()

    @VisibleForTesting
    fun teardown() = db.close()

    @Entity
    data class TodoItem(
        @PrimaryKey(autoGenerate = true) val uid: Int?,
        @ColumnInfo(name = "name") val name: String,
        @ColumnInfo(name = "checked") val checked: Boolean,
        @ColumnInfo(name = "list_id") val list_id: Int
    )

    @Entity
    data class TodoList(
        @PrimaryKey(autoGenerate = true) val uid: Int?,
        @ColumnInfo(name = "name") val name: String
    )

    @Database(entities = [TodoItem::class, TodoList::class], version = 1)
    abstract class AppDatabase : RoomDatabase() {
        abstract fun todoDao(): TodoDao
    }

    @Dao
    interface TodoDao {
        @Query("SELECT * FROM TodoItem WHERE list_id is (:listId)")
        suspend fun loadTodoItems(listId: Int): List<TodoItem>

        @Query("SELECT * FROM TodoItem WHERE uid is (:itemId)")
        suspend fun loadListItem(itemId: Int): TodoItem

        @Query("SELECT * FROM TodoList WHERE uid is (:listId)")
        suspend fun loadList(listId: Int): TodoList

        @Query("SELECT * FROM TodoList")
        suspend fun loadLists(): List<TodoList>

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun insertTodoItem(item: TodoItem)

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        suspend fun insertTodoList(list: TodoList)

        @Delete
        suspend fun deleteTodoList(list: TodoList)

        @Delete
        suspend fun deleteTodoItem(item: TodoItem)
    }
}