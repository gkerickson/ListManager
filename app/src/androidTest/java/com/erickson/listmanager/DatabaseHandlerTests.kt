package com.erickson.listmanager

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.erickson.listmanager.dummy.DatabaseHandler
import com.erickson.listmanager.dummy.DatabaseHandler.addList
import com.erickson.listmanager.dummy.DatabaseHandler.addItem
import com.erickson.listmanager.dummy.DatabaseHandler.getItem
import com.erickson.listmanager.dummy.DatabaseHandler.getItemsForList
import com.erickson.listmanager.dummy.DatabaseHandler.getLists
import com.erickson.listmanager.dummy.DatabaseHandler.updateItem
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
            DatabaseHandler.setup(InstrumentationRegistry.getInstrumentation().targetContext)
        }

        @AfterClass
        @JvmStatic
        @Throws(IOException::class)
        fun afterAll() {
            DatabaseHandler.teardown()
        }
    }

    private val mockListName1 = "name1"
    private val mockListName2 = "name2"
    private val mockTodo1 = "todo1"
    private val mockTodo2 = "todo2"

    @Before
    fun beforeEach() {
        DatabaseHandler.reset()
        addList(mockListName1)
    }

    @Test
    fun getsAddedListsFromDatabase() {
        addList(mockListName2)

        val lists = getLists()
        assertEquals(mockListName1, lists[0].name)
        assertEquals(mockListName2, lists[1].name)
        assertEquals(2, lists.size)
    }

    @Test
    fun getsAddedTodoItemsForListFromDatabase() {
        val list = getLists()[0]
        addItem(mockTodo1, false, list.uid!!)
        addItem(mockTodo2, true, list.uid!!)

        val todos = getItemsForList(list.uid!!)

        assertEquals(mockTodo1, todos[0].name)
        assertEquals(mockTodo2, todos[1].name)
        assertFalse(todos[0].checked)
        assertTrue(todos[1].checked)
    }

    @Test
    fun updatesCheckedValueInDatabaseCorrectly() {
        val checked = true
        val updatedChecked = !checked
        val mockListName1 = "name1"
        addList(mockListName1)
        val listUid = getLists()[0].uid!!

        addItem(mockTodo1, checked, listUid)
        val todoToUpdate = getItemsForList(listUid)[0]

        updateItem(
            DatabaseHandler.TodoItem(
                todoToUpdate.uid,
                mockTodo2,
                updatedChecked,
                todoToUpdate.list_id
            )
        )

        getItemsForList(listUid)[0].let { updatedTodo ->
            assertEquals(updatedChecked, updatedTodo.checked)
            assertEquals(mockTodo2, updatedTodo.name)
        }
    }

    @Test
    fun getListItemBasedOnId() {
        addList(mockListName1)
        val listId = getLists()[0].uid!!
        addItem(mockTodo1, true, listId)
        val itemInList = getItemsForList(listId)[0]

        val itemFromId = getItem(itemInList.uid!!)

        assertEquals(itemInList, itemFromId)
    }
}
