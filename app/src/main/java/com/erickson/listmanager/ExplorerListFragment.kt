package com.erickson.listmanager

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.erickson.listmanager.dummy.DummyContent

class ExplorerListFragment : Fragment(R.layout.fragment_explorer_list) {
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

    private fun fragmentOnClick(index: Int) {
        Log.e("GALEN", "ExplorerListFragment fragmentOnClick")
        this.parentFragmentManager.beginTransaction()
            .replace(R.id.explore_lists_fragment, ExplorerListFragment().also {
                it.arguments = Bundle().apply {
                    putInt(ARG_INDEX, index)
                }
            }, null)
            .setReorderingAllowed(true)
            .addToBackStack("TEST")
            .commit()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.e("GALEN", "ExplorerListFragment onCreateView")

        val view = inflater.inflate(R.layout.fragment_explorer_list, container, false)

        // Set the adapter
        if (view is RecyclerView) {
            with(view) {
                layoutManager = LinearLayoutManager(context)
                adapter = MyExplorerListRecyclerViewAdapter(list, object : FragmentOnClick {
                    override fun onClick(index: Int) {
                        fragmentOnClick(index)
                    }
                })
            }
        }
        return view
    }

    companion object {

        const val ARG_INDEX = "INDEX"

        @JvmStatic
        fun newInstance() =
            ExplorerListFragment().apply {
//                arguments = Bundle().apply {
//                    putInt(ARG_COLUMN_COUNT, columnCount)
//                }
            }
    }
}