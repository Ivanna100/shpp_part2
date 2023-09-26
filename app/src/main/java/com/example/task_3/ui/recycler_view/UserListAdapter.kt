package com.example.task_3.ui.recycler_view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.task_3.utils.ext.loadImage
import com.example.task_3.databinding.ItemUserBinding
import com.example.task_3.domain.model.User
import com.example.task_3.utils.Constants
import com.example.task_3.utils.MyDiffUtil


class UserListAdapter( private val listener: UserItemClickListener ) :
    ListAdapter<User, UserListAdapter.UsersViewHolder>(MyDiffUtil()) {

    inner class UsersViewHolder(val binding: ItemUserBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bind(user: User) {
            binding.imageViewDelete.setOnClickListener{
                listener.onUserDeleteClick(user, adapterPosition)
            }
            with(binding) {
                textViewName.text = user.name
                textViewCareer.text = user.career
                imageViewUserPhoto.loadImage(user.photo)
            }
        }
    }

    private val users = ArrayList<User>()



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): UsersViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemUserBinding.inflate(inflater, parent, false)
        return UsersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UsersViewHolder, position: Int) {
        holder.bind(getItem(position))
        val user = users[position]
        with(holder.binding) {
//        imageViewDelete.setOnClickListener {
//            val positionUser = holder.adapterPosition
//            listener.onUserDeleteClick(user, positionUser)
//        }
            val arrayOfElements = arrayOf(
                setTransitionName(imageViewUserPhoto, Constants.TRANSITION_NAME_IMAGE + user.id),
                setTransitionName(textViewName, Constants.TRANSITION_NAME_NAME + user.id),
                setTransitionName(textViewCareer, Constants.TRANSITION_NAME_CAREER + user.id)
            )
            root.setOnClickListener {
                listener.onUserClick(user, arrayOfElements)
            }
//            textViewName.text = user.name
//            textViewCareer.text = user.career
//            imageViewUserPhoto.loadImage(user.photo)
        }
    }

    private fun setTransitionName(view: View, name: String) : Pair<View, String> {
        view.transitionName = name
        return view to name
    }

    override fun getItemCount(): Int = users.size

    fun updateUsers(newUsers: ArrayList<User>) {
        users.clear()
        users.addAll(newUsers)
    }
}