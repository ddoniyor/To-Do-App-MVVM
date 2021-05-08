package com.example.todoappmvvm.view.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoappmvvm.R
import com.example.todoappmvvm.model.repository.MainRepository
import com.example.todoappmvvm.model.retrofit.Pojo
import com.example.todoappmvvm.model.retrofit.RetrofitBuilder
import com.example.todoappmvvm.view.adapter.ItemAdapter
import com.example.todoappmvvm.viewmodel.MainViewModel
import com.example.todoappmvvm.viewmodel.ViewModelFactory
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import kotlinx.android.synthetic.main.activity_item.*
import kotlinx.android.synthetic.main.item_layout.*


class ItemActivity : AppCompatActivity(), ItemAdapter.CallbackInterface {
    lateinit var mainViewModel: MainViewModel
    lateinit var adapter: ItemAdapter
    lateinit var layoutManager: LinearLayoutManager
    var listId: Int = 1
    var globItemPosition:Int = 0
    lateinit var globalModal:Pojo.Items


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item)
        progressbaritem.visibility = View.VISIBLE
        setUpViewModel()

        layoutManager = LinearLayoutManager(this)
        recyclerItem.layoutManager = layoutManager
        adapter = ItemAdapter(this)
        recyclerItem.adapter = adapter

        listId = intent.getIntExtra("listId", 0)

        mainViewModel.getItems(listId)

        mainViewModel.getAllitemsResponse.observe(this, {
            showItems(it.items as? MutableList<Pojo.Items>)
        })

        mainViewModel.createItemResponse.observe(this, {
            Log.d("ItemAct", "CREATE item ID${it.id}")
            globalModal.id = it.id!!
            adapter.setItem(globalModal)
            //modalGlobal.id = it.id!!
            //mainViewModel.getItems(listId)
        })

        mainViewModel.deleteItemByIdResponse.observe(this, {
            Log.d("ItemAct", "DELETE ITEM STATUSS ${it.status}")
            adapter.deleteItem(globItemPosition)
            //mainViewModel.getItems(listId)
        })
        mainViewModel.updateItemByIdResponse.observe(this, {
            adapter.updateItem(globItemPosition, globalModal)
            //mainViewModel.getItems(listId)
        })
        mainViewModel.updateCheckBoxByIdResponse.observe(this, {
            //mainViewModel.getItems(listId)
        })


        fabItem.setOnClickListener {
            addItem()
        }

    }

    private fun showItems(it: MutableList<Pojo.Items>?) {
        if (it != null) {
            progressbaritem.visibility = View.GONE
            text_view_inItem.visibility = View.GONE
            recyclerItem.visibility = View.VISIBLE


            adapter.setItems(it)

        } else {
            progressbaritem.visibility = View.GONE
            recyclerItem.visibility = View.VISIBLE
            text_view_inItem.visibility = View.VISIBLE
        }
    }

    private fun addItem() {
        var layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        val textTitle = EditText(this)
        val textDescription = EditText(this)
        layout.addView(textTitle)
        layout.addView(textDescription)

        MaterialAlertDialogBuilder(this)
                .setTitle("Добавить задачу")
                .setMessage("Добавьте описание к задаче")
                .setView(layout)
                .setPositiveButton("Добавить") { dialog, which ->
                    if (textTitle.text.toString().isNotEmpty()&&textDescription.text.toString().isNotEmpty()){
                        val modal =
                            Pojo.Items(0, textTitle.text.toString(), textDescription.text.toString())
                        globalModal = modal
                        mainViewModel.postItem(listId, modal)
                        //dialog.dismiss()
                    }else{
                        Toast.makeText(this, "Заполните все поля задачи", Toast.LENGTH_SHORT).show()
                    }

                }.show()
    }

    override fun updateItemById(id: Int, list: MutableList<Pojo.Items>) {
        var listItem = list[id]
        var layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        val textTitle = EditText(this)
        val textDescription = EditText(this)
        layout.addView(textTitle)
        layout.addView(textDescription)
        textTitle.setText(listItem.title)
        textDescription.setText(listItem.description)

        MaterialAlertDialogBuilder(this)
                .setTitle("Изменить задачу")
                .setMessage("Измените название задачи и описание")
                .setView(layout)
                .setPositiveButton("Изменить") { dialog, which ->
                    if (textTitle.text.toString().isNotEmpty() && textDescription.text.toString().isNotEmpty()){
                        val modal = Pojo.Items(
                            0,
                            textTitle.text.toString(),
                            textDescription.text.toString()
                        )
                        globalModal = modal
                        mainViewModel.updateItemById(listItem.id, modal)
                    }else{
                        Toast.makeText(this, "Заполните все поля задачи", Toast.LENGTH_SHORT).show()
                    }
                }.show()
    }

    override fun itemPosition(position: Int) {
        globItemPosition = position
    }

    override fun deleteItemById(id: Int) {
        mainViewModel.deleteItemById(id)

    }

    override fun updateCheckBox(id: Int, state: Pojo.UpdateCheckBoxRequest) {
        mainViewModel.updateCheckBoxById(id, state)
        //mainViewModel.getItems(listId)
    }

    private fun setUpViewModel() {
        mainViewModel =
                ViewModelProvider(
                        this,
                        ViewModelFactory(MainRepository(RetrofitBuilder().getApiInterface(this)))
                ).get(
                        MainViewModel::class.java
                )
    }
    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_right)
    }
}