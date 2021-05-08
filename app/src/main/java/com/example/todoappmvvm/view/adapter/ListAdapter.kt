package com.example.todoappmvvm.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todoappmvvm.R
import com.example.todoappmvvm.model.retrofit.Pojo
import com.example.todoappmvvm.view.activity.ListActivity
import com.google.gson.Gson
import kotlinx.android.synthetic.main.list_layout.view.*
import java.util.ArrayList

class ListAdapter(private var callBackIntent: CallBackIntent) :
    RecyclerView.Adapter<ListAdapter.ListViewHolder>() {
    var todolist = mutableListOf<Pojo.GetAllLists>()

    interface CallBackIntent{
        fun openListDetail(id:Int,position: Int,modal:MutableList<Pojo.GetAllLists>)
        fun openItemActivity(id: Int)
    }


    fun setLists(lists: MutableList<Pojo.GetAllLists>) {
        todolist.clear()
        todolist.addAll(lists)
        Log.d("From listAdapter", "$lists")
        notifyDataSetChanged()
    }

    fun setItem(item: Pojo.CreateList) {
        todolist.add(Pojo.GetAllLists(item.id, item.title,item.description))
        notifyItemInserted(todolist.size - 1)
    }


    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.title
        val description: TextView = itemView.description
        val updateList: ImageButton = itemView.update
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(
            R.layout.list_layout,
            parent,
            false
        )
        return ListViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {

        var listItem = todolist[position]

        holder.itemView.setOnClickListener {
            listItem.id?.let { it1 -> callBackIntent.openItemActivity(it1) }
        }

        holder.updateList.setOnClickListener {
            listItem.id?.let { it1 -> callBackIntent.openListDetail(it1,position,todolist) }
        }

        holder.title.text = todolist[position].title
        holder.description.text = todolist[position].description


    }

    override fun getItemCount(): Int {
        return todolist.size
    }


}