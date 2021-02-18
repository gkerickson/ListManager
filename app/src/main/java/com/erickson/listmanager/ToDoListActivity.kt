package com.erickson.listmanager

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.erickson.listmanager.adapters.ToDoListRecyclerViewAdapter
import com.erickson.listmanager.dialogs.CreateListDialogFragment
import com.erickson.listmanager.dialogs.DialogListener
import com.erickson.listmanager.touch.SwipeHelperCallback
import com.erickson.listmanager.viewholders.ListExplorerViewHolder
import com.erickson.listmanager.viewholders.ToDoItemViewHolder
import com.erickson.listmanager.viewmodels.ToDoListViewModel
import com.google.android.material.floatingactionbutton.FloatingActionButton

class ToDoListActivity : AppCompatActivity(), DialogListener {
    lateinit var viewModel: ToDoListViewModel
    private val adapter: ToDoListRecyclerViewAdapter by lazy {
        ToDoListRecyclerViewAdapter(viewModel.todoItems, object :
            ToDoListOnClick {
            override fun onClick(itemId: Int, checked: Boolean) {
                viewModel.setCheckedForItem(itemId, checked)
            }
        })
    }

    interface ToDoListOnClick {
        fun onClick(itemId: Int, checked: Boolean)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_to_do_list)
        setSupportActionBar(findViewById(R.id.toolbar))

        ToDoListViewModel.SELECTED_LIST_UID = intent.getIntExtra(ARG_LIST_UID, -1)

        viewModel = ViewModelProvider(this).get(ToDoListViewModel::class.java)
        viewModel.listTitle.observe(this, Observer {
            this.title = it
        })

        if (savedInstanceState == null) {
            this.findViewById<RecyclerView>(R.id.todo_list_recycler_view).let {
                it.layoutManager = LinearLayoutManager(this)
                it.adapter = adapter
                ItemTouchHelper(object: SwipeHelperCallback() {
                    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                        viewModel.deleteItem((viewHolder as ToDoItemViewHolder).itemId)
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