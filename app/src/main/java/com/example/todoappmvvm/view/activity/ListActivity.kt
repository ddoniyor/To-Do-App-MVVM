package com.example.todoappmvvm.view.activity

import android.app.ActivityOptions
import android.content.Intent
import android.opengl.Visibility
import android.os.Bundle
import android.util.Log
import android.view.View
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
import kotlinx.android.synthetic.main.activity_item.*
import kotlinx.android.synthetic.main.activity_list.*
import kotlinx.android.synthetic.main.toolbaraction.*

class ListActivity : AppCompatActivity(),ListAdapter.CallBackIntent {
    lateinit var mainViewModel: MainViewModel
    lateinit var adapter: ListAdapter
    lateinit var layoutManager: GridLayoutManager
    lateinit var sharedPreferencesHelper: SharedPreferencesHelper


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list)
        setUpViewModel()
        progressbarlist.visibility = View.VISIBLE
        sharedPreferencesHelper = SharedPreferencesHelper(this)


        mainViewModel.listResponse.observe(this, {
            if (it.data != null) {
                progressbarlist.visibility = View.INVISIBLE

                text_view_inList.visibility = View.GONE
                recyclerList.visibility = View.VISIBLE
                layoutManager = GridLayoutManager(this, 2)
                recyclerList.layoutManager = layoutManager
                adapter = ListAdapter(this)
                adapter.setLists(it.data as MutableList<Pojo.GetAllLists>)
                recyclerList.adapter = adapter
            } else {
                progressbarlist.visibility = View.GONE
                recyclerList.visibility = View.GONE
                text_view_inList.visibility = View.VISIBLE
            }
            Log.d("From LIST Activity", "${it.data}")
        })

        itemsswipetorefresh.setOnRefreshListener {

            mainViewModel.getLists()
            itemsswipetorefresh.isRefreshing = false
        }


        fab.setOnClickListener {
            intent = Intent(this, ListDetail::class.java)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right,
                    R.anim.slide_out_left)
        }


        setSupportActionBar(toolbar_acttion)
        supportActionBar?.apply {
            buttonExit.setOnClickListener {
                logOut()
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


    private fun logOut() {
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

    override fun openListDetail(id: Int, position: Int, modal: MutableList<Pojo.GetAllLists>) {
            intent = Intent(this, ListDetail::class.java)
            intent.putExtra("listDetailId", id)
            intent.putExtra("listDetailIdPosition", position)
            intent.putExtra("listDetailData", modal as ArrayList<String>)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left)

    }

    override fun openItemActivity(id: Int) {
            intent = Intent(this, ItemActivity::class.java)
            intent.putExtra("listId", id)
            startActivity(intent)
            overridePendingTransition(R.anim.slide_in_right,
                R.anim.slide_out_left)
    }

}