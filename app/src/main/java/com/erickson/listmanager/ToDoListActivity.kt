package com.erickson.listmanager

import android.os.Bundle
import android.util.Log
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.RecyclerView
import com.erickson.listmanager.dummy.DummyContent
import com.erickson.listmanager.dummy.DummyContent.addDummyToDo

class ToDoListActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_to_do_list)
        setSupportActionBar(findViewById(R.id.toolbar))

        val index = intent.getIntExtra(ExplorerListFragment.ARG_INDEX, -1)
        this.title = DummyContent.LISTS[index].name

        if(savedInstanceState == null) {
            Log.e("GALEN", "ToDoListActivity adding ExplorerListFragment")
            val bundle = bundleOf(ExplorerListFragment.ARG_INDEX to index)
            supportFragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .replace(R.id.to_do_list_fragment, ExplorerListFragment().apply{
                    arguments = bundle
                })
                .commit()
            Log.e("GALEN", "ToDoListActivity adding ExplorerListFragment done")
        }

        findViewById<FloatingActionButton>(R.id.add_to_do).setOnClickListener { view ->
            addDummyToDo(index)
            (this.supportFragmentManager.findFragmentById(R.id.to_do_list_fragment)?.view as RecyclerView).adapter?.notifyDataSetChanged()
        }
    }
}