package mj.project.delievery.screen.mylocation

import androidx.annotation.StringRes
import mj.project.delievery.data.entity.locaion.MapSearchInfoEntity
import mj.project.delievery.screen.main.home.HomeState

sealed class MyLocationState {

    object Uninitialized: MyLocationState()

    object Loading: MyLocationState()

    data class Success(
        val mapSearchInfoEntity: MapSearchInfoEntity
    ): MyLocationState()

    data class Confirm(
        val mapSearchInfoEntity: MapSearchInfoEntity
    ): MyLocationState()

    data class Error(
        @StringRes val messageId: Int
    ): MyLocationState()

}
