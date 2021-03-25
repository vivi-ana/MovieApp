package com.example.movieapplication.ui.blog.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.movieapplication.core.BaseViewHolder
import com.example.movieapplication.data.model.blog.Post
import com.example.movieapplication.databinding.BlogPostItemBinding

class BlogHomeAdapter (private val postList: List<Post>): RecyclerView.Adapter<BaseViewHolder<*>>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val itemBinding = BlogPostItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return BlogHomeViewHolder(itemBinding, parent.context)
    }
    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when(holder){
            is BlogHomeViewHolder -> holder.bind(postList[position])
        }
    }
    override fun getItemCount(): Int = postList.size

    private inner class BlogHomeViewHolder(
        val binding: BlogPostItemBinding,
        val context: Context
    ): BaseViewHolder<Post>(binding.root) {
        override fun bind(item: Post) {
            Glide.with(context).load(item.post_image).centerCrop().into(binding.postImage)
            Glide.with(context).load(item.profile_picture).centerCrop().into(binding.profilePicture)
            binding.profileName.text = item.profile_name
            binding.postTimestamp.text = "Hace 2 horas"
        }
    }
}