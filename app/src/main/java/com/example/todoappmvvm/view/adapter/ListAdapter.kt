package com.example.todoappmvvm.view.adapter

import android.content.Context
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.todoappmvvm.R
import com.example.todoappmvvm.model.retrofit.Pojo
import com.example.todoappmvvm.view.activity.ItemActivity
import com.example.todoappmvvm.view.activity.ListActivity
import com.example.todoappmvvm.view.activity.ListDetail
import com.google.gson.Gson
import kotlinx.android.synthetic.main.list_layout.view.*
import java.util.ArrayList

class ListAdapter(private val context: Context) :
    RecyclerView.Adapter<ListAdapter.ListViewHolder>() {
    var todolist = mutableListOf<Pojo.GetAllLists>()
    //private var todolist: MutableList<Pojo.GetAllLists> = arrayListOf()


    fun setLists(lists: MutableList<Pojo.GetAllLists>) {
        todolist.clear()
        todolist.addAll(lists)
        Log.d("From listAdapter", "$lists")
        notifyDataSetChanged()
    }


    class ListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.title
        val description: TextView = itemView.description

        //val deleteList: ImageButton = itemView.close
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
            var intent = Intent(context, ItemActivity::class.java)
            intent.putExtra("listId", listItem.id)
            context.startActivity(intent)
        }

        holder.updateList.setOnClickListener {
            var intent = Intent(context, ListDetail::class.java)
            intent.putExtra("listDetailId", listItem.id)
            intent.putExtra("listDetailIdPosition", position)
            intent.putExtra("listDetailData", todolist as ArrayList<String>)
            context.startActivity(intent)
        }

        holder.title.text = todolist[position].title
        holder.description.text = todolist[position].description


    }

    override fun getItemCount(): Int {
        return todolist.size
    }


}