package mj.project.delievery.widget.adapter.listener.order

import mj.project.delievery.model.restaurant.FoodModel
import mj.project.delievery.widget.adapter.listener.AdapterListener

interface OrderMenuListListener: AdapterListener {

    fun onRemoveItem(model: FoodModel)

}