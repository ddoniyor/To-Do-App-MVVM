package com.example.todoappmvvm.view.activity

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
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
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dmax.dialog.SpotsDialog
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.toolbaraction.*
import kotlinx.coroutines.delay

class ListActivity : AppCompatActivity() {
    lateinit var mainViewModel: MainViewModel
    lateinit var adapter: ListAdapter
    lateinit var layoutManager: GridLayoutManager
    lateinit var sharedPreferencesHelper: SharedPreferencesHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        sharedPreferencesHelper = SharedPreferencesHelper(this)
        setUpViewModel()


        mainViewModel.listResponse.observe(this, {
            if (it.data != null) {
                text_view_inList.visibility = View.GONE
                recyclerList.visibility = View.VISIBLE
                layoutManager = GridLayoutManager(this, 2)
                recyclerList.layoutManager = layoutManager
                adapter = ListAdapter(this)
                adapter.setLists(it.data as MutableList<Pojo.GetAllLists>)
                recyclerList.adapter = adapter
            } else {
                recyclerList.visibility = View.GONE
                text_view_inList.visibility = View.VISIBLE
            }

            Log.d("From LIST Activity", "${it.data}")
        })

        fab.setOnClickListener {
            intent = Intent(this, ListDetail::class.java)
            startActivity(intent)
        }


        setSupportActionBar(toolbar_acttion)
        supportActionBar?.apply {
            buttonExit.setOnClickListener {
                openLoginActivity()
            }
        }

        mainViewModel.getLists()
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


    private fun openLoginActivity() {
        MaterialAlertDialogBuilder(this)
            .setTitle("Выход")
            .setMessage("Вы уверены что хотите выйти?")
            .setNeutralButton("Закрыть") { dialog, which ->
                dialog.cancel()
            }
            .setNegativeButton("Нет") { dialog, which ->
                dialog.cancel()
            }
            .setPositiveButton("Да") { dialog, which ->
                sharedPreferencesHelper.clearDataSharedLogin()
                sharedPreferencesHelper.clearDataShared()
                intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finishAffinity()
                dialog.cancel()
            }
            .show()
    }

}