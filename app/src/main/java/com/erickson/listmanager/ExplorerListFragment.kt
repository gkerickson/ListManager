package com.erickson.listmanager

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.erickson.listmanager.dummy.DummyContent

class ExplorerListFragment : Fragment() {
    private var index = -1

    private val list
        get() =
            if (index == -1 || index >= DummyContent.LISTS.size) DummyContent.LISTS
            else DummyContent.LISTS[index].content

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.e("GALEN", "ExplorerListFragment onCreate")
        super.onCreate(savedInstanceState)
        arguments?.let {
            index = it.getInt(ARG_INDEX)
        }
        Log.e("GALEN", "ExplorerListFragment onCreate done with index: " + index.toString())
    }

    interface FragmentOnClick {
        fun onClick(index: Int)
    }

    private val fragmentOnClickHandler = object: FragmentOnClick {
        override fun onClick(index: Int) {
            fragmentOnClick(index)
        }
    }

    private fun navigateToList(index: Int) {
        startActivity(
            Intent(activity, ToDoListActivity::class.java).apply {
                putExtra(ARG_INDEX, index)
            }
        )
    }

    private fun navigateToHomeScreen() {
        this.activity?.onBackPressed()
    }

    private fun fragmentOnClick(index: Int) {
        Log.e("GALEN", "ExplorerListFragment fragmentOnClick")
        if(index > -1 && this.index == -1) navigateToList(index)
        else navigateToHomeScreen()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.e("GALEN", "ExplorerListFragment onCreateView")

        val view = inflater.inflate(R.layout.fragment_explorer_list, container, false)

        if (view is RecyclerView) {
            with(view) {
                Log.e("GALEN", "ExplorerListFragment" + list.toString())

                layoutManager = LinearLayoutManager(context)
                adapter = MyExplorerListRecyclerViewAdapter(list, fragmentOnClickHandler)
            }
        }
        return view
    }

    companion object {

        const val ARG_INDEX = "INDEX"

        @JvmStatic
        fun newInstance(index: Int) =
            ExplorerListFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_INDEX, index)
                }
            }
    }
}