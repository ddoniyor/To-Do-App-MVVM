package com.example.todoappmvvm.view.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.todoappmvvm.R
import com.example.todoappmvvm.model.repository.MainRepository
import com.example.todoappmvvm.model.retrofit.Pojo
import com.example.todoappmvvm.model.retrofit.RetrofitBuilder
import com.example.todoappmvvm.view.adapter.ListAdapter
import com.example.todoappmvvm.viewmodel.MainViewModel
import com.example.todoappmvvm.viewmodel.ViewModelFactory
import kotlinx.android.synthetic.main.activity_list_detail.*

class ListDetail : AppCompatActivity() {
    var listDetailId: Int? = null
    var listDetailIdPosition: Int? = null
    var listDetailData: MutableList<Pojo.GetAllLists>? = null

    lateinit var mainViewModel: MainViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_list_detail)

        listDetailId = intent.getIntExtra("listDetailId", 0)



        if (listDetailId == 0) {
            delete_list_detail.visibility = View.GONE
            update_list_detail.visibility = View.GONE
            addList()
        } else if (listDetailId != 0) {
            enter_list_detail.visibility = View.GONE
            listDetailIdPosition = intent.getIntExtra("listDetailIdPosition", 0)
            listDetailData =
                intent.getSerializableExtra("listDetailData") as MutableList<Pojo.GetAllLists>
            Toast.makeText(this, "fuckl $listDetailId $listDetailData", Toast.LENGTH_LONG).show()
            deleteList()
            updateListByIdDetail()
        }

        setUpViewModel()

        mainViewModel.postListResponse.observe(this, {
            Log.d("From ListDetail", "POSTED LIST ID RESPONSE ${it.id}")
        })
        mainViewModel.updateListResponse.observe(this, {
            Log.d("From ListDetail", "UPDATED LIST ID RESPONSE ${it.status}")
        })
    }

    private fun addList() {
        enter_list_detail.setOnClickListener {
            val modal = Pojo.CreateList(
                0,
                title_list_detail.text.toString(),
                description_list_detail.text.toString()
            )

            Log.d("From ListDetail", "POSTED LIST DATA  $modal")
            mainViewModel.postList(modal)

            intent = Intent(this, ListActivity::class.java)
            startActivity(intent)
            finishAffinity()


        }
    }

    fun deleteList() {
        delete_list_detail.setOnClickListener {
            mainViewModel.deleteListById(listDetailId)
            intent = Intent(this, ListActivity::class.java)
            startActivity(intent)
            finishAffinity()
        }
    }


    fun updateListByIdDetail() {
        var listItem = listDetailData!![listDetailIdPosition!!]
        title_list_detail.setText(listItem.title)
        description_list_detail.setText(listItem.description)
        Log.d(
            "From ListDetail",
            "CLick update Button ${listItem.title},${listItem.description}"
        )
        update_list_detail.setOnClickListener {
            val model = Pojo.UpdateRequest(
                0,
                title_list_detail.text.toString(),
                description_list_detail.text.toString()
            )

            mainViewModel.updateListById(listDetailId, model)
            intent = Intent(this, ListActivity::class.java)
            startActivity(intent)
            finishAffinity()
        }
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

}