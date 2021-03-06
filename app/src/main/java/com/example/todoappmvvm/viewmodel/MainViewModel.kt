package com.example.todoappmvvm.viewmodel

import android.content.Context
import android.content.Intent
import android.os.SystemClock
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todoappmvvm.model.repository.MainRepository
import com.example.todoappmvvm.model.retrofit.Pojo
import com.example.todoappmvvm.view.activity.ListActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainViewModel(private val repository: MainRepository) : ViewModel() {

    val signInResponse = MutableLiveData<Pojo.LoginResponse>()
    val signUpResponse = MutableLiveData<Pojo.UserInfoRegister>()
    val listResponse = MutableLiveData<Pojo.GetAllListResponse>()
    val postListResponse = MutableLiveData<Pojo.CreateListResponse>()
    val deleteListResponse = MutableLiveData<Pojo.DeleteResponse>()
    val updateListResponse = MutableLiveData<Pojo.UpdateResponse>()

    val getAllitemsResponse = MutableLiveData<Pojo.GetAllItemsResponse>()
    val createItemResponse = MutableLiveData<Pojo.CreateItemResponse>()
    val deleteItemByIdResponse = MutableLiveData<Pojo.DeleteItemResponse>()
    val updateItemByIdResponse = MutableLiveData<Pojo.UpdateItemResponse>()
    val updateCheckBoxByIdResponse = MutableLiveData<Pojo.UpdateCheckBoxResponse>()
    val errorMessage = MutableLiveData<String>()
    var errorMessageToken = MutableLiveData<String>()

    fun signIn(modal: Pojo.LoginRequest) {

        val response = repository.signIn(modal)
        response.enqueue(object : Callback<Pojo.LoginResponse> {
            override fun onResponse(
                call: Call<Pojo.LoginResponse>,
                response: Response<Pojo.LoginResponse>
            ) {
                if (response.body()?.token != null) {
                    Log.d("TAAAG", "${response.body()}")
                    signInResponse.postValue(response.body())
                }
            }

            override fun onFailure(call: Call<Pojo.LoginResponse>, t: Throwable) {
                errorMessageToken.postValue(t.message)
            }
        })
    }

    fun signUp(modal: Pojo.UserInfoRegister) {
        val response = repository.signUp(modal)
        response.enqueue(object : Callback<Pojo.UserInfoRegister> {
            override fun onResponse(
                call: Call<Pojo.UserInfoRegister>,
                response: Response<Pojo.UserInfoRegister>
            ) {
                signUpResponse.postValue(response.body())
            }

            override fun onFailure(call: Call<Pojo.UserInfoRegister>, t: Throwable) {
                errorMessage.postValue(t.message)
            }

        })
    }

    fun getLists() {
        val response = repository.getLists()
        response.enqueue(object : Callback<Pojo.GetAllListResponse?> {
            override fun onResponse(
                call: Call<Pojo.GetAllListResponse?>,
                response: Response<Pojo.GetAllListResponse?>
            ) {
                Log.d("From ViewModel GetList", "method getLists() ${response.body()}")
                listResponse.postValue(response.body())
              }

            override fun onFailure(call: Call<Pojo.GetAllListResponse?>, t: Throwable) {
                errorMessage.postValue(t.message)
            }

        })
    }

    fun postList(modal: Pojo.CreateList) {
        val response = repository.postList(modal)
        response.enqueue(object : Callback<Pojo.CreateListResponse> {
            override fun onResponse(
                call: Call<Pojo.CreateListResponse>,
                response: Response<Pojo.CreateListResponse>
            ) {

                Log.d("From ViewModel PostList", "method postList() ${response.body()}")
                postListResponse.postValue(response.body())

            }

            override fun onFailure(call: Call<Pojo.CreateListResponse>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })
    }

    fun deleteListById(id: Int?) {
        val response = repository.deleteListById(id)
        response.enqueue(object : Callback<Pojo.DeleteResponse> {
            override fun onResponse(
                call: Call<Pojo.DeleteResponse>,
                response: Response<Pojo.DeleteResponse>
            ) {
                deleteListResponse.postValue(response.body())
            }

            override fun onFailure(call: Call<Pojo.DeleteResponse>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })
    }

    fun updateListById(id: Int?, modal: Pojo.UpdateRequest) {
        val response = repository.updateListById(id, modal)
        response.enqueue(object : Callback<Pojo.UpdateResponse> {
            override fun onResponse(
                call: Call<Pojo.UpdateResponse>,
                response: Response<Pojo.UpdateResponse>
            ) {

                Log.d("UPDAAATEE RESSPONSEEE", "${response.body()}")
                updateListResponse.postValue(response.body())
            }

            override fun onFailure(call: Call<Pojo.UpdateResponse>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })
    }

    fun getItems(id: Int?) {
        val response = repository.getItems(id)
        response.enqueue(object : Callback<Pojo.GetAllItemsResponse?> {
            override fun onResponse(
                call: Call<Pojo.GetAllItemsResponse?>,
                response: Response<Pojo.GetAllItemsResponse?>
            ) {
                //SystemClock.sleep(1000)
                getAllitemsResponse.postValue(response.body())
            }

            override fun onFailure(call: Call<Pojo.GetAllItemsResponse?>, t: Throwable) {
                errorMessage.postValue(t.message)
            }

        })
    }

    fun postItem(id: Int?, modal: Pojo.Items) {
        val response = repository.postItem(id, modal)
        response.enqueue(object : Callback<Pojo.CreateItemResponse> {
            override fun onResponse(
                call: Call<Pojo.CreateItemResponse>,
                response: Response<Pojo.CreateItemResponse>
            ) {
                createItemResponse.postValue(response.body())
            }

            override fun onFailure(call: Call<Pojo.CreateItemResponse>, t: Throwable) {
                errorMessage.postValue(t.message)
            }

        })
    }

    fun deleteItemById(id: Int?) {
        val response = repository.deleteItemById(id)
        response.enqueue(object : Callback<Pojo.DeleteItemResponse> {
            override fun onResponse(
                call: Call<Pojo.DeleteItemResponse>,
                response: Response<Pojo.DeleteItemResponse>
            ) {

                deleteItemByIdResponse.postValue(response.body())
            }

            override fun onFailure(call: Call<Pojo.DeleteItemResponse>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })

    }

    fun updateItemById(id: Int?, modal: Pojo.Items) {
        val response = repository.updateItemById(id, modal)
        response.enqueue(object : Callback<Pojo.UpdateItemResponse> {
            override fun onResponse(
                call: Call<Pojo.UpdateItemResponse>,
                response: Response<Pojo.UpdateItemResponse>
            ) {
                updateItemByIdResponse.postValue(response.body())
            }

            override fun onFailure(call: Call<Pojo.UpdateItemResponse>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })
    }

    fun updateCheckBoxById(id: Int?, modal: Pojo.UpdateCheckBoxRequest) {
        val response = repository.updateCheckBoxById(id, modal)
        response.enqueue(object : Callback<Pojo.UpdateCheckBoxResponse> {
            override fun onResponse(
                call: Call<Pojo.UpdateCheckBoxResponse>,
                response: Response<Pojo.UpdateCheckBoxResponse>
            ) {
                updateCheckBoxByIdResponse.postValue(response.body())
            }

            override fun onFailure(call: Call<Pojo.UpdateCheckBoxResponse>, t: Throwable) {
                errorMessage.postValue(t.message)
            }
        })
    }

}