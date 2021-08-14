package mj.project.delievery.widget.adapter.listener.restaurant

import mj.project.delievery.model.restaurant.FoodModel
import mj.project.delievery.widget.adapter.listener.AdapterListener

interface FoodMenuListListener: AdapterListener {

    fun onClickItem(model: FoodModel)

}