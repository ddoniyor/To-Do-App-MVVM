package com.example.todoappmvvm.model.repository

import com.example.todoappmvvm.model.retrofit.ApiInterface
import com.example.todoappmvvm.model.retrofit.Pojo

class MainRepository constructor(private val apiInterface: ApiInterface) {

    fun signIn(modal: Pojo.LoginRequest) = apiInterface.signInUser(modal)
    fun signUp(modal: Pojo.UserInfoRegister) = apiInterface.signUpUser(modal)
    fun getLists() = apiInterface.getLists()
    fun postList(modal:Pojo.GetAllLists) = apiInterface.postList(modal)
    fun deleteListById(id: Int?) = apiInterface.deleteListById(id)
    fun updateListById(id:Int?,modal: Pojo.GetAllLists) = apiInterface.updateListById(id,modal)

    fun getItems(id: Int?) = apiInterface.getItems(id)
    fun postItem(id: Int?,modal:Pojo.CreateItem) = apiInterface.postItem(id,modal)
    fun deleteItemById(id: Int?) = apiInterface.deleteItemById(id)
    fun updateItemById(id: Int?,modal: Pojo.UpdateItemRequest) = apiInterface.updateItemById(id,modal)
    fun updateCheckBoxById(id: Int?,modal: Pojo.UpdateCheckBoxRequest) = apiInterface.updateCheckBoxById(id,modal)

}