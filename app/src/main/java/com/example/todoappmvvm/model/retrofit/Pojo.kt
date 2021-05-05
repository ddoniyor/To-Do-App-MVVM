package com.example.todoappmvvm.model.retrofit

import java.io.Serializable

object Pojo {
    data class UserInfoRegister(
        var name: String? = null,
        var username: String? = null,
        var password: String? = null,
        var id: String? = null,
    )

    data class LoginRequest(
        var username: String? = null,
        var password: String? = null,

        )

    data class LoginResponse(
        var token: String? = null,
    )

    data class GetAllLists(
        var id: Int? = null,
        var title: String? = null,
        var description: String? = null,
    ):Serializable

    data class GetAllListResponse(
        var data: List<GetAllLists>,
    )

    data class CreateList(
        var id: Int? = null,
        var title: String? = null,
        var description: String? = null,
    )

    data class CreateListResponse(
        var id: Int? = null,
    )

    data class DeleteRequest(
        var id: Int? = null,
    )

    data class DeleteResponse(
        var status: String? = null,
    )

    data class UpdateRequest(
        var id:Int? = null,
        var title: String? = null,
        var description: String? = null,
    )

    data class UpdateResponse(
        var status: String? = null,
    )

    data class GetAllItems(
        var id: Int,
        var title: String? = null,
        var description: String? = null,
        var done: Boolean = false,
    ):Serializable

    data class GetAllItemsResponse(
        var items: List<GetAllItems>,
    )

    data class CreateItem(
        var id: Int? = null,
        var title: String? = null,
        var description: String? = null,
        var done: Boolean = false,
    )

    data class CreateItemResponse(
        var id: Int? = null,
    )

    data class DeleteItemResponse(
        var status: String? = null,
    )

    data class UpdateItemRequest(
        var title: String? = null,
        var description: String? = null,
    )

    data class UpdateItemResponse(
        var status: String? = null,
    )

    data class UpdateCheckBoxRequest(
        var done: Boolean = false,
    )

    data class UpdateCheckBoxResponse(
        var status: String? = null,
    )

}