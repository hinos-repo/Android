package com.example.roomsample

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.roomsample.database.UserEntity
import com.example.roomsample.databinding.RvItemUserListBinding

class UserListAdapter(var callback: (user: UserEntity) -> Unit, var modify: (user: UserEntity) -> Unit) : ListAdapter<UserEntity, UserListAdapter.ViewHolder>(UserEntityListDiffCallback())
{
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder
    {
        return ViewHolder(RvItemUserListBinding.inflate(LayoutInflater.from(parent.context), parent, false))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int)
    {
        var userEntity = getItem(position)
        holder.bind(
            View.OnClickListener { callback(userEntity) },
            View.OnClickListener { modify(userEntity) },
            userEntity
        )

    }

    class ViewHolder(val binding: RvItemUserListBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(mOnClickListener: View.OnClickListener, mModifyClickListener: View.OnClickListener, mUserEntity: UserEntity) {
            binding.apply {
                clickListener = mOnClickListener
                modifyClickListener = mModifyClickListener
                user = mUserEntity
                executePendingBindings()
            }
        }
    }
}

private class UserEntityListDiffCallback : DiffUtil.ItemCallback<UserEntity>()
{
    override fun areItemsTheSame(oldItem: UserEntity, newItem: UserEntity): Boolean //두 객체가 같은 항목인지 여부를 결정한다.
    {
        return oldItem.userId == newItem.userId
    }

    override fun areContentsTheSame(oldItem: UserEntity, newItem: UserEntity): Boolean // 두 항목의 데이터가 같은지 여부를 결정한다.
    {
        return oldItem == newItem
    }

    override fun getChangePayload(oldItem: UserEntity, newItem: UserEntity): Any? {
        return super.getChangePayload(oldItem, newItem)
    }
}
