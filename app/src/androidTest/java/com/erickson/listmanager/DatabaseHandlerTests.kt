package com.erickson.listmanager

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.erickson.listmanager.dummy.DatabaseHandler
import com.erickson.listmanager.dummy.DatabaseHandler.addList
import com.erickson.listmanager.dummy.DatabaseHandler.addTodo
import com.erickson.listmanager.dummy.DatabaseHandler.db
import com.erickson.listmanager.dummy.DatabaseHandler.getTodosForList
import com.erickson.listmanager.dummy.DatabaseHandler.loadLists
import com.erickson.listmanager.dummy.DatabaseHandler.updateTodo
import org.junit.*
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class DatabaseHandlerTests {
    companion object {
        @BeforeClass
        @JvmStatic
        fun beforeAll() {
            DatabaseHandler.context = InstrumentationRegistry.getInstrumentation().targetContext
        }

        @AfterClass
        @JvmStatic
        @Throws(IOException::class)
        fun afterAll() {
            db.close()
        }
    }

    @Before
    fun beforeEach() {
        db.clearAllTables()
    }

    @Test
    fun useAppContext() {
        // Context of the app under test.
        val appContext = InstrumentationRegistry.getInstrumentation().targetContext
        assertEquals("com.erickson.listmanager", appContext.packageName)
    }

    @Test
    fun getsAddedValuesBackFromDatabase() {
        val mockListName1 = "name1"
        val mockListName2 = "name2"

        addList(mockListName1)
        addList(mockListName2)

        val lists = loadLists()
        assertEquals(mockListName1, lists[0].name)
        assertEquals(mockListName2, lists[1].name)
        assertEquals(2, lists.size)
    }

    @Test
    fun getsAddedTodoItemsFromDatabase() {
        val mockTodo1 = "todo1"
        val mockTodo2 = "todo2"
        val mockListName1 = "name1"
        addList(mockListName1)
        val list = loadLists()[0]

        addTodo(mockTodo1, false, list.uid!!)
        addTodo(mockTodo2, true, list.uid!!)
        val todos = getTodosForList(list.uid!!)

        assertEquals(mockTodo1, todos[0].name)
        assertEquals(mockTodo2, todos[1].name)
        assertFalse(todos[0].checked)
        assertTrue(todos[1].checked)
    }

    @Test
    fun updatesCheckedValueInDatabaseCorrectly() {
        val checked = true
        val updatedChecked = !checked
        val mockItemName = "some-todo"
        val mockChangedName = "new-todo"
        val mockListName1 = "name1"
        addList(mockListName1)
        val list = loadLists()[0]

        addTodo(mockItemName, checked, list.uid!!)
        val todoToUpdate = getTodosForList(list.uid!!)[0]

        updateTodo(
            DatabaseHandler.TodoItem(
                todoToUpdate.uid,
                mockChangedName,
                updatedChecked,
                todoToUpdate.list_id
            )
        )

        getTodosForList(list.uid!!)[0].let { updatedTodo ->
            assertEquals(!checked, updatedTodo.checked)
            assertEquals(mockChangedName, updatedTodo.name)
        }
    }
}
