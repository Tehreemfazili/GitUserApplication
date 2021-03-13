package com.example.gitusersapplication.gitusers

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gitusersapplication.dataSource.models.GitUsers
import com.example.gitusersapplication.databinding.ItemGitUsersBinding

class GitUserAdapter(private var userList: MutableList<GitUsers>, val listener : (GitUsers) -> Unit) :
    RecyclerView.Adapter<GitUserAdapter.ViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemGitUsersBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = userList.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {

        holder.onBind(userList[position], View.OnClickListener { listener(userList[position]) })
    }

    class ViewHolder(val view: ItemGitUsersBinding) : RecyclerView.ViewHolder(view.root) {

        fun onBind(users : GitUsers, listener: View.OnClickListener){
           view.name.text = users.login
            view.url.text = users.url
            view.id.text = users.id.toString()
            view.imgRight.setOnClickListener(listener)
        }
    }

    fun addData(listItems: List<GitUsers>) {
        var size = userList.size
        userList.addAll(listItems)
        var sizeNew = userList.size
        notifyItemRangeChanged(size, sizeNew)
    }

    fun updateList(list: MutableList<GitUsers>) {
        userList = list
        notifyDataSetChanged()
    }

    fun clearItems() {
        userList.clear()
        notifyDataSetChanged()
    }
}