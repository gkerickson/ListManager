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
    const val DEFAULT_COUNT = 2

    init {
        // Add some sample items.
        for (i in 1..DEFAULT_COUNT) {
            createDummyList()
        }
    }

    private fun createDummyToDo(): ToDoItem {
        val randomChar: String = ('a' + kotlin.random.Random.nextInt() % 26).toString()
        return ToDoItem(randomChar, "TEST - $randomChar")
    }

    fun addDummyToDo(listIndex: Int) {
        LISTS[listIndex].content.add(createDummyToDo())
    }

    fun createDummyList() {
        COUNT++
        LISTS.add(
            ToDoList(
                COUNT.toString(), "Item $COUNT", mutableListOf(
                    createDummyToDo(),
                    createDummyToDo(),
                    createDummyToDo()
                )
            )
        )
    }

    interface ListableItem {
        val id: String
        val name: String
    }

    data class ToDoItem(override val id: String, override val name: String) : ListableItem
    data class ToDoList(
        override val id: String = (++COUNT).toString(),
        override val name: String,
        val content: MutableList<ToDoItem> = mutableListOf()
    ) : ListableItem
}