package com.erickson.listmanager.dummy

import android.content.Context
import androidx.annotation.VisibleForTesting
import androidx.room.*

object DatabaseHandler {

    private lateinit var db: AppDatabase

    fun setup(context: Context) {
        db = Room.databaseBuilder(
            context,
            AppDatabase::class.java, "database-name"
        ).build()
    }

    private val todoDao by lazy { db.todoDao() }

    fun addList(name: String) =
        todoDao.insertToDoList(TodoList(null, name))

    fun getLists(): List<TodoList> = todoDao.loadLists()

    fun addItem(name: String, checked: Boolean, listId: Int) =
        todoDao.insertToDoItem(TodoItem(null, name, checked, listId))

    fun updateItem(item: TodoItem) =
        todoDao.insertToDoItem(item)

    fun getItemsForList(listId: Int): List<TodoItem> = todoDao.loadToDoItems(listId)

    fun getItem(itemId: Int): TodoItem = todoDao.loadListItem(itemId)

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
        @ColumnInfo(name="name") val name: String
    )

    @Database(entities = [TodoItem::class, TodoList::class], version = 1)
    abstract class AppDatabase : RoomDatabase() {
        abstract fun todoDao(): TodoDao
    }

    @Dao
    interface TodoDao {
        @Query("SELECT * FROM TodoItem WHERE list_id is (:listId)")
        fun loadToDoItems(listId: Int): List<TodoItem>

        @Query("SELECT * FROM TodoItem WHERE uid is (:itemId)")
        fun loadListItem(itemId: Int): TodoItem

        @Query("SELECT * FROM TodoList")
        fun loadLists(): List<TodoList>

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        fun insertToDoItem(item: TodoItem)

        @Insert(onConflict = OnConflictStrategy.REPLACE)
        fun insertToDoList(list: TodoList)
    }
}