package com.example.todoappmvvm.view.activity

import android.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.todoappmvvm.R
import com.example.todoappmvvm.model.repository.MainRepository
import com.example.todoappmvvm.model.retrofit.Pojo
import com.example.todoappmvvm.model.retrofit.RetrofitBuilder
import com.example.todoappmvvm.view.adapter.ItemAdapter
import com.example.todoappmvvm.viewmodel.MainViewModel
import com.example.todoappmvvm.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.activity_item.*
import kotlinx.android.synthetic.main.activity_list.*

class ItemActivity : AppCompatActivity(),ItemAdapter.CallbackInterface{
    lateinit var mainViewModel: MainViewModel
    lateinit var adapter : ItemAdapter
    lateinit var layoutManager: LinearLayoutManager
    var listId: Int = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_item)
        setUpViewModel()
        listId = intent.getIntExtra("listId", 0)

        mainViewModel.getItems(listId)

        mainViewModel.getAllitemsResponse.observe(this,{
            showItems(it.items as? MutableList<Pojo.GetAllItems>)
        })
        mainViewModel.createItemResponse.observe(this,{
            Log.d("TAAAAAAAAAAAAG","POSTED ITEM ID ${it.id}")
        })
        fabItem.setOnClickListener {
            addItem()
        }

    }

    private fun showItems(it: MutableList<Pojo.GetAllItems>?) {
        if (it !=null){
            text_view_inItem.visibility = View.GONE
            recyclerItem.visibility = View.VISIBLE
            recyclerItem.setHasFixedSize(true)
            layoutManager = LinearLayoutManager(this)
            recyclerItem.layoutManager = layoutManager
            adapter = ItemAdapter(this)
            recyclerItem.adapter = adapter
            adapter.setItems(it)

        }else {
            recyclerItem.visibility = View.GONE
            text_view_inItem.visibility = View.VISIBLE
        }
    }

    private fun setUpViewModel() {
        mainViewModel =
                ViewModelProvider(this, ViewModelFactory(MainRepository(RetrofitBuilder().getApiInterface(this)))).get(
                        MainViewModel::class.java
                )
    }

    private fun addItem() {
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Добавить задачу")
        alertDialog.setMessage("Добавьте описание к задаче")
        val layout = LinearLayout(this)
        layout.orientation = LinearLayout.VERTICAL
        val textTitle = EditText(this)
        val textDescription = EditText(this)
        layout.addView(textTitle)
        layout.addView(textDescription)
        alertDialog.setView(layout)

        alertDialog.setPositiveButton("Добавить"){dialog,i->
            val modal = Pojo.CreateItem(0,textTitle.text.toString(),textDescription.text.toString())
            mainViewModel.postItem(listId,modal)
            dialog.dismiss()
            //mainViewModel.getItems(listId)
        }
        alertDialog.show()
    }

    override fun deleteItemById(id: Int) {
        mainViewModel.deleteItemById(id)
        //mainViewModel.getItems(listId)
    }

    override fun updateItemById(id: Int, list: MutableList<Pojo.GetAllItems>) {
        var listItem = list[id]
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Изменить задачу")
        alertDialog.setMessage("Измените название задачи и описание")
        var layout = LinearLayout(this)
        layout.orientation =LinearLayout.VERTICAL
        val textTitle = EditText(this)
        val textDescription = EditText(this)
        layout.addView(textTitle)
        layout.addView(textDescription)
        textTitle.setText(listItem.title)
        textDescription.setText(listItem.description)
        alertDialog.setView(layout)
        alertDialog.setPositiveButton("Изменить"){dialog,i->
            val modal = Pojo.UpdateItemRequest(
                    textTitle.text.toString(),
                    textDescription.text.toString())
            mainViewModel.updateItemById(listItem.id,modal)
            dialog.dismiss()
            //mainViewModel.getItems(listId)
        }
        alertDialog.show()
    }

    override fun updateCheckBox(id: Int, state: Pojo.UpdateCheckBoxRequest) {
        mainViewModel.updateCheckBoxById(id,state)
        //mainViewModel.getItems(listId)
    }
}