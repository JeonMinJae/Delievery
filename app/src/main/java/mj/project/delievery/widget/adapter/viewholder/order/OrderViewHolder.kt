package mj.project.delievery.widget.adapter.viewholder.order

import mj.project.delievery.R
import mj.project.delievery.databinding.ViewholderOrderBinding
import mj.project.delievery.model.order.OrderModel
import mj.project.delievery.screen.base.BaseViewModel
import mj.project.delievery.util.provider.ResourcesProvider
import mj.project.delievery.widget.adapter.listener.AdapterListener
import mj.project.delievery.widget.adapter.viewholder.ModelViewHolder

class OrderViewHolder(
    private val binding: ViewholderOrderBinding,
    viewModel: BaseViewModel,
    resourcesProvider: ResourcesProvider
) : ModelViewHolder<OrderModel>(binding, viewModel, resourcesProvider) {

    override fun reset() = Unit

    override fun bindData(model: OrderModel) {
        super.bindData(model)
        with(binding) {
            orderTitleText.text = resourcesProvider.getString(R.string.order_history_title, model.orderId)
            val foodMenuList = model.foodMenuList
            foodMenuList
                .groupBy { it.title }
                .entries.forEach { (title, menuList) ->
                    val orderDataStr =
                        orderContentText.text.toString() + "메뉴 : $title | 가격 : ${menuList.first().price}원 X ${menuList.size}\n"
                    orderContentText.text = orderDataStr
                }
            orderContentText.text = orderContentText.text.trim() // trim을 사용하여 공백제거

            orderTotalPriceText.text =
                resourcesProvider.getString(R.string.price,
                    foodMenuList.map { it.price }.reduce { total, price -> total + price })
            //reduce를 사용하여 컬렉션 타입의 데이터를 람다에 차례로 전달해 결과값 반환 누적값,현재값
        }
    }

    override fun bindViews(model: OrderModel, adapterListener: AdapterListener) = Unit

}
