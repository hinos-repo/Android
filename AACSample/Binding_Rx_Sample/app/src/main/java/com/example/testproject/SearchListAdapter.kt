package com.example.testproject

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.testproject.databinding.ItemSearchBinding
import com.example.testproject.util.SearchDiffUtilCallback

class SearchListAdapter : RecyclerView.Adapter<SearchListAdapter.SearchViewHolder>()
{
    private val m_arrSearchList = mutableListOf<SearchData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) = SearchViewHolder(
        ItemSearchBinding.inflate(LayoutInflater.from(parent.context), parent, false)
    )

    override fun onBindViewHolder(holder: SearchViewHolder, position: Int)
    {
        holder.bindViewHolder(m_arrSearchList[position])
    }

    override fun getItemCount(): Int
    {
        return m_arrSearchList.size
    }

    class SearchViewHolder(private val viewBinding : ItemSearchBinding) : RecyclerView.ViewHolder(
            viewBinding.root
    ) {
        fun bindViewHolder(item: SearchData)
        {
            viewBinding.searchData = item
            viewBinding.executePendingBindings()
        }
    }

    private fun setNewData(newData : MutableList<SearchData>)
    {
        m_arrSearchList.run {
            clear()
            addAll(newData)
        }
    }

    private fun calDiff(newData : MutableList<SearchData>)
    {
        val searchDiffCallback = SearchDiffUtilCallback(m_arrSearchList, newData)
        val diffResult : DiffUtil.DiffResult = DiffUtil.calculateDiff(searchDiffCallback)
        diffResult.dispatchUpdatesTo(this)
    }

    private fun shuffleData()
    {
        val newData = mutableListOf<SearchData>().apply {
            addAll(m_arrSearchList)
            shuffle()
        }

        calDiff(newData)
        setNewData(newData)
    }

    fun searchData(totalData : MutableList<SearchData>, strKeyword : String)
    {
        val newData = totalData.filter {
            it.strSeq.contains(strKeyword)
        }.toMutableList()

        calDiff(newData)
        setNewData(newData)
    }
}
