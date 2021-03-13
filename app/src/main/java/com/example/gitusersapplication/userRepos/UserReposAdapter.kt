package com.example.gitusersapplication.userRepos

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.gitusersapplication.dataSource.models.UsersRepo
import com.example.gitusersapplication.databinding.ItemUsersReposBinding

class UserReposAdapter(val items: List<UsersRepo>, val listener : (UsersRepo) -> Unit) :
    RecyclerView.Adapter<UserReposAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            ItemUsersReposBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(items[position], View.OnClickListener { listener(items[position]) })
    }

    class ViewHolder(val view: ItemUsersReposBinding) : RecyclerView.ViewHolder(view.root) {

        fun onBind(items : UsersRepo, listener: View.OnClickListener ){

            view.id.text = items.id.toString()
            view.name.text = items.name
            view.url.text = items.url
            view.imgRight.setOnClickListener(listener)
        }

    }
}