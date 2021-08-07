package mj.project.delievery.screen.main.home

import androidx.annotation.StringRes
import mj.project.delievery.data.entity.locaion.MapSearchInfoEntity

sealed class HomeState {

    object Uninitialized: HomeState()

    object Loading: HomeState()

    data class Success(
        val mapSearchInfo: MapSearchInfoEntity
    ): HomeState()

    data class Error(
        @StringRes val messageId: Int
    ): HomeState()



}
