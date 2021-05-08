package com.example.todoappmvvm.model.repository

import com.example.todoappmvvm.model.retrofit.ApiInterface
import com.example.todoappmvvm.model.retrofit.Pojo

class MainRepository constructor(private val apiInterface: ApiInterface) {

    fun signIn(modal: Pojo.LoginRequest) = apiInterface.signInUser(modal)
    fun signUp(modal: Pojo.UserInfoRegister) = apiInterface.signUpUser(modal)
    fun getLists() = apiInterface.getLists()
    fun postList(modal:Pojo.CreateList) = apiInterface.postList(modal)
    fun deleteListById(id: Int?) = apiInterface.deleteListById(id)
    fun updateListById(id:Int?,modal: Pojo.UpdateRequest) = apiInterface.updateListById(id,modal)

    fun getItems(id: Int?) = apiInterface.getItems(id)
    fun postItem(id: Int?,modal:Pojo.Items) = apiInterface.postItem(id,modal)
    fun deleteItemById(id: Int?) = apiInterface.deleteItemById(id)
    fun updateItemById(id: Int?,modal: Pojo.Items) = apiInterface.updateItemById(id,modal)
    fun updateCheckBoxById(id: Int?,modal: Pojo.UpdateCheckBoxRequest) = apiInterface.updateCheckBoxById(id,modal)

}