package mj.project.delievery.screen.main.home.restaurant

import androidx.annotation.StringRes
import mj.project.delievery.R

enum class RestaurantCategory(
    @StringRes val categoryNameId: Int,
    @StringRes val categoryTypeId: Int
) {
    ALL(R.string.all, R.string.all_type)
}
