package com.example.aleks.behancer_kotlin.ui.project

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.aleks.behancer_kotlin.R
import com.example.aleks.behancer_kotlin.data.model.project.Project
import com.example.aleks.behancer_kotlin.ui.project.ProjectsAdapter
import com.example.aleks.behancer_kotlin.utils.formatTime
import com.squareup.picasso.Picasso

class ProjectsHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    private val FIRST_OWNER_INDEX = 0

    private val image = itemView.findViewById<ImageView>(R.id.image)
    private val name = itemView.findViewById<TextView>(R.id.tv_name)
    private val username = itemView.findViewById<TextView>(R.id.tv_username)
    private val publishedOn = itemView.findViewById<TextView>(R.id.tv_published)

    fun bind(item:Project, onItemClickListener: ProjectsAdapter.OnItemClickListener?){
        Picasso.get().load(item.cover?.photoUrl).fit().into(image)
        name.text = item.name
        username.text = item.owners[FIRST_OWNER_INDEX].username
        publishedOn.text = formatTime(item.publishedOn!!)

        itemView.setOnClickListener {onItemClickListener?.onItemClick(
            item.owners[FIRST_OWNER_INDEX].username
        )}
    }

}