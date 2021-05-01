package com.example.todoappmvvm.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.todoappmvvm.R
import com.example.todoappmvvm.model.retrofit.Pojo
import kotlinx.android.synthetic.main.list_layout.view.*

class ListAdapter(private val callbackInterface:CallbackInterface):RecyclerView.Adapter<ListAdapter.ListViewHolder>(){
    var todolist = mutableListOf<Pojo.GetAllLists>()


    interface CallbackInterface {
        fun deleteListById(id: Int)
        fun updateListById(id:Int,list:MutableList<Pojo.GetAllLists>)
        fun openItemActivity(id:Int)
    }


    fun setLists(lists: MutableList<Pojo.GetAllLists>){
        /*val diffCallBack = ListsDiffUtil(todolist ,lists )
        val diffResult = DiffUtil.calculateDiff(diffCallBack)

        todolist.addAll(lists)
        diffResult.dispatchUpdatesTo(this)*/
        todolist.addAll(lists)
        Log.d("Tadas","$lists")
        notifyDataSetChanged()

    }
    fun insertData(lists: MutableList<Pojo.GetAllLists>){
        val diffCallBack = ListsDiffUtil(todolist ,lists)
        val diffResult = DiffUtil.calculateDiff(diffCallBack)
        //todolist.remove(todolist)
        todolist.clear()
        todolist.addAll(lists)
        diffResult.dispatchUpdatesTo(this)
    }

    fun deleteData(lists: MutableList<Pojo.GetAllLists>){
        val diffCallBack = ListsDiffUtil(todolist ,lists)
        val diffResult = DiffUtil.calculateDiff(diffCallBack)
        todolist.clear()
        todolist.addAll(lists)
        diffResult.dispatchUpdatesTo(this)
    }

    class ListViewHolder(itemView: View):RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.title
        val description: TextView = itemView.description
        val deleteList: ImageButton = itemView.close
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

        holder.itemView.setOnClickListener{
            callbackInterface.openItemActivity(listItem.id!!)
        }

        holder.deleteList.setOnClickListener{
            Log.d("TAAAAAAAAAAAAG","КЛИИИИИИИИИИИИИИИИК УДАЛИИТЬ ${listItem.id}")
            callbackInterface.deleteListById(listItem.id!!)
        }

        holder.updateList.setOnClickListener{
            Log.d("TAAAAAAAAAAAAG","КЛИИИИИИИИИИИИИИИИК ИЗМЕНИТЬ ${listItem.id}")
            callbackInterface.updateListById(position,todolist)
        }

        holder.title.text = todolist[position].title
        holder.description.text = todolist[position].description




    }

    override fun getItemCount(): Int {
        return todolist.size
    }



}