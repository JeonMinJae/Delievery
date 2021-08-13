package mj.project.delievery.screen.main.home.restaurant.detail

import androidx.annotation.StringRes
import mj.project.delievery.R

enum class RestaurantDetailCategory(
    @StringRes val categoryNameId: Int
) {
// 텝 레이아웃 이름
    MENU(R.string.menu), REVIEW(R.string.review)

}
