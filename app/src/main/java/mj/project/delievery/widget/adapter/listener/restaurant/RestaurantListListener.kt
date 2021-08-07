package mj.project.delievery.widget.adapter.listener.restaurant

import mj.project.delievery.model.restaurant.RestaurantModel
import mj.project.delievery.widget.adapter.listener.AdapterListener

interface RestaurantListListener: AdapterListener {

    fun onClickItem(model: RestaurantModel)

}
