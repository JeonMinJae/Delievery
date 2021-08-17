package mj.project.delievery.widget.adapter.listener.restaurant

import mj.project.delievery.model.restaurant.RestaurantModel

interface RestaurantLikeListListener: RestaurantListListener {

    fun onDislikeItem(model: RestaurantModel)

}
