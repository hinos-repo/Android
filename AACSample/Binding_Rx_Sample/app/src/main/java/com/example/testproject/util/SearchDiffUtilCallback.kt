package com.example.testproject.util

import androidx.recyclerview.widget.DiffUtil
import com.example.testproject.SearchData

class SearchDiffUtilCallback(
        private val oldSearch: List<SearchData>,
        private val newSearch: List<SearchData>
) : DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean // 두 객체가 동일한 항목을 나타내는지 확인
    {
        return oldSearch[oldItemPosition] == newSearch[newItemPosition]
    }

    override fun getOldListSize(): Int // 바뀌 기 전 리스트의 크기를 리턴
    {
        return oldSearch.size
    }

    override fun getNewListSize(): Int // 바뀐 후 리스트의 크기를 리턴
    {
        return newSearch.size
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean //두 항목의 데이터가 같은지 확인 (areItemsTheSame = true일 때만 호출)
    {
        return oldSearch[oldItemPosition].strSeq == newSearch[newItemPosition].strSeq
    }
}