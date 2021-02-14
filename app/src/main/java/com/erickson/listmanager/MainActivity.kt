package com.erickson.listmanager

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.recyclerview.widget.RecyclerView
import com.erickson.listmanager.dummy.DummyContent
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.e("GALEN", "MainActivity onCreate")

        if(savedInstanceState == null) {
            Log.e("GALEN", "MainActivity adding ExplorerListFragment")
            val bundle = bundleOf(ExplorerListFragment.ARG_INDEX to -1)
            supportFragmentManager.beginTransaction()
                .setReorderingAllowed(true)
                .add(R.id.explore_lists_fragment, ExplorerListFragment())
                .commit()
            Log.e("GALEN", "MainActivity adding ExplorerListFragment done")
        }

        this.findViewById<FloatingActionButton>(R.id.add_list).setOnClickListener { _ ->
            DummyContent.createDummyList()
            (this.supportFragmentManager.findFragmentById(R.id.explore_lists_fragment)?.view as RecyclerView).adapter?.notifyDataSetChanged()
        }
    }
}