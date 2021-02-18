package com.erickson.listmanager

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.erickson.listmanager.adapters.TodoListRecyclerViewAdapter
import com.erickson.listmanager.dialogs.CreateListDialogFragment
import com.erickson.listmanager.dialogs.DialogListener
import com.erickson.listmanager.touch.SwipeHelperCallback
import com.erickson.listmanager.viewholders.TodoItemViewHolder
import com.erickson.listmanager.viewmodels.TodoListViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class TodoListActivity : AppCompatActivity(), DialogListener {
    lateinit var viewModel: TodoListViewModel
    private val adapter: TodoListRecyclerViewAdapter by lazy {
        TodoListRecyclerViewAdapter(viewModel.todoItems, object :
            TodoListOnClick {
            override fun onClick(itemId: Int, checked: Boolean) {
                viewModel.setCheckedForItem(itemId, checked)
            }
        })
    }

    interface TodoListOnClick {
        fun onClick(itemId: Int, checked: Boolean)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_to_do_list)
        setSupportActionBar(findViewById(R.id.toolbar))

        TodoListViewModel.SELECTED_LIST_UID = intent.getIntExtra(ARG_LIST_UID, -1)

        viewModel = ViewModelProvider(this).get(TodoListViewModel::class.java)
        viewModel.listTitle.observe(this, Observer {
            this.title = it
        })

        if (savedInstanceState == null) {
            this.findViewById<RecyclerView>(R.id.todo_list_recycler_view).let {
                it.layoutManager = LinearLayoutManager(this)
                it.adapter = adapter
                ItemTouchHelper(object: SwipeHelperCallback() {
                    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                        viewModel.deleteItem((viewHolder as TodoItemViewHolder).itemId)
                    }
                }).attachToRecyclerView(it)
            }
        }
        viewModel.todoItems.observe(this, Observer {
            adapter.notifyDataSetChanged()
        })

        findViewById<FloatingActionButton>(R.id.add_to_do).setOnClickListener { view ->
            CreateListDialogFragment().show(supportFragmentManager, null)
        }
    }

    override fun onDialogPositiveClick(newItem: String) {
        viewModel.addItem(newItem)
    }

    companion object {
        const val ARG_LIST_UID: String = "UID"
    }
}