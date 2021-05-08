package com.example.todoappmvvm.model.retrofit

import com.example.todoappmvvm.model.retrofit.Pojo
import retrofit2.Call
import retrofit2.http.*

interface ApiInterface {
    @POST("auth/sign-up")
    fun signUpUser(@Body userDataReg: Pojo.UserInfoRegister): Call<Pojo.UserInfoRegister>

    @POST("auth/sign-in")
    fun signInUser(@Body userDataEnter: Pojo.LoginRequest): Call<Pojo.LoginResponse>

    @GET("api/lists")
    fun getLists():Call<Pojo.GetAllListResponse>

    @POST("api/lists/")
    fun postList(@Body listToPost: Pojo.CreateList):Call<Pojo.CreateListResponse>


    @DELETE("api/lists/{id}")
    fun deleteListById(@Path("id") id: Int?):Call<Pojo.DeleteResponse>

    @PUT("api/lists/{id}")
    fun updateListById(@Path("id")id:Int?, @Body listForUpdate: Pojo.UpdateRequest):Call<Pojo.UpdateResponse>

    //ITEMS
    @GET("api/lists/{id}/items")
    fun getItems(@Path("id")id:Int?):Call<Pojo.GetAllItemsResponse>

    @POST("api/lists/{id}/items/")
    fun postItem(@Path("id")id:Int?,@Body itemToPost: Pojo.Items):Call<Pojo.CreateItemResponse>

    @DELETE("api/items/{id}")
    fun deleteItemById(@Path("id")id: Int?):Call<Pojo.DeleteItemResponse>

    @PUT("api/items/{id}")
    fun updateItemById(@Path("id")id: Int?,@Body itemToUpdate: Pojo.Items):Call<Pojo.UpdateItemResponse>

    @PUT("api/items/{id}")
    fun updateCheckBoxById(@Path("id") id:Int?, @Body checkBoxToUpdate: Pojo.UpdateCheckBoxRequest):Call<Pojo.UpdateCheckBoxResponse>


}