package com.erickson.listmanager.dummy

import java.util.ArrayList

/**
 * Helper class for providing sample content for user interfaces created by
 * Android template wizards.
 *
 * TODO: Replace all uses of this class before publishing your app.
 */
object DummyContent {

    val LISTS: MutableList<ToDoList> = ArrayList()

    var COUNT = 0;
    const val DEFAULT_COUNT = 4

    init {
        // Add some sample items.
        for (i in 1..DEFAULT_COUNT) {
            createDummyItem()
        }
    }

    fun createDummyItem() {
        COUNT++
        LISTS.add(ToDoList(
            COUNT.toString(), "Item $COUNT", listOf(
                ToDoItem("1", "Test - 1"),
                ToDoItem("2", "Test - 2")
            )
        ))
    }

    interface ListableItem{
        val id: String
        val name: String
    }

    data class ToDoItem(override val id: String, override val name: String): ListableItem
    data class ToDoList(override val id: String, override val name: String, val content: List<ToDoItem>): ListableItem
}