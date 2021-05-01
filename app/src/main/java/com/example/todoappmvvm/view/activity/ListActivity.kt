package com.example.todoappmvvm.view.activity

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.todoappmvvm.R
import com.example.todoappmvvm.SharedPreferencesHelper
import com.example.todoappmvvm.model.repository.MainRepository
import com.example.todoappmvvm.model.retrofit.Pojo
import com.example.todoappmvvm.model.retrofit.RetrofitBuilder
import com.example.todoappmvvm.view.adapter.ListAdapter
import com.example.todoappmvvm.viewmodel.MainViewModel
import com.example.todoappmvvm.viewmodel.ViewModelFactory
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.toolbaraction.*

class ListActivity : AppCompatActivity(), ListAdapter.CallbackInterface {
    lateinit var mainViewModel: MainViewModel
    lateinit var adapter :ListAdapter
    lateinit var layoutManager: GridLayoutManager
    lateinit var sharedPreferencesHelper: SharedPreferencesHelper
    lateinit var dialog: AlertDialog
    private var dataFromApi: MutableList<Pojo.GetAllLists>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        sharedPreferencesHelper = SharedPreferencesHelper(this)


        setUpViewModel()
        dialog = SpotsDialog.Builder().setCancelable(true).setContext(this).build()



        mainViewModel.listResponse.observe(this, {

            if (it.data !=null){
                dataFromApi  = it.data as MutableList<Pojo.GetAllLists>
                text_view_inList.visibility = View.GONE
                recyclerList.visibility = View.VISIBLE
                //recyclerList.setHasFixedSize(true)
                layoutManager = GridLayoutManager(this, 2)
                recyclerList.layoutManager = layoutManager
                adapter = ListAdapter(this)
                adapter.setLists(it.data as MutableList<Pojo.GetAllLists>)
                recyclerList.adapter = adapter

            }else {
                recyclerList.visibility = View.GONE
                text_view_inList.visibility = View.VISIBLE
            }


            Log.d("TAAAAAAAAAAAAG", "${it.data}")
        })

        fab.setOnClickListener{
            addList()
        }

        mainViewModel.deleteListResponse.observe(this, {
            Toast.makeText(this, "${it.status}", Toast.LENGTH_LONG).show()
        })
        mainViewModel.postListResponse.observe(this, {
            Log.d("TAAAAAAAAAAAAG", "POSTED LIST ID ${it.id}")
        })



        setSupportActionBar(toolbar_acttion)
        supportActionBar?.apply {
            buttonExit.setOnClickListener {
                sharedPreferencesHelper.clearDataSharedLogin()
                sharedPreferencesHelper.clearDataShared()
                openLoginActivity()
                Toast.makeText(baseContext, "Клик выход", Toast.LENGTH_LONG).show()
            }
        }

        adapter = ListAdapter(this)
        mainViewModel.getLists()

    }

    /*override fun onStart() {
        super.onStart()
        mainViewModel.getLists()
    }*/


    private fun setUpViewModel() {

        mainViewModel =
                ViewModelProvider(this, ViewModelFactory(MainRepository(RetrofitBuilder().getApiInterface(this)))).get(
                        MainViewModel::class.java
                )
    }

    private fun showList(it: MutableList<Pojo.GetAllLists>?){


    }
    private fun addList(){
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Добавить список")
        alertDialog.setMessage("Добавьте описание к списку")
        val layout = LinearLayout(this)
        layout.orientation =LinearLayout.VERTICAL
        val textTitle = EditText(this)
        val textDescription = EditText(this)
        layout.addView(textTitle)
        layout.addView(textDescription)
        alertDialog.setView(layout)
        //val list : MutableList<Pojo.GetAllLists>? = dataFromApi

        alertDialog.setPositiveButton("Добавить"){ dialog, i->
            val modal = Pojo.GetAllLists(0, textTitle.text.toString(), textDescription.text.toString())
            mainViewModel.postList(modal)

            dialog.dismiss()


            /*dataFromApi?.add(modal)
            if (dataFromApi != null) {
                adapter.insertData(dataFromApi!!)
            }*/

            //adapter.notifyItemInserted(id)
            mainViewModel.getLists()
        }
        alertDialog.show()
    }



    override fun deleteListById(id: Int) {
        mainViewModel.deleteListById(id)

        mainViewModel.getLists()
    }

    override fun updateListById(id: Int, list: MutableList<Pojo.GetAllLists>) {
        var listItem = list[id]
        val alertDialog = AlertDialog.Builder(this)
        alertDialog.setTitle("Изменить список")
        alertDialog.setMessage("Измените название списка и описание")
        var layout = LinearLayout(this)
        layout.orientation =LinearLayout.VERTICAL
        val textTitle = EditText(this)
        val textDescription = EditText(this)
        layout.addView(textTitle)
        layout.addView(textDescription)
        textTitle.setText(listItem.title)
        textDescription.setText(listItem.description)
        alertDialog.setView(layout)
        alertDialog.setPositiveButton("Изменить"){ dialog, i->
            val modal = Pojo.GetAllLists(
                    null,
                    textTitle.text.toString(),
                    textDescription.text.toString())
                mainViewModel.updateListById(listItem.id, modal)
            dialog.dismiss()

            dataFromApi?.add(modal)
            if (dataFromApi != null) {
                adapter.insertData(dataFromApi!!)
            }
            //adapter.notifyItemChanged(id,modal)
            mainViewModel.getLists()
        }
        alertDialog.show()
    }

    override fun openItemActivity(id: Int) {
        Toast.makeText(this, "list id $id", Toast.LENGTH_LONG).show()
        intent = Intent(this, ItemActivity::class.java)
        intent.putExtra("listId", id)
        startActivity(intent)
    }

    private fun openLoginActivity() {
        intent = Intent(this, LoginActivity::class.java)
        //intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finishAffinity()
    }

}