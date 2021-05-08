package com.example.todoappmvvm.view.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.todoappmvvm.R
import com.example.todoappmvvm.model.retrofit.Pojo
import kotlinx.android.synthetic.main.item_layout.view.*
import kotlinx.android.synthetic.main.list_layout.view.*

class ItemAdapter(private val callbackInterface: CallbackInterface) :
    RecyclerView.Adapter<ItemAdapter.ItemViewHolder>() {
    var todoItem = mutableListOf<Pojo.Items>()

    interface CallbackInterface {
        fun itemPosition(pos: Int)
        fun deleteItemById(id: Int)
        fun updateItemById(id: Int, list: MutableList<Pojo.Items>)
        fun updateCheckBox(id: Int, state: Pojo.UpdateCheckBoxRequest)
    }

    fun setItems(items: MutableList<Pojo.Items>?) {
        this.todoItem = items!!
        notifyDataSetChanged()
    }

    fun setItem(item: Pojo.Items) {
        todoItem.add(item)
        notifyItemInserted(todoItem.size - 1)
    }
    fun updateItem(id: Int,item:Pojo.Items){
        todoItem.set(id,item)
        notifyItemChanged(id)
    }
    fun deleteItem(id: Int){
        todoItem.removeAt(id)
        notifyItemRemoved(id)
        notifyItemRangeChanged(id, itemCount);
    }



    class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title: TextView = itemView.titleItem
        val description: TextView = itemView.descriptionItem
        val deleteItem: ImageButton = itemView.closeItem
        val updateItem: ImageButton = itemView.updateItem
        val doneItem: CheckBox = itemView.doneItem
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_layout, parent, false)
        return ItemViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) {
        val listItem = todoItem[position]
        val modalTrue = Pojo.UpdateCheckBoxRequest(true)
        val modalFalse = Pojo.UpdateCheckBoxRequest(false)

        holder.deleteItem.setOnClickListener {
            callbackInterface.itemPosition(position)
            callbackInterface.deleteItemById(listItem.id)

        }

        holder.updateItem.setOnClickListener {
            callbackInterface.itemPosition(position)
            callbackInterface.updateItemById(position, todoItem)

        }

        holder.title.text = todoItem[position].title
        holder.description.text = todoItem[position].description

        holder.doneItem.setOnClickListener {

            if (holder.doneItem.isChecked) {
                callbackInterface.updateCheckBox(listItem.id, modalTrue)
                Log.d("RecycAdapterItem", "worked is Checked")
            } else {
                callbackInterface.updateCheckBox(listItem.id, modalFalse)
                Log.d("RecycAdapterItem", "worked is NOT Checked")
            }

        }

        holder.doneItem.isChecked = todoItem[position].done

        /*if (todoItem[position].done) {
            holder.doneItem.text = "Выполнено"
        } else {
            holder.doneItem.text = "Не выполнено"
        }*/

    }

    override fun getItemCount(): Int {
        return todoItem.size
    }
}