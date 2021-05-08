package com.example.todoappmvvm.model.retrofit

import java.io.Serializable

object Pojo {
    abstract class Lists{
        abstract var id: Int
        abstract var title: String
        abstract var description: String
    }
    data class Items(
         var id: Int = 0,
         var title: String = "",
         var description: String = "",
         var done: Boolean = false
    ):Serializable

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
        var id: Int = 0,
        var title: String = "",
        var description: String = "",
    ):Serializable

    data class GetAllListResponse(
        var data: List<GetAllLists>,
    )

    data class CreateList(
            var id: Int = 0,
            var title: String = "",
            var description: String = "",
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
            var id:Int = 0,
            var title: String = "",
            var description: String = "",
    )
    data class UpdateResponse(
        var status: String? = null,
    )

    /*data class GetAllItems(
        var id: Int = 0,
        override var title: String = "",
        override var description: String = "",
        override var done: Boolean = false,
    ):Serializable,Items()*/

    data class GetAllItemsResponse(
        var items: List<Items>,
    )

    /*data class CreateItem(
            override var title: String = "",
            override var description: String = "",
            override var done: Boolean = false,
    ):Items()
*/
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