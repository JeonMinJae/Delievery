package mj.project.delievery.screen.main.my

import android.net.Uri
import androidx.annotation.StringRes
import mj.project.delievery.data.entity.order.OrderEntity
import mj.project.delievery.model.order.OrderModel

sealed class MyState {

    object Uninitialized: MyState()

    object Loading: MyState()

    data class Login(
        val idToken: String
    ): MyState()

    // manager에 idtoekn을 저장한거면, 갖고있다는건데 로그인시도를할때 loginstate에 따라 registerd, 없다면 notregistered로 나눈다.
    sealed class Success: MyState() {

        data class Registered(
            val userName: String,
            val profileImageUri: Uri?,
            val orderList: List<OrderModel>
        ): Success()

        object NotRegistered: Success()

    }

    data class Error(
        @StringRes val messageId: Int,
        val e: Throwable
    ): MyState()

}
